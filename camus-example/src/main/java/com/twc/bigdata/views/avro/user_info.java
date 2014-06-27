/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.twc.bigdata.views.avro;  
@SuppressWarnings("all")
public class user_info extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"user_info\",\"namespace\":\"com.twc.bigdata.views.avro\",\"fields\":[{\"name\":\"user_guid\",\"type\":\"string\"},{\"name\":\"account_number_md5_salt_hashed\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"market\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"soa_division\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"latitude\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"longitude\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"city\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"state\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"account_number_am_hashed\",\"type\":[\"null\",\"string\"],\"default\":null}]}");
  @Deprecated public java.lang.CharSequence user_guid;
  @Deprecated public java.lang.CharSequence account_number_md5_salt_hashed;
  @Deprecated public java.lang.CharSequence market;
  @Deprecated public java.lang.CharSequence soa_division;
  @Deprecated public java.lang.Double latitude;
  @Deprecated public java.lang.Double longitude;
  @Deprecated public java.lang.CharSequence city;
  @Deprecated public java.lang.CharSequence state;
  @Deprecated public java.lang.CharSequence account_number_am_hashed;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return user_guid;
    case 1: return account_number_md5_salt_hashed;
    case 2: return market;
    case 3: return soa_division;
    case 4: return latitude;
    case 5: return longitude;
    case 6: return city;
    case 7: return state;
    case 8: return account_number_am_hashed;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: user_guid = (java.lang.CharSequence)value$; break;
    case 1: account_number_md5_salt_hashed = (java.lang.CharSequence)value$; break;
    case 2: market = (java.lang.CharSequence)value$; break;
    case 3: soa_division = (java.lang.CharSequence)value$; break;
    case 4: latitude = (java.lang.Double)value$; break;
    case 5: longitude = (java.lang.Double)value$; break;
    case 6: city = (java.lang.CharSequence)value$; break;
    case 7: state = (java.lang.CharSequence)value$; break;
    case 8: account_number_am_hashed = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'user_guid' field.
   */
  public java.lang.CharSequence getUserGuid() {
    return user_guid;
  }

  /**
   * Sets the value of the 'user_guid' field.
   * @param value the value to set.
   */
  public void setUserGuid(java.lang.CharSequence value) {
    this.user_guid = value;
  }

  /**
   * Gets the value of the 'account_number_md5_salt_hashed' field.
   */
  public java.lang.CharSequence getAccountNumberMd5SaltHashed() {
    return account_number_md5_salt_hashed;
  }

  /**
   * Sets the value of the 'account_number_md5_salt_hashed' field.
   * @param value the value to set.
   */
  public void setAccountNumberMd5SaltHashed(java.lang.CharSequence value) {
    this.account_number_md5_salt_hashed = value;
  }

  /**
   * Gets the value of the 'market' field.
   */
  public java.lang.CharSequence getMarket() {
    return market;
  }

  /**
   * Sets the value of the 'market' field.
   * @param value the value to set.
   */
  public void setMarket(java.lang.CharSequence value) {
    this.market = value;
  }

  /**
   * Gets the value of the 'soa_division' field.
   */
  public java.lang.CharSequence getSoaDivision() {
    return soa_division;
  }

  /**
   * Sets the value of the 'soa_division' field.
   * @param value the value to set.
   */
  public void setSoaDivision(java.lang.CharSequence value) {
    this.soa_division = value;
  }

  /**
   * Gets the value of the 'latitude' field.
   */
  public java.lang.Double getLatitude() {
    return latitude;
  }

  /**
   * Sets the value of the 'latitude' field.
   * @param value the value to set.
   */
  public void setLatitude(java.lang.Double value) {
    this.latitude = value;
  }

  /**
   * Gets the value of the 'longitude' field.
   */
  public java.lang.Double getLongitude() {
    return longitude;
  }

  /**
   * Sets the value of the 'longitude' field.
   * @param value the value to set.
   */
  public void setLongitude(java.lang.Double value) {
    this.longitude = value;
  }

  /**
   * Gets the value of the 'city' field.
   */
  public java.lang.CharSequence getCity() {
    return city;
  }

  /**
   * Sets the value of the 'city' field.
   * @param value the value to set.
   */
  public void setCity(java.lang.CharSequence value) {
    this.city = value;
  }

  /**
   * Gets the value of the 'state' field.
   */
  public java.lang.CharSequence getState() {
    return state;
  }

  /**
   * Sets the value of the 'state' field.
   * @param value the value to set.
   */
  public void setState(java.lang.CharSequence value) {
    this.state = value;
  }

  /**
   * Gets the value of the 'account_number_am_hashed' field.
   */
  public java.lang.CharSequence getAccountNumberAmHashed() {
    return account_number_am_hashed;
  }

  /**
   * Sets the value of the 'account_number_am_hashed' field.
   * @param value the value to set.
   */
  public void setAccountNumberAmHashed(java.lang.CharSequence value) {
    this.account_number_am_hashed = value;
  }

  /** Creates a new user_info RecordBuilder */
  public static com.twc.bigdata.views.avro.user_info.Builder newBuilder() {
    return new com.twc.bigdata.views.avro.user_info.Builder();
  }
  
  /** Creates a new user_info RecordBuilder by copying an existing Builder */
  public static com.twc.bigdata.views.avro.user_info.Builder newBuilder(com.twc.bigdata.views.avro.user_info.Builder other) {
    return new com.twc.bigdata.views.avro.user_info.Builder(other);
  }
  
  /** Creates a new user_info RecordBuilder by copying an existing user_info instance */
  public static com.twc.bigdata.views.avro.user_info.Builder newBuilder(com.twc.bigdata.views.avro.user_info other) {
    return new com.twc.bigdata.views.avro.user_info.Builder(other);
  }
  
  /**
   * RecordBuilder for user_info instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<user_info>
    implements org.apache.avro.data.RecordBuilder<user_info> {

    private java.lang.CharSequence user_guid;
    private java.lang.CharSequence account_number_md5_salt_hashed;
    private java.lang.CharSequence market;
    private java.lang.CharSequence soa_division;
    private java.lang.Double latitude;
    private java.lang.Double longitude;
    private java.lang.CharSequence city;
    private java.lang.CharSequence state;
    private java.lang.CharSequence account_number_am_hashed;

    /** Creates a new Builder */
    private Builder() {
      super(com.twc.bigdata.views.avro.user_info.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.twc.bigdata.views.avro.user_info.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing user_info instance */
    private Builder(com.twc.bigdata.views.avro.user_info other) {
            super(com.twc.bigdata.views.avro.user_info.SCHEMA$);
      if (isValidValue(fields()[0], other.user_guid)) {
        this.user_guid = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.user_guid);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.account_number_md5_salt_hashed)) {
        this.account_number_md5_salt_hashed = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.account_number_md5_salt_hashed);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.market)) {
        this.market = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.market);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.soa_division)) {
        this.soa_division = (java.lang.CharSequence) data().deepCopy(fields()[3].schema(), other.soa_division);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.latitude)) {
        this.latitude = (java.lang.Double) data().deepCopy(fields()[4].schema(), other.latitude);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.longitude)) {
        this.longitude = (java.lang.Double) data().deepCopy(fields()[5].schema(), other.longitude);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.city)) {
        this.city = (java.lang.CharSequence) data().deepCopy(fields()[6].schema(), other.city);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.state)) {
        this.state = (java.lang.CharSequence) data().deepCopy(fields()[7].schema(), other.state);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.account_number_am_hashed)) {
        this.account_number_am_hashed = (java.lang.CharSequence) data().deepCopy(fields()[8].schema(), other.account_number_am_hashed);
        fieldSetFlags()[8] = true;
      }
    }

    /** Gets the value of the 'user_guid' field */
    public java.lang.CharSequence getUserGuid() {
      return user_guid;
    }
    
    /** Sets the value of the 'user_guid' field */
    public com.twc.bigdata.views.avro.user_info.Builder setUserGuid(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.user_guid = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'user_guid' field has been set */
    public boolean hasUserGuid() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'user_guid' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearUserGuid() {
      user_guid = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'account_number_md5_salt_hashed' field */
    public java.lang.CharSequence getAccountNumberMd5SaltHashed() {
      return account_number_md5_salt_hashed;
    }
    
    /** Sets the value of the 'account_number_md5_salt_hashed' field */
    public com.twc.bigdata.views.avro.user_info.Builder setAccountNumberMd5SaltHashed(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.account_number_md5_salt_hashed = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'account_number_md5_salt_hashed' field has been set */
    public boolean hasAccountNumberMd5SaltHashed() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'account_number_md5_salt_hashed' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearAccountNumberMd5SaltHashed() {
      account_number_md5_salt_hashed = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'market' field */
    public java.lang.CharSequence getMarket() {
      return market;
    }
    
    /** Sets the value of the 'market' field */
    public com.twc.bigdata.views.avro.user_info.Builder setMarket(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.market = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'market' field has been set */
    public boolean hasMarket() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'market' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearMarket() {
      market = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'soa_division' field */
    public java.lang.CharSequence getSoaDivision() {
      return soa_division;
    }
    
    /** Sets the value of the 'soa_division' field */
    public com.twc.bigdata.views.avro.user_info.Builder setSoaDivision(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.soa_division = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'soa_division' field has been set */
    public boolean hasSoaDivision() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'soa_division' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearSoaDivision() {
      soa_division = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'latitude' field */
    public java.lang.Double getLatitude() {
      return latitude;
    }
    
    /** Sets the value of the 'latitude' field */
    public com.twc.bigdata.views.avro.user_info.Builder setLatitude(java.lang.Double value) {
      validate(fields()[4], value);
      this.latitude = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'latitude' field has been set */
    public boolean hasLatitude() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'latitude' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearLatitude() {
      latitude = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'longitude' field */
    public java.lang.Double getLongitude() {
      return longitude;
    }
    
    /** Sets the value of the 'longitude' field */
    public com.twc.bigdata.views.avro.user_info.Builder setLongitude(java.lang.Double value) {
      validate(fields()[5], value);
      this.longitude = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'longitude' field has been set */
    public boolean hasLongitude() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'longitude' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearLongitude() {
      longitude = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'city' field */
    public java.lang.CharSequence getCity() {
      return city;
    }
    
    /** Sets the value of the 'city' field */
    public com.twc.bigdata.views.avro.user_info.Builder setCity(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.city = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'city' field has been set */
    public boolean hasCity() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'city' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearCity() {
      city = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'state' field */
    public java.lang.CharSequence getState() {
      return state;
    }
    
    /** Sets the value of the 'state' field */
    public com.twc.bigdata.views.avro.user_info.Builder setState(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.state = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'state' field has been set */
    public boolean hasState() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'state' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearState() {
      state = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'account_number_am_hashed' field */
    public java.lang.CharSequence getAccountNumberAmHashed() {
      return account_number_am_hashed;
    }
    
    /** Sets the value of the 'account_number_am_hashed' field */
    public com.twc.bigdata.views.avro.user_info.Builder setAccountNumberAmHashed(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.account_number_am_hashed = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'account_number_am_hashed' field has been set */
    public boolean hasAccountNumberAmHashed() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'account_number_am_hashed' field */
    public com.twc.bigdata.views.avro.user_info.Builder clearAccountNumberAmHashed() {
      account_number_am_hashed = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    @Override
    public user_info build() {
      try {
        user_info record = new user_info();
        record.user_guid = fieldSetFlags()[0] ? this.user_guid : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.account_number_md5_salt_hashed = fieldSetFlags()[1] ? this.account_number_md5_salt_hashed : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.market = fieldSetFlags()[2] ? this.market : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.soa_division = fieldSetFlags()[3] ? this.soa_division : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.latitude = fieldSetFlags()[4] ? this.latitude : (java.lang.Double) defaultValue(fields()[4]);
        record.longitude = fieldSetFlags()[5] ? this.longitude : (java.lang.Double) defaultValue(fields()[5]);
        record.city = fieldSetFlags()[6] ? this.city : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.state = fieldSetFlags()[7] ? this.state : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.account_number_am_hashed = fieldSetFlags()[8] ? this.account_number_am_hashed : (java.lang.CharSequence) defaultValue(fields()[8]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
