package com.linkedin.camus.etl.kafka.mapred;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import kafka.common.ErrorMapping;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.Message;

import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.mapred.AvroWrapper;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import com.linkedin.camus.coders.MessageDecoder;
import com.linkedin.camus.etl.kafka.CamusJob;
import com.linkedin.camus.etl.kafka.coders.KafkaAvroMessageDecoder;
import com.linkedin.camus.etl.kafka.coders.MessageDecoderFactory;
import com.linkedin.camus.etl.kafka.common.EtlKey;
import com.linkedin.camus.etl.kafka.common.EtlRequest;

/**
 * Input format for a Kafka pull job.
 */
public class EtlInputFormat extends InputFormat<EtlKey, AvroWrapper<Object>> {

    public static final String KAFKA_BLACKLIST_TOPIC = "kafka.blacklist.topics";
    public static final String KAFKA_WHITELIST_TOPIC = "kafka.whitelist.topics";

    public static final String KAFKA_MOVE_TO_LAST_OFFSET_LIST = "kafka.move.to.last.offset.list";

    public static final String KAFKA_CLIENT_BUFFER_SIZE = "kafka.client.buffer.size";
    public static final String KAFKA_CLIENT_SO_TIMEOUT = "kafka.client.so.timeout";

    public static final String KAFKA_MAX_PULL_HRS = "kafka.max.pull.hrs";
    public static final String KAFKA_MAX_PULL_MINUTES_PER_TASK = "kafka.max.pull.minutes.per.task";
    public static final String KAFKA_MAX_HISTORICAL_DAYS = "kafka.max.historical.days";

    public static final String CAMUS_MESSAGE_DECODER_CLASS = "camus.message.decoder.class";
    public static final String ETL_IGNORE_SCHEMA_ERRORS = "etl.ignore.schema.errors";
    public static final String ETL_AUDIT_IGNORE_SERVICE_TOPIC_LIST = "etl.audit.ignore.service.topic.list";

    private final Logger log = Logger.getLogger(getClass());

    @Override
    public RecordReader<EtlKey, AvroWrapper<Object>> createRecordReader(InputSplit split,
            TaskAttemptContext context) throws IOException, InterruptedException {
        return new EtlRecordReader(split, context);
    }

