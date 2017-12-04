package edu.wofford;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.*;

/**
* <pre>
* Subclass of Arg for optional arguments.
* For example:
*  {@code 
*	  newOptArg = OptArg("shape", "the shape of the thing to calculate the volume of");
*	  newOptArg.setShortFormName("S"); 
* 	  newOptArg.setRestrictedValues("cone cube ellipsoid cylinder");
*
*	  newOptArg.getDescription();
*	  newOptArg.getShortFormName();
*   }
* 	 Would return "the shape of the thing to calculate the volume of" and "S" respectively. 
* </pre>
*/
public class OptArg extends Arg {
	
    /**
    * Constructor for an OptArg object with the given name and default value. By default, its data type 
	* is String, and its description is an empty String. 
    * @param  name, the String value that is the name of the OptArg 
    *		  defaultValue, the String value that is the default value of the OptArg 
    * @return nothing
    */
    OptArg(String name, String defaultValue) {
        this(name, defaultValue, Arg.DataType.STRING, "");
    }

	/**
    * Constructor for an OptArg object with the given name, default value, and description. By default, 
	* its data type is String.
    * @param  name, the String value that is the name of the OptArg 
    *		  defaultValue, the String value that is the default value of the OptArg 
	*		  description, the String value that is the description of the OptArg
    * @return nothing
    */
    OptArg(String name, String defaultValue, String description) {
        this(name, defaultValue, Arg.DataType.STRING, description);
    }

	/**
    * Constructor for an OptArg object with the given name, default value, and data type. Since it is 
	* unspecified, the decsription is initialized as an empty String. 
    * @param  name, the String value that is the name of the OptArg 
    *		  defaultValue, the String value that is the default value of the OptArg 
	*		  dataType, the DataType value that is the data type of the OptArg
    * @return nothing
    */
    OptArg(String optionalArgumentName, String defaultValue, Arg.DataType dataType) {
        this(optionalArgumentName, defaultValue, dataType, "");
    }

	/**
    * Constructor for an OptArg object with the given name, default value, data type, and description. 
    * @param  name, the String value that is the name of the OptArg 
    *		  defaultValue, the String value that is the default value of the OptArg 
	*		  dataType, the DataType value that is the data type of the OptArg
	*		  description, the String value that is teh description of the OptArg
    * @return nothing
    */
    OptArg(String optionalArgumentName, String defaultValue, Arg.DataType datatype, String description) {
        super("--" + optionalArgumentName, description, datatype);
        this.value = defaultValue;
        this.required = false;

    }

    /**
    * Constructor for an OptArg flag object with the given name, and data type. 
    * @param  optionalArgumentName, the String value that is the name of the OptArg flag
	*		  turnedOff, a Boolean value 		???
	*		  dataType, the DataType value that is the data type of the OptArg flag
    * @return nothing
    */
    OptArg(String optionalArgumentName, boolean turnedOff, Arg.DataType dataType) {
        super("-" + optionalArgumentName, "", Arg.DataType.BOOLEAN);
        this.value = "false";
    }

	/**
    * Constructor for an OptArg flag object with the given name, data type, and description. 
    * @param  optionalArgumentName, the String value that is the name of the OptArg flag
	*		  turnedOff, a Boolean value 		???
	*		  dataType, the DataType value that is the data type of the OptArg flag
	*		  description, the String value that is teh description of the OptArg flag
    * @return nothing
    */
    OptArg(String optionalArgumentName, boolean turnedOff, Arg.DataType dataType, String description) {
        super("-" + optionalArgumentName, description, Arg.DataType.BOOLEAN);
        this.value = "false";
    }
	/**
	* Generates the approriate XML for a given optional argument and writes it in the supplied streamwriter
    * @param xMLStreamWriter the stream writer the XML will be writen in
    * @param postionalArgNames a list of all the arguments that are being used by the current argument parser
	* @return the stream writer after writing the current argument's XML information in it
	*/ 
    public XMLStreamWriter writeArgXML(XMLStreamWriter xMLStreamWriter, ArrayList<String> postionalArgNames) {
        try{ 

        xMLStreamWriter.writeCharacters("\n\t");
        xMLStreamWriter.writeStartElement("optional");

        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("name");
        xMLStreamWriter.writeCharacters(this.name.replace("-", ""));
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("value");
        xMLStreamWriter.writeCharacters(this.value);
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("required");
        xMLStreamWriter.writeCharacters(String.valueOf(this.required));
        xMLStreamWriter.writeEndElement();



    
    xMLStreamWriter.writeCharacters("\n\t\t");
    xMLStreamWriter.writeStartElement("datatype");
    xMLStreamWriter.writeCharacters(this.getDataType().toString());
    xMLStreamWriter.writeEndElement();

    if (this.shortFormName.length() > 0) {
        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("shortname");
        xMLStreamWriter.writeCharacters(this.shortFormName);
        xMLStreamWriter.writeEndElement();
    }
    if (this.description.length() > 0) {
        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("description");
        xMLStreamWriter.writeCharacters(this.description);
        xMLStreamWriter.writeEndElement();

      }
    if (this.allRestrictedValuesString.length() > 0) {
        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("restrictedValues");
        xMLStreamWriter.writeCharacters(this.allRestrictedValuesString);
        xMLStreamWriter.writeEndElement();

    }
    xMLStreamWriter.writeCharacters("\n\t");
    xMLStreamWriter.writeEndElement();
}

    catch (XMLStreamException e) {
        e.printStackTrace();
      } 
    return xMLStreamWriter;
}

}
