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
    private static class ArgPositionPair implements Comparable<ArgPositionPair> {
        private int position;
        private Arg argument;

        public ArgPositionPair(int position, Arg argument) {
            this.position = position;
            this.argument = argument;
        }

        public Arg getArg() {
            return this.argument;
        }

        @Override
        public int compareTo(ArgPositionPair o) {
            return position < o.position ? -1 : position > o.position ? 1 : 0;
        }
    }

    public static ArgParser loadFromFile(String fileName) {
        Arg tempArg = null;
        ArgParser argChecker = new ArgParser("", "");
        String tagContent;

        List<ArgPositionPair> argPosition = new ArrayList<ArgPositionPair>();

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
                    //done with parsing
                    if ("arguments".equals(endElement)) {
                        if (argPosition.size() > 0) {
                            Collections.sort(argPosition);
                            for (int i = 0; i < argPosition.size(); i++) {
                                argChecker.addArg(argPosition.get(i).getArg());
                            }

                        }

                        break;
                    }
                    //done using current argument
                    else if ("optional".equals(endElement)) {
                        argChecker.addOptArg((OptArg) tempArg);
                        break;
                    }

                    if ("name".equals(endElement)) {
                        tempArg.setName(tagContent);

                    } else if ("value".equals(endElement)) {
                        tempArg.setValue(tagContent);

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
                        argPosition.add(new ArgPositionPair(Integer.parseInt(tagContent), tempArg));
                    }

                }
                break;

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
            Map<String, Arg> arguments = argparser.getAllArgs();

            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeCharacters("\n");
            xMLStreamWriter.writeStartElement("arguments");

            for (String argNameIterator : arguments.keySet()) {
                Arg argumentIterator = argparser.getArgument(argNameIterator);
                argumentIterator.writeArgXML(xMLStreamWriter);
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
