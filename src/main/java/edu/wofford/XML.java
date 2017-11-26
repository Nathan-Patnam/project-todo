package edu.wofford;

import java.util.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class XML {
    public static ArgParser loadFromFile(String fileName) {
        ArgParser argChecker;
        String tagContent;
        Stack<String> argNames = new Stack<>();
        
        Stack<String> currentArgAccessed = new Stack<>();
        
        List<ArgPositionPair> argPosition = new ArrayList<ArgPositionPair>();

        Arg.DataType argumentDataType = Arg.DataType.STRING;
        try {
            argChecker = null;
            tagContent = null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            FileReader fileReader = new FileReader(fileName);
            XMLStreamReader reader = factory.createXMLStreamReader(fileReader);
            Arg tempPositionalArg = new Arg("");
            

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();
                    //create  argument parser at first element, product owner will add name/description later
                    if ("arguments".equals(startElement)) {
                        argChecker = new ArgParser("", "");
                    }
                    //record what type of argument that is being parsed
                    else if ("optional".equals(startElement)
                            || "flag".equals(startElement)) {
                                
                        argNames.push(startElement);
                    }

                    else if("positional".equals(startElement)){
                        tempPositionalArg = new Arg("");
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
                        if (argPosition.size() > 0) {
                            Collections.sort(argPosition);
                            for (int i = 0; i <argPosition.size(); i++) {
                                argChecker.addArg(argPosition.get(i).getArg());
                            }

                        }

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
                            } else if ("restrictedValues".equals(endElement)) {
                                argChecker.getArgument(currentArgAccessed.peek()).setRestrictedValues(tagContent);
                            } else if ("required".equals(endElement)) {
                                if (tagContent.equals("true")) {
                                    argChecker.setArgAsRequired(currentArgAccessed.peek());
                                }

                            }
                        } else if ("flag".equals(typeOfArgument)) {
                            if ("name".equals(endElement)) {
                                argChecker.addFlag(tagContent);
                                currentArgAccessed.push(tagContent);
                            }
                            else if ("description".equals(endElement)) {
                                argChecker.getArgument(currentArgAccessed.peek()).setDescription(tagContent);
                            } 

                        } else if ("positional".equals(typeOfArgument)) {

                            if ("name".equals(endElement)) {
                                currentArgAccessed.push(tagContent);
                                tempPositionalArg.setName(tagContent);
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
                                tempPositionalArg.setDataType(argumentDataType);
                            } else if ("shortname".equals(endElement)) {
                                tempPositionalArg.setShortFormName(tagContent);
                            } else if ("description".equals(endElement)) {
                                tempPositionalArg.setDescription(tagContent);
                            } else if ("restrictedValues".equals(endElement)) {
                                tempPositionalArg.setRestrictedValues(tagContent);
                            }
                            else if ("position".equals(endElement)) {
                                argPosition.add(new ArgPositionPair(Integer.parseInt(tagContent), tempPositionalArg));
                            }

                            

                        }
                        break;
                    }

                }
            }
            return argChecker;
        } catch (Exception e) {
            System.out.println(e);
            return new ArgParser("");
        }

    }

    public static void saveToFile(String fileName, ArgParser argparser) {
        try {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter = xof.createXMLStreamWriter(new FileWriter(fileName));
            int argumentPositionCounter = 1;
            HashSet<String> flagNames = argparser.getFlagNames();
            Map<String, Arg> arguments = argparser.getAllArgs();

            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeCharacters("\n");
            xMLStreamWriter.writeStartElement("arguments");

            for (String argNameIterator : arguments.keySet()) {

                Arg argumentIterator = argparser.getArgument(argNameIterator);
                //adding a flag
                if (flagNames.contains(argNameIterator)) {
                    xMLStreamWriter.writeCharacters("\n\t");
                    xMLStreamWriter.writeStartElement("flag");

                    xMLStreamWriter.writeCharacters("\n\t\t");
                    xMLStreamWriter.writeStartElement("name");
                    xMLStreamWriter.writeCharacters(argNameIterator);
                    xMLStreamWriter.writeEndElement();

                    if (argumentIterator.getDescription() != null && argumentIterator.getDescription().length() > 0) {
                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("description");
                        xMLStreamWriter.writeCharacters(argumentIterator.getDescription());
                        xMLStreamWriter.writeEndElement();

                    }

                    //close flag tag
                    xMLStreamWriter.writeCharacters("\n\t");
                    xMLStreamWriter.writeEndElement();

                } else {
                    //argument is a optional argument
                    if (argumentIterator instanceof OptArg ) {
                        xMLStreamWriter.writeCharacters("\n\t");
                        xMLStreamWriter.writeStartElement("optional");

                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("name");
                        xMLStreamWriter.writeCharacters(argNameIterator);
                        xMLStreamWriter.writeEndElement();

                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("value");
                        xMLStreamWriter.writeCharacters(argumentIterator.getValue());
                        xMLStreamWriter.writeEndElement();

                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("required");
                        xMLStreamWriter.writeCharacters(String.valueOf(argumentIterator.isArgRequired()));
                        xMLStreamWriter.writeEndElement();

                    } else {
                        xMLStreamWriter.writeCharacters("\n\t");
                        xMLStreamWriter.writeStartElement("positional");

                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("name");
                        xMLStreamWriter.writeCharacters(argNameIterator);
                        xMLStreamWriter.writeEndElement();

                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("position");
                        xMLStreamWriter.writeCharacters(String.valueOf(argumentPositionCounter));
                        xMLStreamWriter.writeEndElement();

                        argumentPositionCounter++;

                    }
                    xMLStreamWriter.writeCharacters("\n\t\t");
                    xMLStreamWriter.writeStartElement("datatype");
                    xMLStreamWriter.writeCharacters(argumentIterator.getDataType().toString());
                    xMLStreamWriter.writeEndElement();

                    if (argumentIterator.getShortFormName() != null) {
                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("shortname");
                        xMLStreamWriter.writeCharacters(argumentIterator.getShortFormName());
                        xMLStreamWriter.writeEndElement();
                    }

                    if (argumentIterator.getDescription() != null && argumentIterator.getDescription().length() > 0) {
                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("description");
                        xMLStreamWriter.writeCharacters(argumentIterator.getDescription());
                        xMLStreamWriter.writeEndElement();

                    }

                    if (argumentIterator.getRestrictedValuesString() != null
                            && argumentIterator.getRestrictedValuesString().length() > 0) {
                        xMLStreamWriter.writeCharacters("\n\t\t");
                        xMLStreamWriter.writeStartElement("restrictedValues");
                        xMLStreamWriter.writeCharacters(argumentIterator.getRestrictedValuesString());
                        xMLStreamWriter.writeEndElement();

                    }
                    //close positional and optional tag
                    xMLStreamWriter.writeCharacters("\n\t");
                    xMLStreamWriter.writeEndElement();
                }

            }
            //close arguments tag
            xMLStreamWriter.writeCharacters("\n");
            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.writeEndDocument();
            xMLStreamWriter.flush();
            xMLStreamWriter.close();

        }

        catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
