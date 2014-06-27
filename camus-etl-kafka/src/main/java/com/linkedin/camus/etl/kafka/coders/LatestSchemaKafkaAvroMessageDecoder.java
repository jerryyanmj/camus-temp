package com.linkedin.camus.etl.kafka.coders;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;

import kafka.message.Message;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.hadoop.conf.Configuration;

import com.linkedin.camus.coders.CamusWrapper;
import com.linkedin.camus.coders.MessageDecoder;


import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.twc.eventgateway2.avro.enriched_event;

public class LatestSchemaKafkaAvroMessageDecoder extends MessageDecoder
{
	
	@Override
	public CamusWrapper<Record> decode(Object payload)
	{
		try
		{
			//GenericDatumReader<Record> reader = new GenericDatumReader<Record>();
			
			//Schema schema = super.registry.getLatestSchemaByTopic(super.topicName).getSchema();
			
			///camus-example/src/main/avro
//			File schemaFile = new File("camus-example/src/main/avro/EgwToHdfs.avsc");
			
			//System.out.println("****************************** FILE PATH ****************************************");
			//System.out.println(schemaFile.getAbsolutePath());
			//System.out.println(schemaFile.getCanonicalPath());
			
			Schema schema = enriched_event.SCHEMA$;
			
			ByteArrayOutputStream b = new ByteArrayOutputStream();
	        ObjectOutputStream o = new ObjectOutputStream(b);
	        o.writeObject(payload);
			
			DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
	        Decoder decoder = DecoderFactory.get().binaryDecoder(b.toByteArray(), null);
	        GenericRecord result = reader.read(null, decoder);
			reader.setSchema(schema);
			
			return new CamusWrapper<Record>((Record) result);
			
			//return new CamusWrapper<Record>(reader.read(
            //        null, 
            //        decoderFactory.jsonDecoder(
            //                schema, 
            //                new String(
            //                        payload, 
            //                        //Message.payloadOffset(message.magic()),
            //                        Message.MagicOffset(),
            //                        payload.length
            //                )
            //        )
            //));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

/*	@Override
	public CamusWrapper<Record> decode(byte[] payload)
	{
		try
		{
			GenericDatumReader<Record> reader = new GenericDatumReader<Record>();
			
			Schema schema = super.registry.getLatestSchemaByTopic(super.topicName).getSchema();
			
			reader.setSchema(schema);
			
			return new CamusWrapper<Record>(reader.read(
                    null, 
                    decoderFactory.jsonDecoder(
                            schema, 
                            new String(
                                    payload, 
                                    //Message.payloadOffset(message.magic()),
                                    Message.MagicOffset(),
                                    payload.length
                            )
                    )
            ));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}*/
}