package edu.wofford;

import java.util.*;

public class ArgParser {
  private String programName;
  private String programDescription;
  private Map<String, String> shortToLong;
  private ArrayList<String> argumentNames;
  private HashSet<String> requiredArgs;
  private HashSet<String> flagNames;
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

  public void addArg(Arg arg) {
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

  public void addOptArg(OptArg arg) {
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

  public void addFlagToList(String flagName) {
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

  public int getNumberArgs() {
    return arguments.size();
  }

  public String getParameterString() {
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

  public String getErrorUsage() {
    String errorMessage = "usage: java " + this.programName + getParameterString() + "\n" + this.programName;
    return errorMessage;
  }

  private String doesOptionalArgumentExist(String commandLineName) {
    String aname = commandLineName.replace("-", "");
    if (arguments.get(aname) == null) {
      throw new ArgDoesNotExistException(aname);
    }
    return aname;
  }

  private boolean isArgAFlag(String flagName) {
    if (arguments.get(flagName) != null) {
      arguments.get(flagName).setValue("true");
    }
    return arguments.get(flagName) != null;
  }

  private boolean argIsACollectionOfFlags(String flagNames) {
    for (int j = 0; j < flagNames.length(); j++) {
      String flagIterator = String.valueOf(flagNames.charAt(j));
      if (arguments.get(flagIterator) != null) {
        arguments.get(flagIterator).setValue("true");
      } else {
        throw new FlagDoesNotExistException(flagIterator);
      }
    }
    return true;
  }

  private boolean doesArgHaveRestrictedValues(Arg argument, String argValue) {
    if (argument.getRestrictedValuesString() != null && argument.getRestrictedValuesString().length() > 0) {
      HashSet<String> argRestrictedValues = argument.getRestrictedValues();
      if (argRestrictedValues.contains(argValue)) {
        argument.setValue(argValue);
        return true;
      } else {
        throw new IllegalArgumentException(argValue + " is not an allowed value for " + argument.getName());
      }
    }
    return false;

  }

  private void removeArgIfRequired(String argname) {
    if (requiredArgs.contains(argname)) {
      requiredArgs.remove(argname);
    }

  }

  private String isArgAShortName(String shortName) {

    if (shortToLong.get(shortName) == null) {
      throw new ArgDoesNotExistException(shortName );
    } else {
      return shortToLong.get(shortName);
    }
  }

  public void parse(String[] args) {
    int usedArguments = 0;

    for (int i = 0; i < args.length; i++) {

      String aname = "";
      if (args[i].equals("-h") || args[i].equals("--help")) {
        throw new HelpException(this);
      }

      else if (args[i].startsWith("-")) {
        if (args[i].startsWith("--")) {
          aname = doesOptionalArgumentExist(args[i]);
        }
        //argument is a flag, collection of flags, or a short name
        else {
          String sname = args[i].substring(1);
          if (isArgAFlag(sname)) {
            continue;
          } else if (sname.length() > 1) {
            argIsACollectionOfFlags(sname);
            continue;
          }
          aname = isArgAShortName(sname);
          usedArguments++;
        }

        //dealing with optional arguments
        Arg a = arguments.get(aname);
        if (a.getDataType() == Arg.DataType.BOOLEAN) {
          a.setValue("true");
        }

        else {
          if (checkType(args[i + 1], a.getDataType())) {

            if (doesArgHaveRestrictedValues(a, args[i + 1])) {
              removeArgIfRequired(aname);
              i++;

            } else {
              removeArgIfRequired(aname);
              a.setValue(args[i + 1]);
              i++;
            }

          } else {
            throw new BadDataTypeException(this, a, args[i + 1]);
          }
        }
      }

      else {
        // Regular argument value 
        if (usedArguments == argumentNames.size()) {
          throw new TooManyArguments(this, args[i]);
        } else {
          aname = argumentNames.get(usedArguments);
          Arg a = arguments.get(aname);
          if (checkType(args[i], a.getDataType())) {

            if (doesArgHaveRestrictedValues(a, args[i])) {
              usedArguments++;
            } else {
              a.setValue(args[i]);
              usedArguments++;
            }
          } else {
            throw new BadDataTypeException(this, a, args[i]);
          }
        }
      }

    }

    if (usedArguments < argumentNames.size()) {
      throw new TooFewArguments(this, usedArguments, argumentNames);

    } else if (requiredArgs.size() > 0) {
      throw new RequiredArgException(requiredArgs);

    }

  }

  public void setArgShortFormName(String argument, String shortFormName) {
    arguments.get(argument).setShortFormName(shortFormName);
    if (shortToLong.get(shortFormName) != null || shortFormName.equals("h")) {
      throw new ShortFormNameException(shortFormName);
    }
    shortToLong.put(shortFormName, argument);
  }

  public void setArgRestricedValues(String argument, String restrictedValues) {
    arguments.get(argument).setRestrictedValues(restrictedValues);

  }

  public void setArgAsRequired(String argument) {
    if (arguments.get(argument).isArgRequired() == false) {
      arguments.get(argument).makeArgRequired();
      requiredArgs.add(argument);
    }
  }
}
