package com.linkedin.camus.etl.kafka.coders;

import java.io.IOException;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;

import com.linkedin.camus.coders.CamusWrapper;
import com.linkedin.camus.coders.MessageDecoder;
import com.linkedin.camus.coders.MessageDecoderException;
import com.linkedin.camus.schemaregistry.CachedSchemaRegistry;
import com.linkedin.camus.schemaregistry.SchemaRegistry;
import org.apache.hadoop.io.Text;

public class KafkaAvroMessageDecoder extends MessageDecoder<byte[], Record> {
	protected DecoderFactory decoderFactory;
	protected SchemaRegistry<Schema> registry;
	private Schema latestSchema;
	
	@Override
	public void init(Properties props, String topicName) {
	    super.init(props, topicName);
	    try {
            SchemaRegistry<Schema> registry = (SchemaRegistry<Schema>) Class.forName(props.getProperty(KafkaAvroMessageEncoder.KAFKA_MESSAGE_CODER_SCHEMA_REGISTRY_CLASS)).newInstance();
            registry.init(props);
            this.registry = new CachedSchemaRegistry<Schema>(registry);
            this.latestSchema = registry.getLatestSchemaByTopic(topicName).getSchema();
        } catch (Exception e) {
        	e.fillInStackTrace();
        	StackTraceElement[] stackTraces = e.getStackTrace();
        	for (StackTraceElement element : stackTraces) {
        		System.out.println(element.toString());
        	}
            throw new MessageDecoderException(e);
        }

        decoderFactory = DecoderFactory.get();
	}

	public CamusWrapper<Record> decode(byte[] payload) {
		try {
			Decoder decoder = DecoderFactory.get().binaryDecoder(payload, null);
			DatumReader<Record> reader = new GenericDatumReader<Record>(
					latestSchema, latestSchema);
			Record result = reader.read(null, decoder);
			return new CamusAvroWrapper(result);
		} catch (IOException e) {
			throw new MessageDecoderException(e);
		}
	}

	public static class CamusAvroWrapper extends CamusWrapper<Record> {

	    public CamusAvroWrapper(Record record) {
            super(record);
            Record header = (Record) super.getRecord().get("header");
   	        if (header != null) {
               if (header.get("server") != null) {
                   put(new Text("server"), new Text(header.get("server").toString()));
               }
               if (header.get("service") != null) {
                   put(new Text("service"), new Text(header.get("service").toString()));
               }
            }
        }
	    
	    @Override
	    public long getTimestamp() {
	        Record header = (Record) super.getRecord().get("header");

	        if (header != null && header.get("time") != null) {
	            return (Long) header.get("time");
	        } else if (super.getRecord().get("timestamp") != null) {
	            return (Long) super.getRecord().get("timestamp");
	        } else {
	            return System.currentTimeMillis();
	        }
	    }
	}
}
