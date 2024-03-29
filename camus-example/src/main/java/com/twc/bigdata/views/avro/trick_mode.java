/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.twc.bigdata.views.avro;  
@SuppressWarnings("all")
public class trick_mode extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"trick_mode\",\"namespace\":\"com.twc.bigdata.views.avro\",\"fields\":[{\"name\":\"count_blocked_seeks\",\"type\":\"int\"}]}");
  @Deprecated public int count_blocked_seeks;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return count_blocked_seeks;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: count_blocked_seeks = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'count_blocked_seeks' field.
   */
  public java.lang.Integer getCountBlockedSeeks() {
    return count_blocked_seeks;
  }

  /**
   * Sets the value of the 'count_blocked_seeks' field.
   * @param value the value to set.
   */
  public void setCountBlockedSeeks(java.lang.Integer value) {
    this.count_blocked_seeks = value;
  }

  /** Creates a new trick_mode RecordBuilder */
  public static com.twc.bigdata.views.avro.trick_mode.Builder newBuilder() {
    return new com.twc.bigdata.views.avro.trick_mode.Builder();
  }
  
  /** Creates a new trick_mode RecordBuilder by copying an existing Builder */
  public static com.twc.bigdata.views.avro.trick_mode.Builder newBuilder(com.twc.bigdata.views.avro.trick_mode.Builder other) {
    return new com.twc.bigdata.views.avro.trick_mode.Builder(other);
  }
  
  /** Creates a new trick_mode RecordBuilder by copying an existing trick_mode instance */
  public static com.twc.bigdata.views.avro.trick_mode.Builder newBuilder(com.twc.bigdata.views.avro.trick_mode other) {
    return new com.twc.bigdata.views.avro.trick_mode.Builder(other);
  }
  
  /**
   * RecordBuilder for trick_mode instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<trick_mode>
    implements org.apache.avro.data.RecordBuilder<trick_mode> {

    private int count_blocked_seeks;

    /** Creates a new Builder */
    private Builder() {
      super(com.twc.bigdata.views.avro.trick_mode.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.twc.bigdata.views.avro.trick_mode.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing trick_mode instance */
    private Builder(com.twc.bigdata.views.avro.trick_mode other) {
            super(com.twc.bigdata.views.avro.trick_mode.SCHEMA$);
      if (isValidValue(fields()[0], other.count_blocked_seeks)) {
        this.count_blocked_seeks = (java.lang.Integer) data().deepCopy(fields()[0].schema(), other.count_blocked_seeks);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'count_blocked_seeks' field */
    public java.lang.Integer getCountBlockedSeeks() {
      return count_blocked_seeks;
    }
    
    /** Sets the value of the 'count_blocked_seeks' field */
    public com.twc.bigdata.views.avro.trick_mode.Builder setCountBlockedSeeks(int value) {
      validate(fields()[0], value);
      this.count_blocked_seeks = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'count_blocked_seeks' field has been set */
    public boolean hasCountBlockedSeeks() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'count_blocked_seeks' field */
    public com.twc.bigdata.views.avro.trick_mode.Builder clearCountBlockedSeeks() {
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public trick_mode build() {
      try {
        trick_mode record = new trick_mode();
        record.count_blocked_seeks = fieldSetFlags()[0] ? this.count_blocked_seeks : (java.lang.Integer) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
