package edu.wofford;

import java.util.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StaxParser {
    private ArgParser argChecker;
    private String tagContent;
    private XMLInputFactory factory;
    private XMLStreamReader reader;
    private Stack<String> argNames;
    private Stack<String> currentArgAccessed;
    private Arg.DataType argumentDataType;

    StaxParser(String fileName) throws Exception {

        this.argChecker = null;
        this.tagContent = null;

        this.factory = XMLInputFactory.newInstance();
        this.reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(fileName));

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
            case XMLStreamConstants.START_ELEMENT:
                String startElement = reader.getLocalName();

                if ("arguments".equals(startElement)) {
                    this.argChecker = new ArgParser("", "");
                } else if ("employee".equals(startElement) || "positional".equals(startElement)
                        || "flag".equals(startElement)) {

                    argNames.push(startElement);
                }

                break;

            case XMLStreamConstants.CHARACTERS:
                tagContent = reader.getText().trim();
                break;

            case XMLStreamConstants.END_ELEMENT:
                String typeOfArgument = argNames.peek();
                String endElement = reader.getLocalName();
                if ("arguments".equals(endElement)) {
                    break;
                }

                else if ("optional".equals(endElement) || "flag".equals(endElement)
                        || "positional".equals(endElement)) {
                    argNames.pop();
                    currentArgAccessed.pop();
                    break;
                } else {
                    String currentArgumentAccessed = currentArgAccessed.peek();
                    if ("optional".equals(typeOfArgument)) {
                        if ("name".equals(endElement)) {
                            this.currentArgAccessed.push(tagContent);
                            break;
                        } else if ("value".equals(endElement)) {
                            argChecker.addOptArg(currentArgumentAccessed, tagContent);

                        } else if ("datatype".equals(endElement)) {
                            if (tagContent.equals("string")) {
                                this.argumentDataType = Arg.DataType.STRING;
                            } else if (tagContent.equals("float")) {
                                this.argumentDataType = Arg.DataType.FLOAT;
                            } else if (tagContent.equals("boolean")) {
                                this.argumentDataType = Arg.DataType.BOOLEAN;
                            } else if (tagContent.equals("int")) {
                                this.argumentDataType = Arg.DataType.INT;
                            }

                            argChecker.getArgument(currentArgumentAccessed).setDataType(this.argumentDataType);

                        } else if ("shortname".equals(endElement)) {
                            argChecker.setArgShortFormName(currentArgumentAccessed, tagContent);

                        } else if ("description".equals(endElement)) {
                            argChecker.getArgument(currentArgumentAccessed).setDescription(tagContent);

                        }
                    } else if ("flag".equals(typeOfArgument)) {
                        if ("name".equals(endElement)) {
                            argChecker.addFlag(tagContent);
                            this.currentArgAccessed.push(tagContent);
                        }

                    } else if ("positional".equals(typeOfArgument)) {
                        if ("name".equals(endElement)) {
                            argChecker.addArg(tagContent);
                            this.currentArgAccessed.push(tagContent);
                        }
                        //TODO
                        //gotta figure this one out, maybe make a argument stack and then add them all at the end TODO
                        else if ("position".equals(endElement)) {

                        } else if ("datatype".equals(endElement)) {

                            if (tagContent.equals("string")) {
                                this.argumentDataType = Arg.DataType.STRING;
                            } else if (tagContent.equals("float")) {
                                this.argumentDataType = Arg.DataType.FLOAT;
                            } else if (tagContent.equals("boolean")) {
                                this.argumentDataType = Arg.DataType.BOOLEAN;
                            } else if (tagContent.equals("int")) {
                                this.argumentDataType = Arg.DataType.INT;
                            }
                            argChecker.getArgument(currentArgumentAccessed).setDataType(this.argumentDataType);
                        } else if ("shortname".equals(endElement)) {
                            argChecker.setArgShortFormName(currentArgumentAccessed, tagContent);
                        } else if ("description".equals(endElement)) {
                            argChecker.getArgument(currentArgumentAccessed).setDescription(tagContent);
                        }

                    }
                }

            case XMLStreamConstants.START_DOCUMENT:
                this.argNames = new Stack<>();
                this.currentArgAccessed = new Stack<>();
                break;

            }
        }
    }
}
