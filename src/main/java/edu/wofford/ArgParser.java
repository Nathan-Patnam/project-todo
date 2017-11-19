package edu.wofford;

import java.util.*;
/** 
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringWriter;
import java.io.File;
*/

import java.io.IOException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;

public class ArgParser {
  private String programName;
  private String programDescription;
  private Map<String, String> shortToLong;
  private Map<String, Arg> arguments;
  private ArrayList<String> argumentNames;
  private HashSet<String> flagNames;

  public ArgParser(String programName) {
    this(programName, "");
  }

  public ArgParser(String programName, String description) {
    this.programName = programName;
    this.programDescription = description;
    arguments = new LinkedHashMap<>();
    argumentNames = new ArrayList<>();
    shortToLong = new HashMap<>();
    flagNames = new HashSet<>();

  }

  public void addArg(String argname) {
    addArg(argname, "", Arg.DataType.STRING);
  }

  public void addArg(String argname, String description) {
    addArg(argname, description, Arg.DataType.STRING);
  }

  public void addArg(String argname, Arg.DataType dataType) {
    addArg(argname, "", dataType);
  }

  public void addArg(String argname, String description, Arg.DataType dataType) {
    arguments.put(argname, new Arg(argname, description, dataType));
    argumentNames.add(argname);
  }

