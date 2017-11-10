package edu.wofford;

import java.util.*;

public class ArgumentParser {

  private String programName;
  private String programDescription;
  Map<String, OptionalArgument> optionalarguments = new LinkedHashMap<String, OptionalArgument>();
  Map<String, Argument> arguments = new LinkedHashMap<String, Argument>();
  private ArrayList<ArrayList<String>> listBadDataTypes = new ArrayList<ArrayList<String>>();

  public ArgumentParser(String programName) {
    this.programName = programName;
    arguments = new LinkedHashMap<>();
  }

  public ArgumentParser(String programName, String description) {
    this.programName = programName;
    this.programDescription = description;
    arguments = new LinkedHashMap<>();
  }

  public void addOptionalArg(String optionalArgName, defaultVal){
    //product owner doesnt have to put in -- for the optionalArgName, user does
    optionalarguments.put(optionalArgName, new OptionalArgument(optionalArgName, defaultVal));
  }



  public void addArg(String argname) {
    arguments.put(argname, new Argument(argname));
  }

  public void addArg(String argname, String description) {
    arguments.put(argname, new Argument(argname, description));

  }

  public void addArg(String argname, Argument.DataType dataType) {
    arguments.put(argname, new Argument(argname, dataType));

  }
  public void addArg(String argname, String description, Argument.DataType dataType) {
    arguments.put(argname, new Argument(argname, description, dataType));
  }

  //messages and error handling

  private void checkIfHelpMessage(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-h")) {
        String message = getHelpMessage();
        throw new HelpException(message);
      }
    }
  }

  //rewrite
  public String getTypeExceptionMessage(ArrayList<ArrayList<String>> listBadDataTypes, int sizeBadDataTypes) {
    String message = "";
    message = "usage: java " + programName + getParameterString() + "\n" + programName + ".java: error: ";
    if (sizeBadDataTypes == 1) {
      message += "argument " + listBadDataTypes.get(0).get(0) + ":" + " invalid " + listBadDataTypes.get(0).get(1)
          + " value: " + listBadDataTypes.get(0).get(2);
    }
    //if there are more than bad data argument
    else {
      for (int i = 0; i < sizeBadDataTypes; i++) {
        message += "argument " + listBadDataTypes.get(i).get(0) + ":" + " invalid " + listBadDataTypes.get(i).get(1)
            + " value: " + listBadDataTypes.get(i).get(2) + "\n";
      }
    }
    return message;
  }
//rewrite using hopefully dataTypes class instead
  public void areThereWrongDataTypes(String[] args) {
    int sizeBadDataTypes = 0;
    int i = 0;
    for (String argNameIterator : arguments.keySet()) {
      String currentArgumentIteratorDataType = arguments.get(argNameIterator).getargumentDataTypeString();
      if (currentArgumentIteratorDataType.equals("float")) {
        try {
          float temp = Float.parseFloat(args[i]);
        } catch (NumberFormatException e) {
          ArrayList<String> badDataType = new ArrayList<String>();
          badDataType.add(argNameIterator);
          badDataType.add("float");
          badDataType.add(args[i]);
          listBadDataTypes.add(badDataType);
          sizeBadDataTypes++;
        }
      } else if (currentArgumentIteratorDataType.equals("int")) {
        try {
          int temp = Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
          ArrayList<String> badDataType = new ArrayList<String>();
          badDataType.add(argNameIterator);
          badDataType.add("int");
          badDataType.add(args[i]);
          listBadDataTypes.add(badDataType);
          sizeBadDataTypes++;
        }
      } else if (currentArgumentIteratorDataType.equals("boolean")) {
        if (!(args[i].equals("true") || args[i].equals("false"))) {
          ArrayList<String> badDataType = new ArrayList<String>();
          badDataType.add(argNameIterator);
          badDataType.add("boolean");
          badDataType.add(args[i]);
          listBadDataTypes.add(badDataType);
          sizeBadDataTypes++;
        }
      }
      i++;
    }

    if (sizeBadDataTypes > 0) {
      String message = getTypeExceptionMessage(listBadDataTypes, sizeBadDataTypes);
      throw new HelpException(message);
    }
  }

  private void checkIfTooFewArguments(String[] args, String key_string) {
    if (args.length < arguments.size()) {
      String missingArguements = "";
      int iterator = 0;
      for (String argNameIterator : arguments.keySet()) {
        if (iterator++ < args.length) {
          continue;
        }
        missingArguements += " " + argNameIterator;
      }
      String message = "usage: java " + programName + key_string + "\n" + programName
          + ".java: error: the following arguments are required:" + missingArguements;
      throw new TooFewArguments(message);
    }
  }

  private void checkIfTooManyArguments(String[] args, String key_string) {
    if (args.length > arguments.size()) {
      String tooManyArguments = "";
      for (int i = arguments.size(); i < args.length; i++) {
        tooManyArguments += " " + args[i];
      }
      String message = "usage: java " + programName + key_string + "\n" + programName
          + ".java: error: unrecognized arguments:" + tooManyArguments;
      throw new TooManyArguments(message);
    }
  }

  public String getArgumentValue(String argument) {
    return arguments.get(argument).getArgumentValue();
  }

  public Argument.DataType getArgumentDataType(String argument) {
    return arguments.get(argument).getArgumentDataType();
  }

  public String getArgumentDataTypeString(String argument) {
    return arguments.get(argument).getargumentDataTypeString();
  }

  public String getHelpMessage() {
    String message = "";

    message = "usage: java " + programName + getParameterString() + "\n" + programDescription + "\n"
        + "positional arguments:";
    for (String argNameIterator : arguments.keySet()) {
      Argument currentArgumentIterator = arguments.get(argNameIterator);
      message += "\n   " + argNameIterator + " " + currentArgumentIterator.getArgumentDescription();
    }

    return message;
  }

  public int getNumberArguments() {
    return arguments.size();
  }

  private String getParameterString() {
    String key_string = "";
    for (String argNameIterator : arguments.keySet()) {
      key_string += " " + argNameIterator;
    }
    return key_string;
  }

  public String getProgramName() {
    return programName;
  }

  public String getProgramDescription() {
    return programDescription;
  }

  public void parse(String[] args) {
    String key_string = getParameterString();
    checkIfHelpMessage(args);
    checkIfTooFewArguments(args, key_string);
    checkIfTooManyArguments(args, key_string);
    areThereWrongDataTypes(args);
    int i = 0;
    for (String argNameIterator : arguments.keySet()) {
      Argument currentArgumentIterator = arguments.get(argNameIterator);
      currentArgumentIterator.setArgumentValue(args[i]);
      i++;
    }
  }
}
