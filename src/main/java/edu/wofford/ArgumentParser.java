package edu.wofford;

import java.util.*;

public class ArgumentParser {

  private String programName;
  private String programDescription;
  //Map<String, OptionalArgument> optionalarguments = new LinkedHashMap<String, OptionalArgument>();
  private Map<String, Argument> arguments = new LinkedHashMap<String, Argument>();
  private ArrayList<String> argumentNames = new ArrayList<String>();
  private ArrayList<ArrayList<String>> listBadDataTypes = new ArrayList<ArrayList<String>>();
  private Map<String, Flag> flags= new HashMap<String, Flag>();
  private int numberOptionalArguments;

  public ArgumentParser(String programName) {
    this.programName = programName;
    arguments = new LinkedHashMap<>();
    argumentNames = new ArrayList<>();
    flags = new HashMap<>();
    numberOptionalArguments=0;
  }

  public ArgumentParser(String programName, String description) {
    this.programName = programName;
    this.programDescription = description;
    arguments = new LinkedHashMap<>();
    argumentNames = new ArrayList<>();
    flags = new HashMap<>();
    numberOptionalArguments=0;
  }

  public void addArg(String argname) {
    arguments.put(argname, new Argument(argname));
    argumentNames.add(argname);
  }

  public void addArg(String argname, String description) {
    arguments.put(argname, new Argument(argname, description));
    argumentNames.add(argname);
  }

  public void addArg(String argname, Argument.DataType dataType) {
    arguments.put(argname, new Argument(argname, dataType));
    argumentNames.add(argname);
  }

  public void addArg(String argname, String description, Argument.DataType dataType) {
    arguments.put(argname, new Argument(argname, description, dataType));
    argumentNames.add(argname);
  }

