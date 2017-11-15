package edu.wofford;

import java.util.*;

public class ArgumentParser {
  private String programName;
  private String programDescription;
  private Map<String, String> shortToLong;
  private Map<String, Argument> arguments;
  private ArrayList<String> argumentNames;

  public ArgumentParser(String programName) {
    this(programName, "");
  }

  public ArgumentParser(String programName, String description) {
    this.programName = programName;
    this.programDescription = description;
    arguments = new LinkedHashMap<>();
    argumentNames = new ArrayList<>();
    shortToLong = new HashMap<>();
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

  }

  public void addOptionalArgument(String argname, String defaultValue, String description) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, description));

  }

  public void addOptionalArgument(String argname, String defaultValue, Argument.DataType dataType) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, dataType));

  }

  public void addOptionalArgument(String argname, String defaultValue, Argument.DataType dataType, String description) {
    arguments.put(argname, new OptionalArgument(argname, defaultValue, dataType, description));

  }

  public void setArgumentShortFormName(String argument, String shortFormName) {
    arguments.get(argument).setShortFormName(shortFormName);
    shortToLong.put(shortFormName, argument);
  }

  public void addFlag(String argname) {
    arguments.put(argname, new OptionalArgument(argname, false, Argument.DataType.BOOLEAN));
  }

  private boolean checkType(String value, Argument.DataType type) {
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

  public Argument getArgument(String argument) {
    return arguments.get(argument);
  }

  public String getArgumentValue(String argument) {
    return arguments.get(argument).getValue();
  }

  public String getArgumentDescription(String argument) {
    return arguments.get(argument).getDescription();
  }

  public Argument.DataType getArgumentDataType(String argument) {
    return arguments.get(argument).getDataType();
  }

  public String getArgumentDataTypeString(String argument) {
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
        Argument currentArgumentIterator = arguments.get(argNameIterator);
        message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
            + currentArgumentIterator.getDataType().toString() + ")";
      }
    }
    return message;
  }

  public int getNumberArguments() {
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
        } else {
          String sname = args[i].substring(1);
          //argument is a flag
          if (arguments.get(sname) != null) {
            arguments.get(sname).setValue("true");
            usedArguments++;
            isArgAFlag = true;
            continue;
          }
          //argument is a collection of flags
          else if (sname.length() > 1) {
            for (int j = 0; i < sname.length(); j++) {
              String flagIterator = String.valueOf(sname.charAt(j));
              if (arguments.get(sname) != null) {
                arguments.get(flagIterator).setValue("true");
                isArgAFlag = true;
              } else {
                throw new IllegalArgumentException("flag " + flagIterator + "does not exist");
              }
            }
            usedArguments++;
            continue;
          }

          aname = shortToLong.get(sname);
          if (aname == null && !isArgAFlag) {
            throw new IllegalArgumentException("argument " + args[i].substring(1) + " does not exist");
          }
        }

        Argument a = arguments.get(aname);
        if (a.getDataType() == Argument.DataType.BOOLEAN) {
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
          Argument a = arguments.get(aname);
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

}
