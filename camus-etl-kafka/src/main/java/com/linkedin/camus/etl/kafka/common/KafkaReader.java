package com.linkedin.camus.etl.kafka.common;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kafka.api.PartitionFetchInfo;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchRequest;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.OffsetResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.Message;
import kafka.message.MessageAndOffset;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import com.linkedin.camus.etl.kafka.CamusJob;

/**
 * Poorly named class that handles kafka pull events within each
 * KafkaRecordReader.
 * 
 * @author Richard Park
 */
public class KafkaReader {
//    // index of context
//    private EtlRequest kafkaRequest = null;
//    private SimpleConsumer simpleConsumer = null;
//
//    private long beginOffset;
//    private long currentOffset;
//    private long lastOffset;
//    private long currentCount;
//
//    private TaskAttemptContext context;
//
//    private Iterator<MessageAndOffset> messageIter = null;
//
//    private long totalFetchTime = 0;
//    private long lastFetchTime = 0;
//
//    private int fetchBufferSize;
//
//    private long processedOffset = 0;
//
//    /**
//     * Construct using the json represention of the kafka request
//     */
//    public KafkaReader(TaskAttemptContext context, EtlRequest request, int clientTimeout,
//            int fetchBufferSize) throws Exception {
//        this.fetchBufferSize = fetchBufferSize;
//        this.context = context;
//
//        System.out.println("bufferSize = " + fetchBufferSize);
//        System.out.println("timeout = " + clientTimeout);
//
//        // Create the kafka request from the json
//
//        kafkaRequest = request;
//        //Leader might change during setup.Query for leader again
//        //Leader might have less offsets due to replication. Query for offset again
//        checkMetadataAndOffset();
//
//        beginOffset = request.getOffset();
//        currentOffset = request.getOffset();
//        lastOffset = request.getLastOffset();
//        currentCount = 0;
//        totalFetchTime = 0;
//
//        // read data from queue
//
//        URI uri = kafkaRequest.getURI();
//        simpleConsumer = new SimpleConsumer(uri.getHost(), uri.getPort(),
//                CamusJob.getKafkaTimeoutValue(context), CamusJob.getKafkaBufferSize(context),
//                CamusJob.getKafkaClientName(context));
//        fetch();
//        System.out.println("Connected to node " + uri + " beginning reading at offset "
//                + beginOffset + " latest offset=" + lastOffset);
//    }
//
//    public boolean hasNext() throws IOException {
//        return (messageIter != null && messageIter.hasNext()) || fetch();
//    }
//
//    /**
//     * Fetches the next Kafka message and stuffs the results into the key and
//     * value
//     * 
//     * @param key
//     * @param value
//     * @return true if there exists more events
//     * @throws IOException
//     */
//    public boolean getNext(EtlKey key, BytesWritable value) throws IOException {
//        if (hasNext()) {
//
//            MessageAndOffset msgAndOffset = messageIter.next();
//            if (processedOffset == 0) {
//                System.out.println("Starting processing at offset : " + msgAndOffset.offset());
//            }
//            processedOffset = msgAndOffset.offset();
//            Message message = msgAndOffset.message();
//
//            ByteBuffer buf = message.payload();
//            int origSize = buf.remaining();
//            byte[] bytes = new byte[origSize];
//            buf.get(bytes, buf.position(), origSize);
//            value.set(bytes, 0, origSize);
//
//            key.clear();
//            key.set(kafkaRequest.getTopic(), kafkaRequest.getNodeId(), kafkaRequest.getPartition(),
//                    currentOffset, msgAndOffset.offset(), message.checksum());
//
//            currentOffset = msgAndOffset.offset(); // increase offset
//            currentCount++; // increase count
//
//            return true;
//        } else {
//            System.out.println("No more messages in getNext(), processed offset till = " + processedOffset);
//            return false;
//        }
//    }
//
//    /**
//     * Creates a fetch request.
//     * 
//     * @return false if there's no more fetches
//     * @throws IOException
//     */
//    public boolean fetch() throws IOException {
//        if (currentOffset >= lastOffset) {
//            return false;
//        }
//        long tempTime = System.currentTimeMillis();
//        // FetchResponse fetchResponse =
//        System.out.println("In fetch(), processed offset is 0");
//        processedOffset = 0;
//        TopicAndPartition topicAndPartition = new TopicAndPartition(kafkaRequest.getTopic(),
//                kafkaRequest.getPartition());
//        System.out.println("Asking for data from offset : " + (currentOffset + 1l));
//        PartitionFetchInfo partitionFetchInfo = new PartitionFetchInfo((currentOffset + 1l),
//                fetchBufferSize);
//
//        HashMap<TopicAndPartition, PartitionFetchInfo> fetchInfo = new HashMap<TopicAndPartition, PartitionFetchInfo>();
//        fetchInfo.put(topicAndPartition, partitionFetchInfo);
//
//        FetchRequest fetchRequest = new FetchRequest(
//                CamusJob.getKafkaFetchRequestCorrelationId(context),
//                CamusJob.getKafkaClientName(context),
//                CamusJob.getKafkaFetchRequestMaxWait(context),
//                CamusJob.getKafkaFetchRequestMinBytes(context), fetchInfo);
//
//        FetchResponse fetchResponse = null;
//        try {
//            fetchResponse = simpleConsumer.fetch(fetchRequest);
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//        }
//        ByteBufferMessageSet messageBuffer = fetchResponse.messageSet(kafkaRequest.getTopic(),
//                kafkaRequest.getPartition());
//        lastFetchTime = (System.currentTimeMillis() - tempTime);
//        totalFetchTime += lastFetchTime;
//        if (!hasError(fetchResponse.errorCode(kafkaRequest.getTopic(), kafkaRequest.getPartition()))) {
//            messageIter = messageBuffer.iterator();
//            boolean flag = false;
//            boolean debug = true;
//            Iterator<MessageAndOffset> messageIter2 = messageBuffer.iterator();
//            while (messageIter2.hasNext()) {
//                MessageAndOffset message = messageIter2.next();
//                if (message.offset() < currentOffset) {
//                    flag = true;
//                    if (debug) {
//                        debug = false;
//                        System.out.println("Skipping offsets from : " + message.offset());
//                    }
//                } else {
//                    System.out.println("Skipped offsets till : " + message.offset());
//                    break;
//                }
//            }
//            if (!messageIter2.hasNext()) {
//                System.out.println("No more data left to process. Returning false");
//                messageIter = messageIter2;
//                return false;
//            }
//            if (flag) {
//                messageIter = messageIter2;
//            }
//            return true;
//        } else {
//            return false;
//        }
//
//    }
//
//    /**
//     * Closes this context
//     * 
//     * @throws IOException
//     */
//    public void close() throws IOException {
//        if (simpleConsumer != null) {
//            simpleConsumer.close();
//        }
//    }
//
//    /**
//     * Called by the default implementation of {@link #map} to check error code
//     * to determine whether to continue.
//     */
//    private boolean hasError(Short errorCode) throws IOException {
//
//        if (errorCode == ErrorMapping.OffsetOutOfRangeCode()) {
//            // offset cannot cross the maximum offset (guaranteed by Kafka
//            // protocol).
//            // Kafka server may delete old files from time to time
//            if (currentOffset != kafkaRequest.getEarliestOffset()) {
//                // get the current offset range
//                currentOffset = kafkaRequest.getEarliestOffset();
//                return false;
//            }
//            throw new IOException(kafkaRequest + " earliest offset = " + currentOffset
//                    + " : invalid offset.");
//        } else if (errorCode == ErrorMapping.InvalidMessageCode()) {
//            throw new IOException(kafkaRequest + " current offset = " + currentOffset
//                    + " : invalid offset.");
//        } else if (errorCode != ErrorMapping.NoError()) {
//            throw new IOException(kafkaRequest + " current offset = " + currentOffset + " error:"
//                    + ErrorMapping.exceptionFor(errorCode));
//        } else {
//            return false;
//        }
//    }


