package com.linkedin.camus.schemaregistry;

import org.apache.avro.Schema;
import org.apache.hadoop.conf.Configuration;
import org.twc.eventgateway2.avro.enriched_event;
import com.twc.bigdata.views.avro.View;


public class EnrichedEventSchemaRegistry extends MemorySchemaRegistry<Schema> {
	
	public EnrichedEventSchemaRegistry(Configuration conf) {
		this();
	}
	
	public EnrichedEventSchemaRegistry() {
		super();
		super.register("prod-eg_v2_2-big_data_v0.3.256-68d94", enriched_event.SCHEMA$);
		super.register("vodbeta_v1", View.SCHEMA$);
		super.register("channelbeta_v1", View.SCHEMA$);
	}
}