    @Override
    public List<InputSplit> getSplits(JobContext context) throws IOException, InterruptedException {
        CamusJob.startTiming("getSplits");
        System.out.println("start getSplits");
        List<EtlRequest> requests;
        try {

            ArrayList<String> topicList = new ArrayList<String>();
            HashSet<String> whiteListTopics = new HashSet<String>( 
                    Arrays.asList(getKafkaWhitelistTopic(context)));
            HashSet<String> blackListTopics = new HashSet<String>(
                    Arrays.asList(getKafkaBlacklistTopic(context)));
            System.out.println("Whitelist topics : " + whiteListTopics);
            System.out.println("Blacklist Topics : " + blackListTopics);

            if (!whiteListTopics.isEmpty()) {
                topicList.addAll(whiteListTopics);
            }
            CamusJob.startTiming("kafkaSetupTime");
            List<TopicMetadata> topicMetadataList = null;//KafkaClient.getMetadata(topicList);
            SimpleConsumer consumer = null;
            consumer = new SimpleConsumer(CamusJob.getKafkaHostUrl(context),
                    CamusJob.getKafkaHostPort(context), CamusJob.getKafkaTimeoutValue(context),
                    CamusJob.getKafkaBufferSize(context), CamusJob.getKafkaClientName(context));
            topicMetadataList = (consumer.send(new TopicMetadataRequest(topicList)))
                    .topicsMetadata();

            CamusJob.stopTiming("kafkaSetupTime");

            ArrayList<String> topicsToDiscard = new ArrayList<String>();
            for (TopicMetadata topicMetadata : topicMetadataList) {
                if (blackListTopics.contains(topicMetadata.topic())
                        || !createMessageDecoder(context, topicMetadata.topic())) {
                    topicsToDiscard.add(topicMetadata.topic());
                }
            }

            // Initialize a decoder for each topic so we can discard the topics
            // for which we cannot construct a decoder
            for (String topic : topicList) {
                System.out.println("TOPIC ------> " + topic);
                System.out.println(context);

                try {
                    MessageDecoderFactory.createMessageDecoder(context, topic);
                } catch (Exception e) {
                    log.debug("We cound not construct a decoder for topic '" + topic
                            + "', so that topic will be discarded.", e);
                    topicsToDiscard.add(topic);
                }
            }

            if (topicsToDiscard.isEmpty()) {
                log.info("All topics seem valid! None will be discarded :)");
            } else {
                log.error("We could not construct decoders for the following topics, so they will be discarded: "
                        + topicsToDiscard);
                topicList.removeAll(topicsToDiscard);
            }

            log.info("The following topicList will be pulled from Kafka and written to HDFS: "
                    + topicList);

            requests = new ArrayList<EtlRequest>();
            
            HashMap<String, List<EtlRequest>> partitionInfo = new HashMap<String, List<EtlRequest>>();
            
            for (TopicMetadata topicMetadata : topicMetadataList) {
                if (topicsToDiscard.contains(topicMetadata.topic())) {
                    continue;
                }
                ArrayList<EtlRequest> tempEtlRequests = new ArrayList<EtlRequest>();
                List<PartitionMetadata> partitionsMetadata = topicMetadata.partitionsMetadata();
                for (PartitionMetadata partitionMetadata : partitionsMetadata) {
                    if (partitionMetadata.errorCode() != ErrorMapping.NoError()) {
                        log.info("Skipping the creation of ETL request for Topic : "
                                + topicMetadata.topic() + " and Partition : "
                                + partitionMetadata.partitionId() + " Exception : "
                                + ErrorMapping.exceptionFor(partitionMetadata.errorCode()));
                        continue;
                    } else {
                        try {
                            EtlRequest etlRequest = new EtlRequest(context, topicMetadata.topic(),
                                    Integer.toString(partitionMetadata.leader().id()),
                                    partitionMetadata.partitionId(), new URI("tcp://"
                                    + partitionMetadata.leader().getConnectionString()));
                            tempEtlRequests.add(etlRequest);
                        } catch (URISyntaxException e) {
                            log.info("Skipping the creation of ETL request for Topic : "
                                    + topicMetadata.topic() + " and Partition : "
                                    + partitionMetadata.partitionId() + " Exception : "
                                    + e.getMessage());
                            continue;
                        }
                    }
                }
                if (tempEtlRequests.size() != 0) {
                    partitionInfo.put(topicMetadata.topic(), tempEtlRequests);
                }
                // tempEtlRequests.clear();
            }
            requests = new ArrayList<EtlRequest>();
            for (String topic : partitionInfo.keySet()) {
                requests.addAll(partitionInfo.get(topic));
            }
        } catch (Exception e) {
            log.error("Unable to pull requests from Kafka brokers. Exiting the program", e);
            return null;
        }

        // writing request to output directory so next Camus run can use them if
        // needed
        writeRequests(requests, context);

        System.out.println("Input paths:");
        for(Path path : FileInputFormat.getInputPaths(context)) {
            System.out.println(path.toUri().getPath());
        }
        Map<EtlRequest, EtlKey> offsetKeys = getPreviousOffsets(
                FileInputFormat.getInputPaths(context), context);
        //System.out.println("The size of previous offsets read is " + offsetKeys.size() + " and the requests size is " + requests.size());
        Set<String> moveLatest = getMoveToLatestTopicsSet(context);
        
        for (EtlRequest request : requests) {
            if (moveLatest.contains(request.getTopic()) || moveLatest.contains("all")) {
                offsetKeys.put(request,
                        new EtlKey(request.getTopic(), request.getLeaderId(), request.getPartition(),
                                0, request.getLastOffset()));
            }
            
            EtlKey key = offsetKeys.get(request);
//            if(key == null)
//            {
//                System.out.println("Not able to locate the previous offset. ");
//            }
            if (key != null) {
//                System.out.println("The leader in the key is --> " + key.getleaderId());
//                System.out.println("NOTE : The offset has been changed to read from this offset ---> " + key.getOffset());
                request.setOffset(key.getOffset());
            }

            if (request.getEarliestOffset() > request.getOffset()) {
//                System.out.println("NOTE : The offset has been changed to read from the EARLIEST offset ---> " + key.getOffset());
                request.setOffset(request.getEarliestOffset());
            }
           
            log.info(request);
        }
      
        writePrevious(offsetKeys.values(), context);

        CamusJob.stopTiming("getSplits");
        CamusJob.startTiming("hadoop");
        CamusJob.setTime("hadoop_start");
        
        System.out.println("end getSplits");
        return allocateWork(requests, context);
    }

