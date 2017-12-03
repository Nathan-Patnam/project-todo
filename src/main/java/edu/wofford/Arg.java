package edu.wofford;

import java.util.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;

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
  protected boolean required;

  /**
  * Constructor for an Arg object with the given name. By default, its data type is String and its description
  * us an empty String. 
  * @param  name, the String value that is the name of the Arg 
  * @return an Arg object 
  */
  public Arg(String name) {
    this(name, "", DataType.STRING);
  }

  /**
  * Constructor for an Arg object with the given name and description. By default, its data type is String.
  * @param  name, the String value that is the name of the Arg 
  *			description, the String alue that is the description of the Arg 
  * @return nothing
  */
  public Arg(String name, String description) {
    this(name, description, DataType.STRING);
  }

  /**
  * Constructor for an Arg object with the given name, description, and data type. 
  * @param name, the String value that is the name of the Arg object
  *		   description, the String value that is the description of the Arg object
  *		   dataType, the DataType value that is the data type of the Arg object
  * @return nothing 
  */
  public Arg(String name, String description, DataType dataType) {
    this.name = name;
    this.description = description;
    this.dataType = dataType;
    this.restrictedValues = new HashSet<>();
    this.required = true ;
  }

  /**
  * Sets the name of the Arg object
  * @param  argName, the String value that is the name of the Arg object
  * @return nothing 
  */
  public void setName(String argName) {
    this.name = argName;
  }

  /**
  * Returns the name of the Arg object
  * @param  none 
  * @return the String value that is the name of the Arg object
  */
  public String getName() {
    return this.name.replace("-", "");
  }

  /**
  * Sets the description of the Arg object 
  * @param  description, the String value that is the description of the Arg object
  * @return nothing 
  */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
  * Returns the description of the Arg object
  * @param  none 
  * @return the String value that is the description of the Arg object
  */
  public String getDescription() {
    return this.description;
  }

  /**
  * Sets the short form name of the Arg object 
  * @param  shortFormName, the String value that is the short form name of the Arg object
  * @return nothing 
  */
  public void setShortFormName(String shortFormName) {
    this.shortFormName = shortFormName;
  }

  /**
  * Returns the short form name of the Arg object
  * @param  none 
  * @return the String value that is the short form name of the Arg object
  */
  public String getShortFormName() {
    return this.shortFormName;
  }

  /**
  * Sets the data type of the Arg object 
  * @param dataType, the DataType value that is the data type of the Arg object
  * @return nothing 
  */
  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  /**
  * Sets the restricted values of the Arg object 
  * @param  restrictedValues, the String value that are the restricted values for the Arg object,
  *							  separated by spaces 
  * @return nothing 
  */
  public void setRestrictedValues(String restrictedValuesString) {
    this.allRestrictedValuesString = "";
    for (String value : restrictedValuesString.split(" ")) {
      this.restrictedValues.add(value);
      this.allRestrictedValuesString += value + " ";
    }
    this.allRestrictedValuesString = this.allRestrictedValuesString.trim();


  }

  /**
  * Sets the value of the Arg object 
  * @param value, the String value that is the value of the Arg object
  * @return nothing 
  */
  public void setValue(String value) {
    this.value = value;
  }

  /**
  * Returns the value of the Arg object 
  * @param  none
  * @return the String value that is the value of the Arg object
  */
  public String getValue() {
    return this.value;
  }

  /** 
  public <T> T getVal() {
    if (this.dataType == DataType.BOOLEAN) {
      return Boolean.parseBoolean(this.value);
    }
  }
  */

  /**
  * Sets the Arg as required  
  * @param  none
  * @return nothing 
  */
  public void makeArgRequired() {
    this.required = true;
  }

  /**
  * Checks to see if the Arg as required  
  * @param  none
  * @return boolean value indicating whether or not this Arg is required 
  */
  public boolean isArgRequired() {
    return this.required;
  }

  /**
  * Returns the data type of the Arg object 
  * @param  none
  * @return the DataType value that is the data type of the Arg object 
  */
  public DataType getDataType() {
    return this.dataType;
  }

  /**
  * Returns the HashSet of restricted values for this Arg object
  * @param  none
  * @return the HashSet of restricted values
  */
  public HashSet<String> getRestrictedValues() {
    return this.restrictedValues;
  }

  /**
  * Returns the a String of the restricted values for this Arg object
  * @param  none
  * @return a String of the restricted values
  */
  public String getRestrictedValuesString() {


    return this.allRestrictedValuesString;
  }

  




  public XMLStreamWriter writeArgXML(XMLStreamWriter streamWriter, ArgParser argparser) {
    try {
      streamWriter.writeCharacters("\n\t");
      streamWriter.writeStartElement("positional");

      streamWriter.writeCharacters("\n\t\t");
      streamWriter.writeStartElement("name");
      streamWriter.writeCharacters(name);
      streamWriter.writeEndElement();

      streamWriter.writeCharacters("\n\t\t");
      streamWriter.writeStartElement("datatype");
      streamWriter.writeCharacters(this.dataType.toString());
      streamWriter.writeEndElement();

      if (this.shortFormName != null) {
        streamWriter.writeCharacters("\n\t\t");
        streamWriter.writeStartElement("shortname");
        streamWriter.writeCharacters(this.shortFormName);
        streamWriter.writeEndElement();
      }

      if (this.description != null && this.description.length() > 0) {
        streamWriter.writeCharacters("\n\t\t");
        streamWriter.writeStartElement("description");
        streamWriter.writeCharacters(this.description);
        streamWriter.writeEndElement();

      }

      if (this.allRestrictedValuesString != null && this.allRestrictedValuesString.length() > 0) {
        streamWriter.writeCharacters("\n\t\t");
        streamWriter.writeStartElement("restrictedValues");
        streamWriter.writeCharacters(allRestrictedValuesString);
        streamWriter.writeEndElement();

      }

      streamWriter.writeCharacters("\n\t\t");
      streamWriter.writeStartElement("position");
      streamWriter.writeCharacters(String.valueOf(argparser.getPostionalArgNames().indexOf(name)+1));
      streamWriter.writeEndElement();

      streamWriter.writeCharacters("\n\t");
      streamWriter.writeEndElement();
   
    }
    catch (XMLStreamException e) {
      e.printStackTrace();
    } 

    return streamWriter;

  }

}