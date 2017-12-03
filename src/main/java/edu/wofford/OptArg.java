package edu.wofford;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class OptArg extends Arg {

    OptArg(String name, String defaultValue) {
        this(name, defaultValue, Arg.DataType.STRING, "");
    }

    OptArg(String name, String defaultValue, String description) {
        this(name, defaultValue, Arg.DataType.STRING, description);
    }

    OptArg(String optionalArgumentName, String defaultValue, Arg.DataType dataType) {
        this(optionalArgumentName, defaultValue, dataType, "");
    }

    OptArg(String optionalArgumentName, String defaultValue, Arg.DataType datatype, String description) {
        super("--" + optionalArgumentName, description, datatype);
        this.value = defaultValue;
        this.required = false;

    }

    //flag constructors
    OptArg(String optionalArgumentName, boolean turnedOff, Arg.DataType dataType) {
        super("-" + optionalArgumentName, "", Arg.DataType.BOOLEAN);
        this.value = "false";
    }

    OptArg(String optionalArgumentName, boolean turnedOff, Arg.DataType dataType, String description) {
        super("-" + optionalArgumentName, description, Arg.DataType.BOOLEAN);
        this.value = "false";
    }

    public XMLStreamWriter writeArgXML(XMLStreamWriter xMLStreamWriter, ArgParser argparser) {
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

    if (this.shortFormName != null) {
        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("shortname");
        xMLStreamWriter.writeCharacters(this.shortFormName);
        xMLStreamWriter.writeEndElement();
    }
    if (this.description != null && this.description.length() > 0) {
        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("description");
        xMLStreamWriter.writeCharacters(this.description);
        xMLStreamWriter.writeEndElement();

      }
    if (this.allRestrictedValuesString != null
            && this.allRestrictedValuesString.length() > 0) {
        xMLStreamWriter.writeCharacters("\n\t\t");
        xMLStreamWriter.writeStartElement("restrictedValues");
        xMLStreamWriter.writeCharacters(this.allRestrictedValuesString);
        xMLStreamWriter.writeEndElement();

    }
    //close positional and optional tag
    xMLStreamWriter.writeCharacters("\n\t");
    xMLStreamWriter.writeEndElement();
}

    catch (XMLStreamException e) {
        e.printStackTrace();
      } 
    return xMLStreamWriter;
}

}
