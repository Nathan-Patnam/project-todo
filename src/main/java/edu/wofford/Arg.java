package edu.wofford;

import java.util.*;

public class Arg {
  public enum DataType {
    STRING("string"), INT("int"), BOOLEAN("boolean"), FLOAT("float");
    private DataType(String s) {
      description = s;
    }

    private String description;

    public String toString() {
      return description;
    }
  }

  protected String name;
  protected String description;
  protected DataType dataType;
  protected String value;
  protected String shortFormName;
  protected HashSet<String> restrictedValues;
  protected String allRestrictedValuesString;

  public Arg(String name, String description, DataType dataType) {
    this.name = name;
    this.description = description;
    this.dataType = dataType;
    this.restrictedValues = new HashSet<>();
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setShortFormName(String shortFormName) {
    this.shortFormName = shortFormName;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  public void setRestrictedValues(String restrictedValues) {
    for (String value : restrictedValues.split(" ")) {
    this.restrictedValues.add(value);
    }

  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return this.description;
  }

  public DataType getDataType() {
    return this.dataType;
  }

  public String getValue() {
    return this.value;
  }

  public String getShortFormName() {
    return this.shortFormName;

  }

  public HashSet<String> getRestrictedValues() {
    return this.restrictedValues;
  }

  public String getRestrictedValuesString() {
    this.allRestrictedValuesString = "";

    if (restrictedValues.size() > 0) {
      for (String restrictedValue : restrictedValues) {
        this.allRestrictedValuesString += restrictedValue + " ";
      }
      this.allRestrictedValuesString= this.allRestrictedValuesString.trim();

    }

    return this.allRestrictedValuesString;
  }

}