	// index of context
	private EtlRequest kafkaRequest = null;
	private SimpleConsumer simpleConsumer = null;

	private long beginOffset;
	private long currentOffset;
	private long lastOffset;
	private long currentCount;

	private TaskAttemptContext context;

	private Iterator<MessageAndOffset> messageIter = null;

	private long totalFetchTime = 0;
	private long lastFetchTime = 0;

	private int fetchBufferSize;

	/**
	 * Construct using the json represention of the kafka request
	 */
	public KafkaReader(TaskAttemptContext context, EtlRequest request,
			int clientTimeout, int fetchBufferSize) throws Exception {
		this.fetchBufferSize = fetchBufferSize;
		this.context = context;

		System.out.println("bufferSize=" + fetchBufferSize);
		System.out.println("timeout=" + clientTimeout);

		// Create the kafka request from the json

		kafkaRequest = request;

		beginOffset = request.getOffset();
		currentOffset = request.getOffset();
		lastOffset = request.getLastOffset();
		currentCount = 0;
		totalFetchTime = 0;

		// read data from queue

		URI uri = kafkaRequest.getURI();
		simpleConsumer = new SimpleConsumer(uri.getHost(), uri.getPort(),
				CamusJob.getKafkaTimeoutValue(context),
				CamusJob.getKafkaBufferSize(context),
				CamusJob.getKafkaClientName(context));
		System.out.println("Connected to leader " + uri
				+ " beginning reading at offset " + beginOffset
				+ " latest offset = " + lastOffset);
		fetch();
	}

