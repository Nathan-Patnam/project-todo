/** 
package edu.wofford;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

public class SaxHandler extends DefaultHandler {
    private Stack<String> argStack = new Stack<String>();
    private Stack<String> argAttributesStack = new Stack<String>();
    private ArgParser argchecker = new ArgParser("","");
    private boolean ArgBeenEncounted = false;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        //basically a body tag contains all other elements so do nothing
        if (qName.equals("arguments")) {
            if (argStack.size() > 0) {
                throw new IllegalArgumentException(
                        "arguments element should have already been given, consult the schema");
            }
        }
        //types of arguments that we can have
        else if (qName.equals("positional") || qName.equals("optional") || qName.equals("flag")) {
            this.argStack.push(qName);
            ArgBeenEncounted = true;
        }
        //all types of attributes of the arguments
        else if (qName.equals("datatype") || qName.equals("position") || qName.equals("shortname")
                || qName.equals("value") || qName.equals("description") || qName.equals("present")) {
            this.argAttributesStack.push(qName);
        }

        //what to do when we encounter different types of elements of arguments
        else {
            throw new IllegalArgumentException(qName + " is a unknown XML element, please consult the schema");
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        //pop twice since the first item popped is the name of the argument
        //the second item popped is the type of argument it is
        if (qName.equals("positional") || qName.equals("optional") || qName.equals("flag")) {
            this.argStack.pop();
            this.argStack.pop();
            ArgBeenEncounted = false;
        }

        else if (qName.equals("datatype") || qName.equals("position") || qName.equals("shortname")
                || qName.equals("value") || qName.equals("description") || qName.equals("present")) {
            this.argAttributesStack.pop();

        }

    }

    public void characters(char ch[], int start, int length) throws SAXException {

        Arg.DataType dataType;
        String value = new String(ch, start, length).trim();
        if (value.length() == 0)
            return; // ignore white space
        String currentArgTypeAccessed;
        String currentArgNameAccessed;

        if (ArgBeenEncounted) {
            currentArgTypeAccessed = this.elementStack.get(elementStack.size() - 1);
            currentArgNameAccessed = currentArgTypeAccessed = this.elementStack.peek();
        } else {
            currentArgTypeAccessed = this.elementStack.peek();
        }

        //if characters is a type of DataType

        if (currentArgTypeAccessed.equals("positional")) {
            if ("name".equals(currentElement())) {
                argchecker.addArg(currentArgumentAccessed);
                this.elementStack.push(currentArgumentAccessed);
            }

            else if ("datatype".equals(currentElement())) {
                argchecker.getArgument(currentArgumentAccessed).setDataType(value);
                ;
            } else if ("shortname".equals(currentElement())) {
                argchecker.addArg(currentArgumentAccessed);
            }

            else if ("shortname".equals(currentElement())) {
                argchecker.addArg(currentArgumentAccessed);
            }

        }

        else if (currentArgumentAccessed.equals("positional")) {
            if ("name".equals(currentElement())) {
                argchecker.addArg(currentArgumentAccessed);
                this.elementStack.push(currentArgumentAccessed);
            }

            else if ("datatype".equals(currentElement())) {
                argchecker.getArgument(currentArgumentAccessed).setDataType(currentEcurrentArgumentAccessedlement);
                ;
            } else if ("shortname".equals(currentElement())) {
                argchecker.addArg(currentArgumentAccessed);
            }

            else if ("shortname".equals(currentElement())) {
                argchecker.addArg(currentArgumentAccessed);
            }

        }

        else if ("vehicleId".equals(currentElement()) && "vehicle".equals(currentElementParent())) {

            Vehicle vehicle = (Vehicle) this.objectStack.peek();
            vehicle.vehicleId = (vehicle.vehicleId != null ? vehicle.vehicleId : "") + value;

        } else if ("name".equals(currentElement()) && "vehicle".equals(currentElementParent())) {

            Vehicle vehicle = (Vehicle) this.objectStack.peek();
            vehicle.name = (vehicle.name != null ? vehicle.name : "") + value;
        }
    }

    private String currentElementParent() {
        if (this.elementStack.size() < 2)
            return null;
        return this.elementStack.get(this.elementStack.size() - 2);
    }


    public ArgParser getArgParser(){
        return argchecker;
    }

}
*/