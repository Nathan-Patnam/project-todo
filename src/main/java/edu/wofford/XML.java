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
     /**
  * Will create a argument parser from a supplied XML file and return the argument parser
  * @param fileName name of the file
  * @return the generated argument parser
  */
    public static ArgParser loadFromFile(String fileName) {
        Arg tempArg = null;
        ArgParser argChecker = new ArgParser("", "");
        String tagContent;
        HashMap<Integer, Arg> argPosition = new HashMap<>();
        Arg.DataType argumentDataType = Arg.DataType.STRING;
        try {
            tagContent = null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            FileReader fileReader = new FileReader(fileName);
            XMLStreamReader reader = factory.createXMLStreamReader(fileReader);
            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();
                    if ("optional".equals(startElement)) {
                        tempArg = new OptArg("", "");
                    } else if ("positional".equals(startElement)) {
                        tempArg = new Arg("");
                    }

                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:

                    String endElement = reader.getLocalName();
                    if ("arguments".equals(endElement)) {
                        if (argPosition.size() > 0) {
                            for (int i = 1; i <= argPosition.size(); i++) {
                    
                                argChecker.addArg(argPosition.get(i));
                                
                            }
                        }
                    }
                    else if ("optional".equals(endElement)) {
                        argChecker.addOptArg((OptArg) tempArg);
                    }
                    else if ("name".equals(endElement)) {
                        tempArg.setName(tagContent);

                    } else if ("value".equals(endElement)) {
                        tempArg.setValue(tagContent);

                    } else if ("datatype".equals(endElement)) {
                        if (tagContent.equals("string")) {
                            argumentDataType = Arg.DataType.STRING;
                        } else if (tagContent.equals("float")) {
                            argumentDataType = Arg.DataType.FLOAT;
                        } else if (tagContent.equals("boolean")) {
                            if (tempArg instanceof OptArg) {
                                argChecker.addFlagToList(tempArg.getName());
                            }
                            argumentDataType = Arg.DataType.BOOLEAN;
                        } else if (tagContent.equals("int")) {
                            argumentDataType = Arg.DataType.INT;
                        }

                        tempArg.setDataType(argumentDataType);

                    } else if ("shortname".equals(endElement)) {
                        tempArg.setShortFormName(tagContent);
                    } else if ("description".equals(endElement)) {
                        tempArg.setDescription(tagContent);
                    } else if ("restrictedValues".equals(endElement)) {
                        tempArg.setRestrictedValues(tagContent);
                    }

                    else if ("required".equals(endElement)) {
                        if (tagContent.equals("true")) {
                            tempArg.makeArgRequired();
                        }
                    } else if ("position".equals(endElement)) {
                        argPosition.put(Integer.parseInt(tagContent), tempArg);
                    }
                    break;
                }

            }

            return argChecker;
        } catch (Exception e) {
            System.out.println(e);
            return new ArgParser("");
        }

    }


    /**
  * Will create a file and store a argument parser's information as XML in that file
  * @param fileName name of the file to be created
  * @param argparser the argument parser that you want to generate XML for
  */

    public static void saveToFile(String fileName, ArgParser argparser) {
        try {
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter = xof.createXMLStreamWriter(new FileWriter(fileName));
            Map<String, Arg> arguments = argparser.getAllArgs();
            ArrayList<String> postionalArgNames=argparser.getPostionalArgNames();

            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeCharacters("\n");
            xMLStreamWriter.writeStartElement("arguments");

            for (String argNameIterator : arguments.keySet()) {
                Arg argumentIterator = argparser.getArgument(argNameIterator);
                argumentIterator.writeArgXML(xMLStreamWriter, postionalArgNames);
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