  public void addOptionalArgument(String argname, String defaultValue) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue));
    argumentNames.add(argname);
    numberOptionalArguments++;
  }

  public void addOptionalArgument(String argname, String defaultValue, String description) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, description));
    argumentNames.add(argname);
    numberOptionalArguments++;
  }

  public void addOptionalArgument(String argname, String defaultValue, Argument.DataType dataType) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, dataType));
    argumentNames.add(argname);
    numberOptionalArguments++;
  }

  public void addOptionalArgument(String argname, String defaultValue, Argument.DataType dataType, String description) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, dataType, description));
    argumentNames.add(argname);
    numberOptionalArguments++;
  }


  public void addFlag(String argname){
    flags.put(("--"+argname), new Flag(("--"+argname)));
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
  public void checkForWrongDataTypes() {

    int sizeBadDataTypes = 0;
    int i = 0;
    for (String argNameIterator : arguments.keySet()) {
      String currentArgumentIteratorDataType = arguments.get(argNameIterator).getargumentDataTypeString();
      String currentArgumentIteratorValue= arguments.get(argNameIterator).getArgumentValue();
      if (currentArgumentIteratorDataType.equals("float")) {
        try {
          float temp = Float.parseFloat(currentArgumentIteratorValue);
        } catch (NumberFormatException e) {
          ArrayList<String> badDataType = new ArrayList<String>();
          badDataType.add(argNameIterator);
          badDataType.add("float");
          badDataType.add(currentArgumentIteratorValue);
          listBadDataTypes.add(badDataType);
          sizeBadDataTypes++;
        }
      } else if (currentArgumentIteratorDataType.equals("int")) {
        try {
          int temp = Integer.parseInt(currentArgumentIteratorValue);
        } catch (NumberFormatException e) {
          ArrayList<String> badDataType = new ArrayList<String>();
          badDataType.add(argNameIterator);
          badDataType.add("int");
          badDataType.add(currentArgumentIteratorValue);
          listBadDataTypes.add(badDataType);
          sizeBadDataTypes++;
        }
      } else if (currentArgumentIteratorDataType.equals("boolean")) {
        if (!(currentArgumentIteratorValue.equals("true") || currentArgumentIteratorValue.equals("false"))) {
          ArrayList<String> badDataType = new ArrayList<String>();
          badDataType.add(argNameIterator);
          badDataType.add("boolean");
          badDataType.add(currentArgumentIteratorValue);
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
    // int sizeBadDataTypes = 0;
    // String message = "usage: java " + programName + getParameterString() + "\n" + programName + ".java: error: ";
    // for (String argNameIterator : arguments.keySet()) {
    //   Argument currentArgumentIterator = arguments.get(argNameIterator);
    //   String currentArgumentIteratorDataTypeString = currentArgumentIterator.getargumentDataTypeString();
    //   String currentArgumentIteratorValue = currentArgumentIterator.getArgumentValue();
    //   if (currentArgumentIteratorDataTypeString.equals("float")) {
    //     try {
    //       float temp = Float.parseFloat(currentArgumentIteratorValue);
    //     } catch (NumberFormatException e) {
    //       sizeBadDataTypes++;
    //     }
    //   } else if (currentArgumentIteratorDataTypeString.equals("int")) {
    //     try {
    //       int temp = Integer.parseInt(currentArgumentIteratorValue);
    //     } catch (NumberFormatException e) {
    //       sizeBadDataTypes++;
    //     }
    //   } else if (currentArgumentIteratorDataTypeString.equals("boolean")) {
    //     if (!(currentArgumentIteratorValue.toLowerCase().equals("true")
    //         || currentArgumentIteratorValue.toLowerCase().equals("false"))) {
    //       sizeBadDataTypes++;
    //     }
    //   }
    //   if (sizeBadDataTypes == 1) {
    //     message += "argument " + argNameIterator + ":" + " invalid " + currentArgumentIteratorDataTypeString
    //         + " value: " + currentArgumentIteratorValue;
    //   } else if (sizeBadDataTypes == 2) {
    //     message += "\n" + "argument " + argNameIterator + ":" + " invalid " + currentArgumentIteratorDataTypeString
    //         + " value: " + currentArgumentIteratorValue + "\n";
    //   } else if (sizeBadDataTypes >= 3) {
    //     message += "argument " + argNameIterator + ":" + " invalid " + currentArgumentIteratorDataTypeString
    //         + " value: " + currentArgumentIteratorValue + "\n";
    //   }

    // }
    // if (sizeBadDataTypes > 0) {
    //   throw new HelpException(message);
    // }
  }

  private void tooFewArgumentsProvided(ArrayList<String> argumentsLeft) {
    String missingArguements = "";

    for (int i = 0; i <argumentsLeft.size(); i++) {

      missingArguements += " " + argumentsLeft.get(i);
    }
    String message = "usage: java " + programName + getParameterString() + "\n" + programName
        + ".java: error: the following arguments are required:" + missingArguements;
    throw new TooFewArguments(message);
  }

  private void tooManyArgumentsProvided(ArrayList <String> badArguments) {
    String tooManyArguments = "";
    for (int i = 0; i < badArguments.size(); i++) {
      tooManyArguments += " " + badArguments.get(i);
    }
    String message = "usage: java " + programName + getParameterString() + "\n" + programName
        + ".java: error: unrecognized arguments:" + tooManyArguments;
    throw new TooManyArguments(message);
  }

  public String getArgumentValue(String argument) {
    return arguments.get(argument).getArgumentValue();
  }

  public String getOptionalArgumentValue(String optionalArgName) {
    return ((OptionalArgument) arguments.get(optionalArgName)).getOptionalArgumentValue();
  }

  public Argument.DataType getArgumentDataType(String argument) {
    return arguments.get(argument).getArgumentDataType();
  }

  public Argument.DataType getOptionalArgumentDataType(String argument) {
    return ((OptionalArgument) arguments.get(argument)).getArgumentDataType();
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
      message += "\n   " + argNameIterator + " " + currentArgumentIterator.getArgumentDescription()+" (" + currentArgumentIterator.getargumentDataTypeString()+")";
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
    ArrayList<String> badArguments = new ArrayList<String>();
    Boolean isThereOptionalArgument = false;
    for (int i = 0; i < args.length; i++) {
      String argumentValue = args[i];
      //help message logic
      if (argumentValue.equals("-h")) {
        String message = getHelpMessage();
        throw new HelpException(message);
      }
      else if(flags.get(argumentValue) != null){
        flags.get(argumentValue).flagIsPresent();
        if(argumentValue.equals("--help")){
          String message = getHelpMessage();
          throw new HelpException(message);
        }
      }
      //when optionalArgument is first encountered
      else if (argumentValue.contains("--") && isThereOptionalArgument == false) {
        isThereOptionalArgument = true;
      }
      //if a value is encountered after a optional Argument
      else if (isThereOptionalArgument && (arguments.get(args[i]) == null)) {
        if (argumentNames.isEmpty()) {
          badArguments.add(args[i]);
        } else {
          ((OptionalArgument)arguments.get(argumentNames.get(0))).setOptionalArgumentValue(argumentValue);
          argumentNames.remove(0);
          isThereOptionalArgument = false;
        }
      }
      //if a optionalArgument proceeds a optionalArgument
      else if (argumentValue.contains("--") && isThereOptionalArgument) {
        if (argumentNames.isEmpty()) {
          badArguments.add(args[i]);
        } else {
          argumentNames.remove(0);
         
        }
      }
      //special case if last value is a optionalArgument
      else if(i==args.length && argumentValue.contains("--")){
        argumentNames.remove(0);

      }

      // if its just a regular value
      else {
        if (argumentNames.isEmpty()) {
          badArguments.add(args[i]);
        } else {
          arguments.get(argumentNames.get(0)).setArgumentValue(argumentValue);
          argumentNames.remove(0);
        }
      }

    }
    if (badArguments.size() > 0) {
      tooManyArgumentsProvided(badArguments);
    }
    if(argumentNames.size()> numberOptionalArguments){
      tooFewArgumentsProvided(argumentNames);
    }
    checkForWrongDataTypes();

  }
}
