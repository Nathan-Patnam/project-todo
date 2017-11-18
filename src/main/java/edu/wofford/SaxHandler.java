
package edu.wofford;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;


public class SaxHandler extends DefaultHandler {

    private Stack<String> elementStack = new Stack<String>();
    private ArgumentParser argchecker = new ArgumentParser("VolumeCalculator", "a program that calculates the volume of a ellipsoid");
    

    public void startElement(String uri, String localName,
        String qName, Attributes attributes) throws SAXException {

        //basically a body toag contains all other elements so do nothing
        if("arguments".equals(qName)){
        } 
        //types of arguments that we can have
        else if("positional".equals(qName)){
            this.elementStack.push(qName);
        }
        else if("optional".equals(qName)){
            this.elementStack.push(qName);
        }
        else if("flag".equals(qName)){
            this.elementStack.push(qName);
        }

        //what to do when we encounter different types of elements of arguments
    }

    public void endElement(String uri, String localName,
        String qName) throws SAXException {
            //pop twice since the first item popped is the name of the argument
            //the second item popped is the type of argument it is
        if( qName.equals("positional") || qName.equals("optional") ||
        qName.equals("flag")){
        this.elementStack.pop();
        this.elementStack.pop();
        }

    }

    public void characters(char ch[], int start, int length)
        throws SAXException {

        String value = new String(ch, start, length).trim();
        if(value.length() == 0) return; // ignore white space
        String currentArgumentAccessed= this.elementStack.peek();

        if (currentArgumentAccessed.equals("positional")) {
        if("name".equals(currentElement())) {
            argchecker.addArg(currentArgumentAccessed);
            this.elementStack.push(currentArgumentAccessed);
        } 
        
        else if("datatype".equals(currentElement())) {
            argchecker.getArgument(currentArgumentAccessed).setDataType(currentEcurrentArgumentAccessedlement);;
        } 
        else if("shortname".equals(currentElement())) {
            argchecker.addArg(currentArgumentAccessed);
        } 

        else if("shortname".equals(currentElement())) {
            argchecker.addArg(currentArgumentAccessed);
        } 


    }





         else if("vehicleId".equals(currentElement()) &&
                  "vehicle"  .equals(currentElementParent())){

            Vehicle vehicle   = (Vehicle) this.objectStack.peek();
            vehicle.vehicleId = (vehicle.vehicleId != null ?
                                    vehicle.vehicleId  : "")  + value;

        } else if("name"   .equals(currentElement()) &&
                  "vehicle".equals(currentElementParent())){

            Vehicle vehicle = (Vehicle) this.objectStack.peek();
            vehicle.name = (vehicle.name != null ? vehicle.name  : "")
                               + value;
        }
    }

    private String currentElementParent() {
        if(this.elementStack.size() < 2) return null;
        return this.elementStack.get(this.elementStack.size()-2);
    }

}     