package edu.wofford;

import java.util.*;

public class ArgumentParser {

  private String programName;
  private String programDescription;
  private Map<String, String> shortToLong;
  private Map<String, Argument> arguments;
  private ArrayList<String> argumentNames;
  //private ArrayList<ArrayList<String>> listBadDataTypes = new ArrayList<ArrayList<String>>();
  //private Map<String, Flag> flags = new HashMap<String, Flag>();
  private int numberOptionalArguments;

  public ArgumentParser(String programName) {
    this(programName, "");
  }

  public ArgumentParser(String programName, String description) {
    this.programName = programName;
    this.programDescription = description;
    arguments = new LinkedHashMap<>();
    argumentNames = new ArrayList<>();
    shortToLong = new HashMap<>();
    numberOptionalArguments = 0;
  }

  public void addArg(String argname) {
    addArg(argname, "", Argument.DataType.STRING);
  }

  public void addArg(String argname, String description) {
    addArg(argname, description, Argument.DataType.STRING);
  }

  public void addArg(String argname, Argument.DataType dataType) {
    addArg(argname, "", dataType);
  }

  public void addArg(String argname, String description, Argument.DataType dataType) {
    arguments.put(argname, new Argument(argname, description, dataType));
    argumentNames.add(argname);
  }

  public void addOptionalArgument(String argname, String defaultValue) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue));
    numberOptionalArguments++;
  }

  public void addOptionalArgument(String argname, String defaultValue, String description) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, description));
    numberOptionalArguments++;
  }

  public void addOptionalArgument(String argname, String defaultValue, Argument.DataType dataType) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, dataType));
    numberOptionalArguments++;
  }

  public void addOptionalArgument(String argname, String defaultValue, Argument.DataType dataType, String description) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, dataType, description));
    numberOptionalArguments++;
  }

  /*
  public void addFlag(String argname) {
    arguments.put("--" + argname, new OptionalArgument("--" + argname, "false", Argument.DataType.BOOLEAN));
  }
  */

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


  private void tooFewArgumentsProvided(ArrayList<String> argumentsLeft) {
    String missingArguements = "";

    for (int i = 0; i < argumentsLeft.size(); i++) {

      missingArguements += " " + argumentsLeft.get(i);
    }
    String message = "usage: java " + programName + getParameterString() + "\n" + programName
        + ".java: error: the following arguments are required:" + missingArguements;
    throw new TooFewArguments(message);
  }

  private void tooManyArgumentsProvided(ArrayList<String> badArguments) {
    String tooManyArguments = "";
    for (int i = 0; i < badArguments.size(); i++) {
      tooManyArguments += " " + badArguments.get(i);
    }
    String message = "usage: java " + programName + getParameterString() + "\n" + programName
        + ".java: error: unrecognized arguments:" + tooManyArguments;
    throw new TooManyArguments(message);
  }

  private boolean checkType(String value, Argument.DataType type) {
    switch(type) {
      case BOOLEAN:
        return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
      case INT:
        try { Integer.parseInt(value); return true; } 
        catch(NumberFormatException e) { return false; }
      case FLOAT:
        try { Float.parseFloat(value); return true; } 
        catch(NumberFormatException e) { return false; }
      default:
        return true;
    }
  }

  public String getArgumentValue(String argument) {
    return arguments.get(argument).getValue();
  }

  public String getOptionalArgumentValue(String optionalArgName) {
    return arguments.get(optionalArgName).getValue();  
  }

  public String getOptionalDescription(String optionalArgName) {
    return arguments.get(optionalArgName).getDescription();
  }

  public Argument.DataType getArgumentDataType(String argument) {
    return arguments.get(argument).getDataType();
  }

  public Argument.DataType getOptionalArgumentDataType(String argument) {
    return ((OptionalArgument) arguments.get(argument)).getDataType();
  }

  public String getArgumentDataTypeString(String argument) {
    return arguments.get(argument).getDataType().toString();
  }

  public String getHelpMessage() {
    String message = "";

    message = "usage: java " + programName + getParameterString() + "\n" + programDescription + "\n"
        + "positional arguments:";
    for (String argNameIterator : arguments.keySet()) {
      Argument currentArgumentIterator = arguments.get(argNameIterator);
      message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
          + currentArgumentIterator.getDataType().toString() + ")";
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

  private Boolean isArgAOptionalArgument(String argValue) {
    return argValue.matches("^--[A-Za-z0-9]");
  }

  private Boolean isArgaFlag(String argValue) {
    return argValue.matches("^-[A-Za-z0-9]");
  }

  public void parse(String[] args) {
    
    int usedArguments = 0;
    for(int i = 0; i < args.length; i++) {
      String aname = "";
      if(args[i].equals("-h") || args[i].equals("--help")) {
        throw new HelpException("hi");
      }
      else if(args[i].startsWith("-")) {
        if(args[i].startsWith("--")) {
          aname = args[i].substring(2);
        }
        else {
          String sname = args[i].substring(1);
          aname = shortToLong.get(sname);
          if(aname == null) {
            // OOPS. The short name isn't valid.
          }
        }
        Argument a = arguments.get(aname);
        if(a.getDataType() == Argument.DataType.BOOLEAN) {
          a.setValue("true");
        }
        else {
          if(checkType(args[i + 1], a.getDataType())) {
            a.setValue(args[i + 1]);
            i++;
          }
          else {
            // Throw some exception
            String message = "";
            message = "usage: java " + programName + getParameterString() + "\n" + 
                      programName + ".java: error: " +
                      "argument " + aname + ":" + " invalid " + a.getDataType().toString() +
                      " value: " + args[i + 1];
            
            throw new HelpException(message);
          }
        }      
      }
      else {
        // Regular argument value
        if(usedArguments == argumentNames.size()) {
          // Too many args exception
          throw new TooManyArguments("suck it");
        }
        else {
          aname = argumentNames.get(usedArguments);
          Argument a = arguments.get(aname);
          if(checkType(args[i], a.getDataType())) {
            a.setValue(args[i]);
            usedArguments++;
          }
          else {
            String message = "";
            message = "usage: java " + programName + getParameterString() + "\n" + 
                      programName + ".java: error: " +
                      "argument " + aname + ":" + " invalid " + a.getDataType().toString() +
                      " value: " + args[i];
            
            throw new HelpException(message);
          }
        }
      }
    }
    if(usedArguments < argumentNames.size()) {
      // Too few args exception
      throw new TooFewArguments("whatever");
    }


    /*

    ArrayList<String> badArguments = new ArrayList<String>();
    Boolean isThereOptionalArgument = false;
    String lastKnowOptionalArgument = "";
    int lastIndexinArgs = args.length - 1;
    for (int i = 0; i < args.length; i++) {
      String argumentValue = args[i];
      Boolean argumentIsAOptionalArgument = isArgAOptionalArgument(argumentValue);
      Boolean argumentIsAFlag = isArgaFlag(argumentValue);
      
      //argument is a optional Argument
      if (argumentIsAOptionalArgument) {
        if (argumentValue.equals("--help")) {
          String message = getHelpMessage();
          throw new HelpException(message);
        }

        else if (arguments.get(argumentValue.substring(2)) == null) {
          throw new IllegalArgumentException("argument " + argumentValue.replace("--", " ") + "does not exist");
        }

        else if (i == lastIndexinArgs) {
          argumentNames.remove(0);
        }

        //when optionalArgument is first encountered
        if (!isThereOptionalArgument) {
          isThereOptionalArgument = true;
          lastKnowOptionalArgument = argumentValue.substring(2);
        }
        //if a optional argument is followed by another optionalArgument
        else if (isThereOptionalArgument) {
          if (argumentNames.isEmpty()) {
            badArguments.add(args[i]);
          } else {
            argumentNames.remove(0);
            lastKnowOptionalArgument = argumentValue.substring(2);
          }
        }

      }
      //Argument in a Flag
      else if (argumentIsAFlag) {
        if (argumentValue.equals("-h")) {
          String message = getHelpMessage();
          throw new HelpException(message);
        } else if (arguments.get(argumentValue.substring(1)) == null) {
          throw new IllegalArgumentException("argument " + argumentValue.replace("-", " ") + "does not exist");
        } else {
          arguments.get(argumentValue).setValue("true");
        }
      }

      //argument is a value
      else {
        //if a value is encountered after a optional Argument
        if (isThereOptionalArgument) {

          if (argumentNames.isEmpty() && i != lastIndexinArgs) {
            badArguments.add(args[i]);
          } 
          else {
            ((OptionalArgument)arguments.get(lastKnowOptionalArgument)).setOptionalArgumentValue(argumentValue);
            isThereOptionalArgument = false;
            if(i!= lastIndexinArgs){
              argumentNames.remove(0);
            }
          }

        }
        else {
          if (argumentNames.isEmpty()) {
            badArguments.add(args[i]);
          }
          else{
            arguments.get(argumentNames.get(0)).setValue(argumentValue);
            argumentNames.remove(0);
          }
        }

      }

    }
    if (badArguments.size() > numberOptionalArguments) {
      tooManyArgumentsProvided(badArguments);
    }
    if (argumentNames.size() > numberOptionalArguments) {
      tooFewArgumentsProvided(argumentNames);
    }
    checkForWrongDataTypes();

    */
  }

}
