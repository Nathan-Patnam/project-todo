package edu.wofford;

import java.util.*;


public class ArgParser {
  private String programName;
  private String programDescription;
  private Map<String, String> shortToLong;
  private ArrayList<String> argumentNames;
  private HashSet<String> requiredArgs;
  private  HashSet<String> flagNames;
  private Map<String, Arg> arguments;

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
    requiredArgs = new HashSet<>();

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

  public void addArg(Arg arg){
    arguments.put(arg.getName(), arg);
    argumentNames.add(arg.getName());

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

  public void addOptArg(OptArg arg){
    arguments.put(arg.getName(), arg);
    argumentNames.add(arg.getName());

  }


  public void addFlag(String argname) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN));
    flagNames.add(argname);
  }

  public void addFlag(String argname, String description) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN, description));
    flagNames.add(argname);
  }


  public void addFlagToList(String flagName){
    flagNames.add(flagName);
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

  public Map<String, Arg> getAllArgs() {
    return this.arguments;
  }

  public HashSet<String> getFlagNames() {
    return this.flagNames;
  }

  /**
  * Returns the value that the argument holds. If no value has been set for the argument then it will return null
  * and if the argument doesn't exist then an error will be thrown.
  * @param  argument  the name of the arugment you want the value of
  * @return           the value associated with that argument
  * 
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
      if (!argNameIterator.equals("help") && !argNameIterator.equals("h")) {
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
      if (!argNameIterator.equals("help") && !argNameIterator.equals("h")) {
        key_string += " " + argNameIterator;
      }
    }
    return key_string;
  }

  public String setProgramName(String programName) {
    return this.programName = programName;
  }

  public String setProgramDescription(String programDescription) {
    return this.programDescription = programDescription;
  }

  public String getProgramName() {
    return programName;
  }

  public String getProgramDescription() {
    return programDescription;
  }

  private void tooManyInputsGiven(){
    String requiredArgString = "";
    for (String requiredArgs : requiredArgs) {
      requiredArgString += requiredArgs + " ";
    }
    throw new IllegalArgumentException("The argument(s) " + requiredArgString + "are required");
  }


  private void tooFewInputsGiven(int usedArguments){
    String missingArguements = "";
    for (int i = usedArguments; i < argumentNames.size(); i++) {
      missingArguements += " " + argumentNames.get(i);
    }
    String message = "usage: java " + programName + getParameterString() + "\n" + programName
        + ".java: error: the following arguments are required:" + missingArguements;
    throw new TooFewArguments(message);
    }


  public void parse(String[] args) {
    HashSet<String> argRestrictedValues;
    int usedArguments = 0;
    for (int i = 0; i < args.length; i++) {
      boolean isArgAFlag = false;
      String aname = "";
      if (args[i].equals("-h") || args[i].equals("--help")) {
        throw new HelpException(getHelpMessage());
      }

      else if (args[i].startsWith("-")) {
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

            if (a.getRestrictedValuesString() != null && a.getRestrictedValuesString().length() > 0) {
              argRestrictedValues = a.getRestrictedValues();
              if (argRestrictedValues.contains(args[i + 1])) {
                if (requiredArgs.contains(aname)) {
                  requiredArgs.remove(aname);
                }

                a.setValue(args[i + 1]);
                i++;
              } else {
                throw new IllegalArgumentException(args[i + 1] + " is not an allowed value for " + aname);
              }
            } else {
              if (requiredArgs.contains(aname)) {
                requiredArgs.remove(aname);
              }
              a.setValue(args[i + 1]);
              i++;
            }

          } else {
            // Throw some exception
            String message = "";
            message = "usage: java " + programName + getParameterString() + "\n" + programName + ".java: error: "
                + "argument " + aname + ":" + " invalid " + a.getDataType().toString() + " value: " + args[i + 1];

            throw new BadDataType(message);
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

            if (a.getRestrictedValuesString() != null && a.getRestrictedValuesString().length() > 0) {
              argRestrictedValues = a.getRestrictedValues();
              if (argRestrictedValues.contains(args[i])) {
                a.setValue(args[i]);
                usedArguments++;
              } else {
                throw new IllegalArgumentException(args[i] + " is not an allowed value for " + aname);
              }
            } else {
              a.setValue(args[i]);
              usedArguments++;
            }
          } else {
            String message = "";
            message = "usage: java " + programName + getParameterString() + "\n" + programName + ".java: error: "
                + "argument " + aname + ":" + " invalid " + a.getDataType().toString() + " value: " + args[i];

            throw new BadDataType(message);
          }
        }
      }

    }

    if (usedArguments < argumentNames.size()) {
      System.out.println(usedArguments);
  
      tooFewInputsGiven(usedArguments);

    }
    else if (requiredArgs.size() > 0) {
      tooManyInputsGiven();


  }

  
}



public void setArgShortFormName(String argument, String shortFormName) {
  arguments.get(argument).setShortFormName(shortFormName);
  if (shortToLong.get(shortFormName) != null || shortFormName.equals("h")) {
    throw new IllegalArgumentException("The short form name " + shortFormName + " is already in uses");
  }
  shortToLong.put(shortFormName, argument);
}

public void setArgRestricedValues(String argument, String restrictedValues) {
  arguments.get(argument).setRestrictedValues(restrictedValues);

}

public void setArgAsRequired(String argument) {
  if(arguments.get(argument).isArgRequired()==false){
    arguments.get(argument).makeArgRequired();
    requiredArgs.add(argument);
  }
}
}
