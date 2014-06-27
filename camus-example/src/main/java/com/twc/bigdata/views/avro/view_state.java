/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.twc.bigdata.views.avro;  
@SuppressWarnings("all")
public class view_state extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"view_state\",\"namespace\":\"com.twc.bigdata.views.avro\",\"fields\":[{\"name\":\"last_event_seen_msec\",\"type\":[\"long\",\"null\"],\"default\":0},{\"name\":\"stopped_event_seen_msec\",\"type\":[\"long\",\"null\"],\"default\":0},{\"name\":\"emit_msec\",\"type\":[\"long\",\"null\"],\"default\":0},{\"name\":\"current_bit_rate\",\"type\":[\"int\",\"null\"],\"default\":0},{\"name\":\"play_state\",\"type\":[\"int\",\"null\"],\"default\":0},{\"name\":\"is_buffering\",\"type\":[\"null\",\"boolean\"],\"default\":null},{\"name\":\"is_complete\",\"type\":[\"null\",\"boolean\"],\"default\":null}]}");
  @Deprecated public java.lang.Long last_event_seen_msec;
  @Deprecated public java.lang.Long stopped_event_seen_msec;
  @Deprecated public java.lang.Long emit_msec;
  @Deprecated public java.lang.Integer current_bit_rate;
  @Deprecated public java.lang.Integer play_state;
  @Deprecated public java.lang.Boolean is_buffering;
  @Deprecated public java.lang.Boolean is_complete;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return last_event_seen_msec;
    case 1: return stopped_event_seen_msec;
    case 2: return emit_msec;
    case 3: return current_bit_rate;
    case 4: return play_state;
    case 5: return is_buffering;
    case 6: return is_complete;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: last_event_seen_msec = (java.lang.Long)value$; break;
    case 1: stopped_event_seen_msec = (java.lang.Long)value$; break;
    case 2: emit_msec = (java.lang.Long)value$; break;
    case 3: current_bit_rate = (java.lang.Integer)value$; break;
    case 4: play_state = (java.lang.Integer)value$; break;
    case 5: is_buffering = (java.lang.Boolean)value$; break;
    case 6: is_complete = (java.lang.Boolean)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'last_event_seen_msec' field.
   */
  public java.lang.Long getLastEventSeenMsec() {
    return last_event_seen_msec;
  }

  /**
   * Sets the value of the 'last_event_seen_msec' field.
   * @param value the value to set.
   */
  public void setLastEventSeenMsec(java.lang.Long value) {
    this.last_event_seen_msec = value;
  }

  /**
   * Gets the value of the 'stopped_event_seen_msec' field.
   */
  public java.lang.Long getStoppedEventSeenMsec() {
    return stopped_event_seen_msec;
  }

  /**
   * Sets the value of the 'stopped_event_seen_msec' field.
   * @param value the value to set.
   */
  public void setStoppedEventSeenMsec(java.lang.Long value) {
    this.stopped_event_seen_msec = value;
  }

  /**
   * Gets the value of the 'emit_msec' field.
   */
  public java.lang.Long getEmitMsec() {
    return emit_msec;
  }

  /**
   * Sets the value of the 'emit_msec' field.
   * @param value the value to set.
   */
  public void setEmitMsec(java.lang.Long value) {
    this.emit_msec = value;
  }

  /**
   * Gets the value of the 'current_bit_rate' field.
   */
  public java.lang.Integer getCurrentBitRate() {
    return current_bit_rate;
  }

  /**
   * Sets the value of the 'current_bit_rate' field.
   * @param value the value to set.
   */
  public void setCurrentBitRate(java.lang.Integer value) {
    this.current_bit_rate = value;
  }

  /**
   * Gets the value of the 'play_state' field.
   */
  public java.lang.Integer getPlayState() {
    return play_state;
  }

  /**
   * Sets the value of the 'play_state' field.
   * @param value the value to set.
   */
  public void setPlayState(java.lang.Integer value) {
    this.play_state = value;
  }

  /**
   * Gets the value of the 'is_buffering' field.
   */
  public java.lang.Boolean getIsBuffering() {
    return is_buffering;
  }

  /**
   * Sets the value of the 'is_buffering' field.
   * @param value the value to set.
   */
  public void setIsBuffering(java.lang.Boolean value) {
    this.is_buffering = value;
  }

  /**
   * Gets the value of the 'is_complete' field.
   */
  public java.lang.Boolean getIsComplete() {
    return is_complete;
  }

  /**
   * Sets the value of the 'is_complete' field.
   * @param value the value to set.
   */
  public void setIsComplete(java.lang.Boolean value) {
    this.is_complete = value;
  }

  /** Creates a new view_state RecordBuilder */
  public static com.twc.bigdata.views.avro.view_state.Builder newBuilder() {
    return new com.twc.bigdata.views.avro.view_state.Builder();
  }
  
  /** Creates a new view_state RecordBuilder by copying an existing Builder */
  public static com.twc.bigdata.views.avro.view_state.Builder newBuilder(com.twc.bigdata.views.avro.view_state.Builder other) {
    return new com.twc.bigdata.views.avro.view_state.Builder(other);
  }
  
  /** Creates a new view_state RecordBuilder by copying an existing view_state instance */
  public static com.twc.bigdata.views.avro.view_state.Builder newBuilder(com.twc.bigdata.views.avro.view_state other) {
    return new com.twc.bigdata.views.avro.view_state.Builder(other);
  }
  
  /**
   * RecordBuilder for view_state instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<view_state>
    implements org.apache.avro.data.RecordBuilder<view_state> {

    private java.lang.Long last_event_seen_msec;
    private java.lang.Long stopped_event_seen_msec;
    private java.lang.Long emit_msec;
    private java.lang.Integer current_bit_rate;
    private java.lang.Integer play_state;
    private java.lang.Boolean is_buffering;
    private java.lang.Boolean is_complete;

    /** Creates a new Builder */
    private Builder() {
      super(com.twc.bigdata.views.avro.view_state.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.twc.bigdata.views.avro.view_state.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing view_state instance */
    private Builder(com.twc.bigdata.views.avro.view_state other) {
            super(com.twc.bigdata.views.avro.view_state.SCHEMA$);
      if (isValidValue(fields()[0], other.last_event_seen_msec)) {
        this.last_event_seen_msec = (java.lang.Long) data().deepCopy(fields()[0].schema(), other.last_event_seen_msec);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.stopped_event_seen_msec)) {
        this.stopped_event_seen_msec = (java.lang.Long) data().deepCopy(fields()[1].schema(), other.stopped_event_seen_msec);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.emit_msec)) {
        this.emit_msec = (java.lang.Long) data().deepCopy(fields()[2].schema(), other.emit_msec);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.current_bit_rate)) {
        this.current_bit_rate = (java.lang.Integer) data().deepCopy(fields()[3].schema(), other.current_bit_rate);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.play_state)) {
        this.play_state = (java.lang.Integer) data().deepCopy(fields()[4].schema(), other.play_state);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.is_buffering)) {
        this.is_buffering = (java.lang.Boolean) data().deepCopy(fields()[5].schema(), other.is_buffering);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.is_complete)) {
        this.is_complete = (java.lang.Boolean) data().deepCopy(fields()[6].schema(), other.is_complete);
        fieldSetFlags()[6] = true;
      }
    }

    /** Gets the value of the 'last_event_seen_msec' field */
    public java.lang.Long getLastEventSeenMsec() {
      return last_event_seen_msec;
    }
    
    /** Sets the value of the 'last_event_seen_msec' field */
    public com.twc.bigdata.views.avro.view_state.Builder setLastEventSeenMsec(java.lang.Long value) {
      validate(fields()[0], value);
      this.last_event_seen_msec = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'last_event_seen_msec' field has been set */
    public boolean hasLastEventSeenMsec() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'last_event_seen_msec' field */
    public com.twc.bigdata.views.avro.view_state.Builder clearLastEventSeenMsec() {
      last_event_seen_msec = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'stopped_event_seen_msec' field */
    public java.lang.Long getStoppedEventSeenMsec() {
      return stopped_event_seen_msec;
    }
    
    /** Sets the value of the 'stopped_event_seen_msec' field */
    public com.twc.bigdata.views.avro.view_state.Builder setStoppedEventSeenMsec(java.lang.Long value) {
      validate(fields()[1], value);
      this.stopped_event_seen_msec = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'stopped_event_seen_msec' field has been set */
    public boolean hasStoppedEventSeenMsec() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'stopped_event_seen_msec' field */
    public com.twc.bigdata.views.avro.view_state.Builder clearStoppedEventSeenMsec() {
      stopped_event_seen_msec = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'emit_msec' field */
    public java.lang.Long getEmitMsec() {
      return emit_msec;
    }
    
    /** Sets the value of the 'emit_msec' field */
    public com.twc.bigdata.views.avro.view_state.Builder setEmitMsec(java.lang.Long value) {
      validate(fields()[2], value);
      this.emit_msec = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'emit_msec' field has been set */
    public boolean hasEmitMsec() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'emit_msec' field */
    public com.twc.bigdata.views.avro.view_state.Builder clearEmitMsec() {
      emit_msec = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'current_bit_rate' field */
    public java.lang.Integer getCurrentBitRate() {
      return current_bit_rate;
    }
    
    /** Sets the value of the 'current_bit_rate' field */
    public com.twc.bigdata.views.avro.view_state.Builder setCurrentBitRate(java.lang.Integer value) {
      validate(fields()[3], value);
      this.current_bit_rate = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'current_bit_rate' field has been set */
    public boolean hasCurrentBitRate() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'current_bit_rate' field */
    public com.twc.bigdata.views.avro.view_state.Builder clearCurrentBitRate() {
      current_bit_rate = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'play_state' field */
    public java.lang.Integer getPlayState() {
      return play_state;
    }
    
    /** Sets the value of the 'play_state' field */
    public com.twc.bigdata.views.avro.view_state.Builder setPlayState(java.lang.Integer value) {
      validate(fields()[4], value);
      this.play_state = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'play_state' field has been set */
    public boolean hasPlayState() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'play_state' field */
    public com.twc.bigdata.views.avro.view_state.Builder clearPlayState() {
      play_state = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'is_buffering' field */
    public java.lang.Boolean getIsBuffering() {
      return is_buffering;
    }
    
    /** Sets the value of the 'is_buffering' field */
    public com.twc.bigdata.views.avro.view_state.Builder setIsBuffering(java.lang.Boolean value) {
      validate(fields()[5], value);
      this.is_buffering = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'is_buffering' field has been set */
    public boolean hasIsBuffering() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'is_buffering' field */
    public com.twc.bigdata.views.avro.view_state.Builder clearIsBuffering() {
      is_buffering = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'is_complete' field */
    public java.lang.Boolean getIsComplete() {
      return is_complete;
    }
    
    /** Sets the value of the 'is_complete' field */
    public com.twc.bigdata.views.avro.view_state.Builder setIsComplete(java.lang.Boolean value) {
      validate(fields()[6], value);
      this.is_complete = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'is_complete' field has been set */
    public boolean hasIsComplete() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'is_complete' field */
    public com.twc.bigdata.views.avro.view_state.Builder clearIsComplete() {
      is_complete = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    public view_state build() {
      try {
        view_state record = new view_state();
        record.last_event_seen_msec = fieldSetFlags()[0] ? this.last_event_seen_msec : (java.lang.Long) defaultValue(fields()[0]);
        record.stopped_event_seen_msec = fieldSetFlags()[1] ? this.stopped_event_seen_msec : (java.lang.Long) defaultValue(fields()[1]);
        record.emit_msec = fieldSetFlags()[2] ? this.emit_msec : (java.lang.Long) defaultValue(fields()[2]);
        record.current_bit_rate = fieldSetFlags()[3] ? this.current_bit_rate : (java.lang.Integer) defaultValue(fields()[3]);
        record.play_state = fieldSetFlags()[4] ? this.play_state : (java.lang.Integer) defaultValue(fields()[4]);
        record.is_buffering = fieldSetFlags()[5] ? this.is_buffering : (java.lang.Boolean) defaultValue(fields()[5]);
        record.is_complete = fieldSetFlags()[6] ? this.is_complete : (java.lang.Boolean) defaultValue(fields()[6]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