  public void addOptArg(String argname, String defaultValue) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, "");

  }

  public void addOptArg(String argname, String defaultValue, String description) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, description);

  }

  public void addOptArg(String argname, String defaultValue, Arg.DataType dataType) {
    addOptArg(argname, defaultValue, dataType, "");

  }

  public void addOptArg(String argname, String defaultValue, Arg.DataType dataType, String description) {
    arguments.put(argname, new OptArg(argname, defaultValue, dataType, description));
  }

  public void setArgShortFormName(String argument, String shortFormName) {
    arguments.get(argument).setShortFormName(shortFormName);
    if (shortToLong.get(shortFormName) != null || shortFormName.equals("h")) {
      throw new IllegalArgumentException("The short form name " + shortFormName + " is already in uses");
    }

    //idea is to put in shortForName and argument, so we can do reverse lookups in O(1) time.
    // two caveats, all values  have to be unique and values cant be used on top of another another 
    shortToLong.put(shortFormName, argument);

  }

  public void addFlag(String argname) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN));
    flagNames.add(argname);
  }

  private boolean checkType(String value, Arg.DataType type) {
    switch (type) {
    case BOOLEAN:
      return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
    case INT:
      try {
        Integer.parseInt(value);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    case FLOAT:
      try {
        Float.parseFloat(value);
        return true;
      } catch (NumberFormatException e) {
        return false;
      }
    default:
      return true;
    }
  }

  public Arg getArgument(String argument) {

    return arguments.get(argument);
  }

  /**
  * Returns the value that the argument holds. If no value has been set for the argument then it will return null
  * and if the argument doesn't exist then an error will be thrown.
  * @param  argument  the name of the arugment you want the value of
  * @return           the value associated with that argument
  * @throws  
  */
  public String getArgValue(String argument) {
    return arguments.get(argument).getValue();
  }

  /**
  * Returns the description of the argument. If no description has been set for the argument then it will
  * return a empty string. If the arugment doesn't exists and error will be thrown.
  * @param  argument  the name of the arugment you want the description of
  * @return           the value associated with that argument
  */
  public String getArgDescription(String argument) {

    return arguments.get(argument).getDescription();
  }

  public Arg.DataType getArgDataType(String argument) {

    return arguments.get(argument).getDataType();
  }

  public String getArgDataTypeString(String argument) {

    return arguments.get(argument).getDataType().toString();
  }

  private String getHelpMessage() {
    String message = "";
    message = "usage: java " + programName + getParameterString() + "\n" + programDescription + "\n"
        + "positional arguments:";
    for (String argNameIterator : arguments.keySet()) {
      if (argNameIterator.equals("help") || argNameIterator.equals("h")) {
        continue;
      } else {
        Arg currentArgumentIterator = arguments.get(argNameIterator);
        message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
            + currentArgumentIterator.getDataType().toString() + ")";
      }
    }
    return message;
  }

  public int getNumberArgs() {
    return arguments.size();
  }

  private String getParameterString() {
    String key_string = "";
    for (String argNameIterator : arguments.keySet()) {
      if (argNameIterator.equals("help") || argNameIterator.equals("h")) {
        continue;
      }
      key_string += " " + argNameIterator;
    }
    return key_string;
  }

  public String setProgramName(String programName) {
    return this.programName = programName;
  }

  public String getProgramName() {
    return programName;
  }

  public String getProgramDescription() {
    return programDescription;
  }

  /** 
  public SaxHandler handler;
  public ArgParser loadArgs(String fileName){
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try {
      File xmlInput = new File (fileName); 
      SAXParser saxParser = factory.newSAXParser();
      handler = new SaxHandler();
      saxParser.parse(xmlInput, handler);
      return handler.getArgParser();
    } 
    catch (Throwable err) {
        err.printStackTrace ();
    }
    return handler.getArgParser();
  }
  
  */

  public void parse(String[] args) {

    int usedArguments = 0;
    for (int i = 0; i < args.length; i++) {
      boolean isArgAFlag = false;
      String aname = "";
      if (args[i].equals("-h") || args[i].equals("--help")) {
        String message = getHelpMessage();
        throw new HelpException(message);
      } else if (args[i].startsWith("-")) {
        if (args[i].startsWith("--")) {
          aname = args[i].substring(2);
          if (arguments.get(aname) == null) {
            throw new IllegalArgumentException("argument " + args[i].substring(2) + " does not exist");
          }

        } else {
          String sname = args[i].substring(1);
          //argument is a flag
          if (arguments.get(sname) != null) {
            arguments.get(sname).setValue("true");
            isArgAFlag = true;
            continue;
          }
          //argument is a collection of flags
          else if (sname.length() > 1) {
            for (int j = 0; j < sname.length(); j++) {
              String flagIterator = String.valueOf(sname.charAt(j));
              if (arguments.get(flagIterator) != null) {
                arguments.get(flagIterator).setValue("true");
                isArgAFlag = true;
              } else {
                throw new IllegalArgumentException("flag " + flagIterator + " does not exist");
              }
            }
            usedArguments++;
            continue;
          }

          aname = shortToLong.get(sname);
          if (aname == null && !isArgAFlag) {
            throw new IllegalArgumentException("argument " + args[i].substring(1) + " does not exist");
          } else {
            usedArguments++;
          }
        }

        Arg a = arguments.get(aname);
        if (a.getDataType() == Arg.DataType.BOOLEAN) {
          a.setValue("true");
        } else {
          if (checkType(args[i + 1], a.getDataType())) {
            a.setValue(args[i + 1]);
            i++;
          } else {
            // Throw some exception
            String message = "";
            message = "usage: java " + programName + getParameterString() + "\n" + programName + ".java: error: "
                + "argument " + aname + ":" + " invalid " + a.getDataType().toString() + " value: " + args[i + 1];

            throw new HelpException(message);
          }
        }
      }

      else {
        // Regular argument value
        if (usedArguments == argumentNames.size()) {
          String message = "usage: java " + programName + getParameterString() + "\n" + programName
              + ".java: error: unrecognized arguments: " + args[i];
          throw new TooManyArguments(message);
        } else {
          aname = argumentNames.get(usedArguments);
          Arg a = arguments.get(aname);
          if (checkType(args[i], a.getDataType())) {
            a.setValue(args[i]);
            usedArguments++;
          } else {
            String message = "";
            message = "usage: java " + programName + getParameterString() + "\n" + programName + ".java: error: "
                + "argument " + aname + ":" + " invalid " + a.getDataType().toString() + " value: " + args[i];

            throw new HelpException(message);
          }
        }
      }

    }

    if (usedArguments < argumentNames.size()) {
      String missingArguements = "";
      for (int i = usedArguments; i < argumentNames.size(); i++) {
        missingArguements += " " + argumentNames.get(i);
      }
      String message = "usage: java " + programName + getParameterString() + "\n" + programName
          + ".java: error: the following arguments are required:" + missingArguements;
      throw new TooFewArguments(message);
    }

  }

  public void getArgInfoAsXML(String fileName) {
    try {
      XMLOutputFactory xof = XMLOutputFactory.newInstance();
      XMLStreamWriter xMLStreamWriter = null;
      xMLStreamWriter = xof.createXMLStreamWriter(new FileWriter(fileName));

      int argumentPositionCounter = 1;
      xMLStreamWriter.writeStartDocument();
      xMLStreamWriter.writeStartElement("arguments");
      for (String argNameIterator : arguments.keySet()) {
        Arg argumentIterator = getArgument(argNameIterator);
        //adding a flag
        if (flagNames.contains(argNameIterator)) {
          xMLStreamWriter.writeStartElement("flag");

          xMLStreamWriter.writeStartElement("name");
          xMLStreamWriter.writeCharacters(argNameIterator);
          xMLStreamWriter.writeEndElement();

          xMLStreamWriter.writeStartElement("present");
          xMLStreamWriter.writeCharacters(argumentIterator.getValue());
          xMLStreamWriter.writeEndElement();
          //close flag tag
          xMLStreamWriter.writeEndElement();

        } else {
          //argument is a positional argument
          if (argumentIterator instanceof Arg) {
            xMLStreamWriter.writeStartElement("positional");

            xMLStreamWriter.writeStartElement("name");
            xMLStreamWriter.writeCharacters(argNameIterator);
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("position");
            xMLStreamWriter.writeCharacters(String.valueOf(argumentPositionCounter));
            xMLStreamWriter.writeEndElement();

            argumentPositionCounter++;
          } else {
            xMLStreamWriter.writeStartElement("optional");

            xMLStreamWriter.writeStartElement("name");
            xMLStreamWriter.writeCharacters(argNameIterator);
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("value");
            xMLStreamWriter.writeCharacters(argumentIterator.getValue());
            xMLStreamWriter.writeEndElement();

          }

          xMLStreamWriter.writeStartElement("datatype");
          xMLStreamWriter.writeCharacters(argumentIterator.getDataType().toString());
          xMLStreamWriter.writeEndElement();

          if (argumentIterator.getShortFormName() != null) {
            xMLStreamWriter.writeStartElement("shortname");
            xMLStreamWriter.writeCharacters(argumentIterator.getShortFormName());
            xMLStreamWriter.writeEndElement();
          }

          if (argumentIterator.getDescription() != null && argumentIterator.getDescription().length() > 0) {
            xMLStreamWriter.writeStartElement("description");
            xMLStreamWriter.writeCharacters(argumentIterator.getDescription());
            xMLStreamWriter.writeEndElement();
          }
          //close flag or optional tag
          xMLStreamWriter.writeEndElement();
        }
      }
      //close arguments tag
      xMLStreamWriter.writeEndElement();
      xMLStreamWriter.writeEndDocument();
      xMLStreamWriter.flush();
      xMLStreamWriter.close();

    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
