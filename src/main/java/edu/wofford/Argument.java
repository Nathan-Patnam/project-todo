package edu.wofford;

public class Argument {
  public enum DataType {
    STRING("string"), 
    INT("int"), 
    BOOLEAN("boolean"), 
    FLOAT("float");
    private DataType(String s) {
      description = s;
    }
    private String description;
    public String toString() { return description; }
  }

  protected String name;
  protected String description;
  protected DataType dataType;
  protected String value;

  public Argument(String name) {
    this(name, "", DataType.STRING);
  }

  public Argument(String name, String description) {
    this(name, description, DataType.STRING);
  }

  public Argument(String name, DataType dataType) {
    this(name, "", dataType);
  }

  public Argument(String name, String description, DataType dataType) {
    this.name = name;
    this.description = description;
    this.dataType = dataType;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  public void setValue(String value) {
    this.value = value;
  }



  public String getDescription() {
    return description;
  }

  public DataType getDataType() {
    return this.dataType;
  }

  public String getValue() {
    return value;
  }

}