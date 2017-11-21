package edu.wofford;

import java.io.*;
import java.util.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StaxParser {
    public static ArgParser createParserFromXML(String xmlFilename) {
        ArgParser argChecker;
        String tagContent;
        XMLInputFactory factory;
        XMLStreamReader reader;
        Stack<String> argNames = new Stack<>();;
        Stack<String> currentArgAccessed = new Stack<>();;
        Arg.DataType argumentDataType = Arg.DataType.STRING;
        try {
            argChecker = null;
            tagContent = null;
            factory = XMLInputFactory.newInstance();
            FileReader fileReader = new FileReader(xmlFilename);
            reader = factory.createXMLStreamReader(fileReader);
    
            while (reader.hasNext()) {
                int event = reader.next();
    
                switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();
                    //creat  argument parser at first element, product owner will add name/description later
                    if ("arguments".equals(startElement)) {
                        argChecker = new ArgParser("", "");
                    } 
                    //record what type of argument that is being parsed
                    else if ("optional".equals(startElement) || "positional".equals(startElement)
                            || "flag".equals(startElement)) {
                        argNames.push(startElement);
                    }
                    break;
    
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
    
                case XMLStreamConstants.END_ELEMENT:
                    
                    String endElement = reader.getLocalName();
                    //done with parsing
                    if ("arguments".equals(endElement)) {
            
                        break;
                    }
                    //done using current argument
                    else if ("optional".equals(endElement) || "flag".equals(endElement)
                            || "positional".equals(endElement)) {
                        argNames.pop();
                        currentArgAccessed.pop();
                        break;
                    } else {   
                        String typeOfArgument = argNames.peek();
                        //encountered some type of attribute for some argument
    
                        if ("optional".equals(typeOfArgument)) {
                            if ("name".equals(endElement)) {
                                currentArgAccessed.push(tagContent);
                            
                            } else if ("value".equals(endElement)) {
                                argChecker.addOptArg(currentArgAccessed.peek(), tagContent);
    
                            } else if ("datatype".equals(endElement)) {
                                if (tagContent.equals("string")) {
                                    argumentDataType = Arg.DataType.STRING;
                                } else if (tagContent.equals("float")) {
                                    argumentDataType = Arg.DataType.FLOAT;
                                } else if (tagContent.equals("boolean")) {
                                    argumentDataType = Arg.DataType.BOOLEAN;
                                } else if (tagContent.equals("int")) {
                                    argumentDataType = Arg.DataType.INT;
                                }
    
                                argChecker.getArgument(currentArgAccessed.peek()).setDataType(argumentDataType);
    
                            } else if ("shortname".equals(endElement)) {
                                argChecker.setArgShortFormName(currentArgAccessed.peek(), tagContent);
    
                            } else if ("description".equals(endElement)) {
                                argChecker.getArgument(currentArgAccessed.peek()).setDescription(tagContent);
                            }
                            else if ("restrictedValues".equals(endElement)) {
                                argChecker.getArgument(currentArgAccessed.peek()).setRestrictedValues(tagContent);
                            }
                            else if ("required".equals(endElement)) {
                                if(tagContent.equals("true")){
                                    argChecker.setArgAsRequired(currentArgAccessed.peek());
                                }
                               
                            }
                        } else if ("flag".equals(typeOfArgument)) {
                            if ("name".equals(endElement)) {
                                argChecker.addFlag(tagContent);
                                currentArgAccessed.push(tagContent);
                            }
    
                        } 
                        else if ("positional".equals(typeOfArgument)) {
                            if ("name".equals(endElement)) {
                                argChecker.addArg(tagContent);
                                currentArgAccessed.push(tagContent);
                            }
                            //TODO
                            //gotta figure one out, maybe make a argument stack and then add them all at the end TODO
                            else if ("position".equals(endElement)) {
    
                            } else if ("datatype".equals(endElement)) {
    
                                if (tagContent.equals("string")) {
                                    argumentDataType = Arg.DataType.STRING;
                                } else if (tagContent.equals("float")) {
                                    argumentDataType = Arg.DataType.FLOAT;
                                } else if (tagContent.equals("boolean")) {
                                    argumentDataType = Arg.DataType.BOOLEAN;
                                } else if (tagContent.equals("int")) {
                                    argumentDataType = Arg.DataType.INT;
                                }
                                argChecker.getArgument(currentArgAccessed.peek()).setDataType(argumentDataType);
                            } else if ("shortname".equals(endElement)) {
                                argChecker.setArgShortFormName(currentArgAccessed.peek(), tagContent);
                            } else if ("description".equals(endElement)) {
                                argChecker.getArgument(currentArgAccessed.peek()).setDescription(tagContent);
                            }
                            else if ("restrictedValues".equals(endElement)) {
                                argChecker.getArgument(currentArgAccessed.peek()).setRestrictedValues(tagContent);
                            }
    
                        }
                         break;
                    }
                
        }
    }
    return argChecker;
}
        catch(Exception e) {
            System.out.println(e);
            return new ArgParser("");
        }

    }




    }

