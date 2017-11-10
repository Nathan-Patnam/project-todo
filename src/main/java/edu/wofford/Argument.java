package edu.wofford;

import java.util.*;

public class Argument {
  public enum DataType {
    STRING, INT, BOOLEAN, FLOAT
  }

  private String argumentName;
  private String argumentDescription;
  private DataType argumentDataType;
  private String argValue;
  //private List<String> argDataTypes = new ArrayList<String>(Arrays.asList("string", "float", "int", "boolean"));

  public Argument(String argname) {
    this.argumentName = argname;
    this.argumentDataType = DataType.STRING;
  }

  public Argument(String argname, String argDescription) {
    this.argumentName = argname;
    this.argumentDescription=argDescription;
    this.argumentDataType = DataType.STRING;
  }

  public Argument(String argname, DataType dataType) {
    this.argumentName = argname;
    this.argumentDataType = dataType;
  }

  public Argument(String argname, String description, DataType dataType) {
    this.argumentName = argname;
    this.argumentDescription = description;
    this.argumentDataType = dataType;
  }

  public void setArgumentDescription(String argumentDescription) {
    this.argumentDescription = argumentDescription;
  }

  public void setArgumentDataType(DataType dataType) {
    this.argumentDataType = dataType;
  }

  public void setArgumentValue(String argValue) {
    this.argValue = argValue;
  }

  public String getArgumentName() {
    return argumentName;
  }

  public String getArgumentDescription() {
    return argumentDescription;
  }

  public DataType getArgumentDataType() {
    return this.argumentDataType;
  }

  public String getargumentDataTypeString() {
    String datatype = "";
    switch (this.argumentDataType) {
    case STRING: datatype= "string";
    break;
    case BOOLEAN: datatype= "boolean";
    break;
    case FLOAT: datatype= "float";
    break;
    case INT: datatype= "int";
    break;

    }
    return datatype;
  }


  public String getArgumentValue() {
    return argValue;
  }

}