	public boolean hasNext() throws IOException {
		if (messageIter != null && messageIter.hasNext())
			return true;
		else
			return fetch();

	}

	/**
	 * Fetches the next Kafka message and stuffs the results into the key and
	 * value
	 * 
	 * @param key
	 * @param value
	 * @return true if there exists more events
	 * @throws IOException
	 */
	public boolean getNext(EtlKey key, BytesWritable value) throws IOException {
		if (hasNext()) {

			MessageAndOffset msgAndOffset = messageIter.next();
			Message message = msgAndOffset.message();

			ByteBuffer buf = message.payload();
			int origSize = buf.remaining();
			byte[] bytes = new byte[origSize];
			buf.get(bytes, buf.position(), origSize);
			value.set(bytes, 0, origSize);

			key.clear();
			key.set(kafkaRequest.getTopic(), kafkaRequest.getLeaderId(),
					kafkaRequest.getPartition(), currentOffset,
					msgAndOffset.offset(), message.checksum());

			currentOffset = msgAndOffset.offset(); // increase offset
			currentCount++; // increase count

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Creates a fetch request.
	 * 
	 * @return false if there's no more fetches
	 * @throws IOException
	 */

	public boolean fetch() throws IOException {
		if (currentOffset + 1 >= lastOffset) {
			return false;
		}
		long tempTime = System.currentTimeMillis();
		TopicAndPartition topicAndPartition = new TopicAndPartition(
				kafkaRequest.getTopic(), kafkaRequest.getPartition());
		System.out.println("\nAsking for offset : " + (currentOffset + 1));
		PartitionFetchInfo partitionFetchInfo = new PartitionFetchInfo(
				currentOffset + 1, fetchBufferSize);

		HashMap<TopicAndPartition, PartitionFetchInfo> fetchInfo = new HashMap<TopicAndPartition, PartitionFetchInfo>();
		fetchInfo.put(topicAndPartition, partitionFetchInfo);

		FetchRequest fetchRequest = new FetchRequest(
				CamusJob.getKafkaFetchRequestCorrelationId(context),
				CamusJob.getKafkaClientName(context),
				CamusJob.getKafkaFetchRequestMaxWait(context),
				CamusJob.getKafkaFetchRequestMinBytes(context), fetchInfo);

		FetchResponse fetchResponse = null;
		try {
			fetchResponse = simpleConsumer.fetch(fetchRequest);
			if (fetchResponse.hasError()) {
				System.out.println("Error encountered during a fetch request from Kafka");
				System.out.println("Error Code generated : "
						+ fetchResponse.errorCode(kafkaRequest.getTopic(), kafkaRequest.getPartition()));
				return false;
			} else {
				ByteBufferMessageSet messageBuffer = fetchResponse.messageSet(
						kafkaRequest.getTopic(), kafkaRequest.getPartition());
				lastFetchTime = (System.currentTimeMillis() - tempTime);
				System.out.println("Time taken to fetch : " + (lastFetchTime / 1000) + " seconds");
				int skipped = 0;
				totalFetchTime += lastFetchTime;
				messageIter = messageBuffer.iterator();
				boolean flag = false;
				Iterator<MessageAndOffset> messageIter2 = messageBuffer
						.iterator();
				MessageAndOffset message = null;
				while (messageIter2.hasNext()) {
					message = messageIter2.next();
					if (message.offset() < currentOffset) {
						flag = true;
						skipped++;
					} else {
						System.out.println("Skipped offsets till : " + message.offset());
						break;
					}
				}
				System.out.println("Number of skipped offsets : " + skipped);
				if (!messageIter2.hasNext()) {
					System.out.println("No more data left to process. Returning false");
					messageIter = null;
					return false;
				}
				if (flag) {
					messageIter = messageIter2;
				}
				return true;
			}
		} catch (Exception e) {
			System.out.println("Exception generated during fetch");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Closes this context
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (simpleConsumer != null) {
			simpleConsumer.close();
		}
	}

	/**
	 * Called by the default implementation of {@link #map} to check error code
	 * to determine whether to continue.
	 */
//	private boolean hasError(Short errorCode) throws IOException {
//
//		if (errorCode == ErrorMapping.OffsetOutOfRangeCode()) {
//			// offset cannot cross the maximum offset (guaranteed by Kafka
//			// protocol).
//			// Kafka server may delete old files from time to time
//			if (currentOffset != kafkaRequest.getEarliestOffset()) {
//				// get the current offset range
//				currentOffset = kafkaRequest.getEarliestOffset();
//				return false;
//			}
//			throw new IOException(kafkaRequest + " earliest offset="
//					+ currentOffset + " : invalid offset.");
//		} else if (errorCode == ErrorMapping.InvalidMessageCode()) {
//			throw new IOException(kafkaRequest + " current offset="
//					+ currentOffset + " : invalid offset.");
//		} else if (errorCode != ErrorMapping.NoError()) {
//			throw new IOException(kafkaRequest + " current offset="
//					+ currentOffset + " error:"
//					+ ErrorMapping.exceptionFor(errorCode));
//		} else {
//			return false;
//		}
//	}

	/**
     * 
     */
	// public void checkMetadataAndOffset() {
	// try {
	// SimpleConsumer consumer = new
	// SimpleConsumer(CamusJob.getKafkaHostUrl(context),
	// CamusJob.getKafkaHostPort(context),
	// CamusJob.getKafkaTimeoutValue(context),
	// CamusJob.getKafkaBufferSize(context),
	// CamusJob.getKafkaClientName(context));
	// List<String> topic = new ArrayList<String>();
	// topic.add(kafkaRequest.getTopic());
	// TopicMetadata topicMetadata = (consumer.send(new
	// TopicMetadataRequest(topic)))
	// .topicsMetadata().get(0);
	// consumer.close();
	// List<PartitionMetadata> partitionsMetadata =
	// topicMetadata.partitionsMetadata();
	//
	// for (PartitionMetadata partitionMetadata : partitionsMetadata) {
	// if (partitionMetadata.partitionId() == kafkaRequest.getPartition()) {
	// URI partitionURI = new URI("tcp://"
	// + partitionMetadata.leader().getConnectionString());
	// System.out.println("Old and new leader : " + partitionURI.toString() +
	// "   "
	// + kafkaRequest.getURI().toString());
	// if (partitionURI.equals(kafkaRequest.getURI()))
	// return;
	// else {
	// kafkaRequest.setURI(partitionURI);
	// // TODO : Change the EtlRequest getLastOffset to work
	// // for this too
	// System.out.println("Changing leader to new connection parameters : "
	// + partitionURI.toString());
	// consumer = new SimpleConsumer(kafkaRequest.getURI().getHost(),
	// kafkaRequest
	// .getURI().getPort(), CamusJob.getKafkaTimeoutValue(context),
	// CamusJob.getKafkaBufferSize(context),
	// CamusJob.getKafkaClientName(context));
	// // TODO : maxNumOffets value? What needs to be put here?
	// Map<TopicAndPartition, PartitionOffsetRequestInfo> offsetInfo = new
	// HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
	// offsetInfo.put(
	// new TopicAndPartition(kafkaRequest.getTopic(), kafkaRequest
	// .getPartition()), new PartitionOffsetRequestInfo(
	// kafka.api.OffsetRequest.LatestTime(), 1));
	// OffsetResponse response = consumer.getOffsetsBefore(new OffsetRequest(
	// offsetInfo, kafka.api.OffsetRequest.CurrentVersion(), CamusJob
	// .getKafkaClientName(context)));
	// long[] endOffset = response.offsets(kafkaRequest.getTopic(),
	// kafkaRequest.getPartition());
	// consumer.close();
	// long newLeaderLatestOffset = endOffset[0];
	// if (newLeaderLatestOffset < kafkaRequest.getOffset()) {
	// System.out
	// .println("Modified the request to start from the new partition and start reading from the offset : "
	// + newLeaderLatestOffset);
	// kafkaRequest.setOffset(newLeaderLatestOffset);
	// }
	// }
	// }
	// }
	// } catch (Exception e) {
	// System.out.println("Exception generated while checking the metadata in KafkaReader. "
	// + e.getLocalizedMessage());
	// return;
	// }
	//
	// }

	/**
	 * Returns the total bytes that will be fetched. This is calculated by
	 * taking the diffs of the offsets
	 * 
	 * @return
	 */
	public long getTotalBytes() {
		return (lastOffset > beginOffset) ? lastOffset - beginOffset : 0;
	}

	/**
	 * Returns the total bytes that have been fetched so far
	 * 
	 * @return
	 */
	public long getReadBytes() {
		return currentOffset - beginOffset;
	}

	/**
	 * Returns the number of events that have been read r
	 * 
	 * @return
	 */
	public long getCount() {
		return currentCount;
	}

	/**
	 * Returns the fetch time of the last fetch in ms
	 * 
	 * @return
	 */
	public long getFetchTime() {
		return lastFetchTime;
	}

	/**
	 * Returns the totalFetchTime in ms
	 * 
	 * @return
	 */
	public long getTotalFetchTime() {
		return totalFetchTime;
	}
}