    private Set<String> getMoveToLatestTopicsSet(JobContext context) {
        System.out.println("start getMoveToLatestTopicsSet");
        Set<String> topics = new HashSet<String>();

        String[] arr = getMoveToLatestTopics(context);

        if (arr != null) {
            for (String topic : arr) {
                topics.add(topic);
            }
        }
        System.out.println("end getMoveToLatestTopicsSet");
        return topics;
    }

    private boolean createMessageDecoder(JobContext context, String topic) {
        System.out.println("start createMessageDecoder");
        try {
            MessageDecoderFactory.createMessageDecoder(context, topic);
            System.out.println("end createMessageDecoder");
            return true;
        } catch (Exception e) {
            log.debug("We cound not construct a decoder for topic '" + topic
                    + "', so that topic will be discarded.", e);
            return false;
        }
    }

    private List<InputSplit> allocateWork(List<EtlRequest> requests, JobContext context)
            throws IOException {
        System.out.println("start allocateWork");
        int numTasks = context.getConfiguration().getInt("mapred.map.tasks", 30);
        // Reverse sort by size
        Collections.sort(requests, new Comparator<EtlRequest>() {
            @Override
            public int compare(EtlRequest o1, EtlRequest o2) {
                if (o2.estimateDataSize() == o1.estimateDataSize()) {
                    return 0;
                }
                if (o2.estimateDataSize() < o1.estimateDataSize()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        List<InputSplit> kafkaETLSplits = new ArrayList<InputSplit>();

        for (int i = 0; i < numTasks; i++) {
            EtlSplit split = new EtlSplit();

            if (requests.size() > 0) {
                split.addRequest(requests.get(0));
                kafkaETLSplits.add(split);
                requests.remove(0);
            }
        }

        for (EtlRequest r : requests) {
            getSmallestMultiSplit(kafkaETLSplits).addRequest(r);
        }
        System.out.println("end allocateWork");
        return kafkaETLSplits;
    }

    private EtlSplit getSmallestMultiSplit(List<InputSplit> kafkaETLSplits) throws IOException {
        System.out.println("start getSmallestMultiSplit");
        EtlSplit smallest = (EtlSplit) kafkaETLSplits.get(0);

        for (int i = 1; i < kafkaETLSplits.size(); i++) {
            EtlSplit challenger = (EtlSplit) kafkaETLSplits.get(i);
            if ((smallest.getLength() == challenger.getLength() && smallest.getNumRequests() > challenger
                    .getNumRequests()) || smallest.getLength() > challenger.getLength()) {
                smallest = challenger;
            }
        }
        System.out.println("end getSmallestMultiSplit");
        return smallest;
    }

    private void writePrevious(Collection<EtlKey> missedKeys, JobContext context)
    		throws IOException {
        System.out.println("start writePrevious");
        FileSystem fs = FileSystem.get(context.getConfiguration());
        Path output = FileOutputFormat.getOutputPath(context);

        if (fs.exists(output)) {
            fs.mkdirs(output);
        }

        output = new Path(output, EtlMultiOutputFormat.OFFSET_PREFIX + "-previous");
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, context.getConfiguration(),
        		output, EtlKey.class, NullWritable.class);

        for (EtlKey key : missedKeys) {
        	try {
        		writer.append(key, NullWritable.get());
        		System.out.println("Writing previous offset to " + output + " key: " + key.toString());
        	} catch (IOException e) {
        		System.out.println("********Exception caught while appending to previous offset file***********");
        		e.printStackTrace();
        		throw e;
        	}
        }
        try {
        	writer.close();
        	System.out.println("end writePrevious");
        } catch (IOException e) {
        	System.out.println("*********Exception caught while closing previous offset file************");
        	e.printStackTrace();
        	throw e;
        }
    }

    private void writeRequests(List<EtlRequest> requests, JobContext context) throws IOException {
        System.out.println("start writeRequests");
        FileSystem fs = FileSystem.get(context.getConfiguration());
        Path output = FileOutputFormat.getOutputPath(context);

        if (fs.exists(output)) {
            fs.mkdirs(output);
        }

        output = new Path(output, EtlMultiOutputFormat.REQUESTS_FILE);
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, context.getConfiguration(),
                output, EtlRequest.class, NullWritable.class);

        for (EtlRequest r : requests) {
			try {
				writer.append(r, NullWritable.get());
				System.out.println("Writing previous request to " + output + " request: " + r.toString());
			} catch (IOException e) {
				System.out.println("**********Exception caught while writing to previous requests file*************");
				e.printStackTrace();
				throw e;
			}
        }
        try {
        	writer.close();
        	System.out.println("end writeRequests");
        } catch (IOException e) {
        	System.out.println("*********Exception caught while closing previous requests file************");
        	e.printStackTrace();
        	throw e;
        }
    }
//    private List<EtlRequest> getPreviousRequests(JobContext context) throws IOException {
//        List<EtlRequest> requests = new ArrayList<EtlRequest>();
//        FileSystem fs = FileSystem.get(context.getConfiguration());
//        Path input = FileInputFormat.getInputPaths(context)[0];
//
//        input = new Path(input, EtlMultiOutputFormat.REQUESTS_FILE);
//        SequenceFile.Reader reader = new SequenceFile.Reader(fs, input, context.getConfiguration());
//
//        EtlRequest request = new EtlRequest();
//        while (reader.next(request, NullWritable.get())) {
//            requests.add(new EtlRequest(request));
//        }
//
//        reader.close();
//        return requests;
//    }

    private Map<EtlRequest, EtlKey> getPreviousOffsets(Path[] inputs, JobContext context)
            throws IOException {
        System.out.println("start getPreviousOffsets");
        Map<EtlRequest, EtlKey> offsetKeysMap = new HashMap<EtlRequest, EtlKey>();
        for (Path input : inputs) {
            FileSystem fs = input.getFileSystem(context.getConfiguration());
            for (FileStatus f : fs.listStatus(input, new OffsetFileFilter())) {
                log.info("previous offset file:" + f.getPath().toString());
                SequenceFile.Reader reader = new SequenceFile.Reader(fs, f.getPath(),
                        context.getConfiguration());
                EtlKey key = new EtlKey();
                System.out.println("Got previous offset from " + input + " key: " + key.toString());
                while (reader.next(key, NullWritable.get())) {
                    EtlRequest request = new EtlRequest(context, key.getTopic(), key.getLeaderId(),
                            key.getPartition());
                    System.out.println("Got request from previous offset from " + input + " request: " + request.toString());	
                    if (offsetKeysMap.containsKey(request)) {
                        
                        EtlKey oldKey = offsetKeysMap.get(request);                      
                        if (oldKey.getOffset() < key.getOffset()) {
                            offsetKeysMap.put(request, key);
                        }
                    } else {
                        offsetKeysMap.put(request, key);
                    }
                    key = new EtlKey();
                }
                reader.close();
            }
        }
        System.out.println("end getPreviousOffsets");
        return offsetKeysMap;
    }

    public static void setMoveToLatestTopics(JobContext job, String val) {
        job.getConfiguration().set(KAFKA_MOVE_TO_LAST_OFFSET_LIST, val);
    }

    public static String[] getMoveToLatestTopics(JobContext job) {
        return job.getConfiguration().getStrings(KAFKA_MOVE_TO_LAST_OFFSET_LIST);
    }

    public static void setKafkaClientBufferSize(JobContext job, int val) {
        job.getConfiguration().setInt(KAFKA_CLIENT_BUFFER_SIZE, val);
    }

    public static int getKafkaClientBufferSize(JobContext job) {
        return job.getConfiguration().getInt(KAFKA_CLIENT_BUFFER_SIZE, 2 * 1024 * 1024);
    }

    public static void setKafkaClientTimeout(JobContext job, int val) {
        job.getConfiguration().setInt(KAFKA_CLIENT_SO_TIMEOUT, val);
    }

    public static int getKafkaClientTimeout(JobContext job) {
        return job.getConfiguration().getInt(KAFKA_CLIENT_SO_TIMEOUT, 60000);
    }

    public static void setKafkaMaxPullHrs(JobContext job, int val) {
        job.getConfiguration().setInt(KAFKA_MAX_PULL_HRS, val);
    }

    public static int getKafkaMaxPullHrs(JobContext job) {
        return job.getConfiguration().getInt(KAFKA_MAX_PULL_HRS, -1);
    }

    public static void setKafkaMaxPullMinutesPerTask(JobContext job, int val) {
        job.getConfiguration().setInt(KAFKA_MAX_PULL_MINUTES_PER_TASK, val);
    }

    public static int getKafkaMaxPullMinutesPerTask(JobContext job) {
        return job.getConfiguration().getInt(KAFKA_MAX_PULL_MINUTES_PER_TASK, -1);
    }

    public static void setKafkaMaxHistoricalDays(JobContext job, int val) {
        job.getConfiguration().setInt(KAFKA_MAX_HISTORICAL_DAYS, val);
    }

    public static int getKafkaMaxHistoricalDays(JobContext job) {
        return job.getConfiguration().getInt(KAFKA_MAX_HISTORICAL_DAYS, -1);
    }

    public static void setKafkaBlacklistTopic(JobContext job, String val) {
        job.getConfiguration().set(KAFKA_BLACKLIST_TOPIC, val);
    }

    public static String[] getKafkaBlacklistTopic(JobContext job) {
        if (job.getConfiguration().get(KAFKA_BLACKLIST_TOPIC) != null
                && !job.getConfiguration().get(KAFKA_BLACKLIST_TOPIC).isEmpty()) {
            return job.getConfiguration().getStrings(KAFKA_BLACKLIST_TOPIC);
        } else {
            return new String[] {};
        }
    }

    public static void setKafkaWhitelistTopic(JobContext job, String val) {
        job.getConfiguration().set(KAFKA_WHITELIST_TOPIC, val);
    }

    public static String[] getKafkaWhitelistTopic(JobContext job) {
        if (job.getConfiguration().get(KAFKA_WHITELIST_TOPIC) != null
                && !job.getConfiguration().get(KAFKA_WHITELIST_TOPIC).isEmpty()) {
            return job.getConfiguration().getStrings(KAFKA_WHITELIST_TOPIC);
        } else {
            return new String[] {};
        }
    }

    public static void setEtlIgnoreSchemaErrors(JobContext job, boolean val) {
        job.getConfiguration().setBoolean(ETL_IGNORE_SCHEMA_ERRORS, val);
    }

    public static boolean getEtlIgnoreSchemaErrors(JobContext job) {
        return job.getConfiguration().getBoolean(ETL_IGNORE_SCHEMA_ERRORS, false);
    }

    public static void setEtlAuditIgnoreServiceTopicList(JobContext job, String topics) {
        job.getConfiguration().set(ETL_AUDIT_IGNORE_SERVICE_TOPIC_LIST, topics);
    }

    public static String[] getEtlAuditIgnoreServiceTopicList(JobContext job) {
        return job.getConfiguration().getStrings(ETL_AUDIT_IGNORE_SERVICE_TOPIC_LIST);
    }

    public static void setMessageDecoderClass(JobContext job, Class<KafkaAvroMessageDecoder> cls) {
        job.getConfiguration().setClass(CAMUS_MESSAGE_DECODER_CLASS, cls,
                KafkaAvroMessageDecoder.class);
    }

    public static Class<KafkaAvroMessageDecoder> getMessageDecoderClass(JobContext job) {
        return (Class<KafkaAvroMessageDecoder>) job.getConfiguration().getClass(
                CAMUS_MESSAGE_DECODER_CLASS, KafkaAvroMessageDecoder.class);
    }

    private class OffsetFileFilter implements PathFilter {

        @Override
        public boolean accept(Path arg0) {
            return arg0.getName().startsWith(EtlMultiOutputFormat.OFFSET_PREFIX);
        }
    }
}
