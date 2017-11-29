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

  /**
  * Constructor for an ArgParse object given a program name 
  * @param  programName, the String value that is the name of the program 
  * @return nothing 
  */
  public ArgParser(String programName) {
    this(programName, "");
  }

  /**
  * Constructor for an ArgParser object given a program name and a description
  * @param  programName, the String value that is the name of the program 
  *			description, the String value that is the description 
  * @return nothing 
  */
  public ArgParser(String programName, String description) {
    this.programName = programName;
    this.programDescription = description;
    arguments = new LinkedHashMap<>();
    argumentNames = new ArrayList<>();
    shortToLong = new HashMap<>();
    flagNames = new HashSet<>();
    requiredArgs = new HashSet<>();

  }

  /**
  * Adds an argument with the given name. 
  * @param  argname, the String value that is the name of the argument to be added 
  * @return nothing 
  */
  public void addArg(String argname) {
    addArg(argname, "", Arg.DataType.STRING);
  }

  /**
  * Adds an argument with the given name and the given description. 
  * @param argname, the String value that is the name of the argument to be added
  *		  description, the String value that is the description of the argument to be added
  * @return nothing 
  */
  public void addArg(String argname, String description) {
    addArg(argname, description, Arg.DataType.STRING);
  }

  /**
  * Adds an argument with the given name and the given data type. 
  * @param argname, the String value that is the name of the argument to be added
  *		   dataType, the DataType value that is the data type of the argument to be added
  * @return nothing 
  */
  public void addArg(String argname, Arg.DataType dataType) {
    addArg(argname, "", dataType);
  }

  /**
  * Adds an argument with the given name, the given description, and the given data type. 
  * @param argname, the String value that is the name of the argument to be added
  *		  description, the String value that is the description of the argument to be added
  *		  dataType, the DataType value that is the data type of the argument to be added
  * @return nothing 
  */
  public void addArg(String argname, String description, Arg.DataType dataType) {
    arguments.put(argname, new Arg(argname, description, dataType));
    argumentNames.add(argname);
  }

  /**
  * Adds an argument that has already been created. 
  * @param  arg, an argument object
  * @return nothing 
  */
  public void addArg(Arg arg){
    arguments.put(arg.getName(), arg);
    argumentNames.add(arg.getName());

  }

  /**
  * Adds an optional argument with given name and default value. 
  * @param  argname, the String value that is the name of the optional argument being added
  *         defaultValue, the string value taht is the value of the optional argument being added.
  * @return nothing 
  */  
  public void addOptArg(String argname, String defaultValue) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, "");

  }

  /**
  * Adds an optional argument with given name, default value, and description. 
  * @param  argname, the String value that is the name of the optional argument being added
  *         defaultValue, the string value taht is the value of the optional argument being added.
  *		    description, the String value that is the description of the optional argument to be added
  * @return nothing 
  */  
  public void addOptArg(String argname, String defaultValue, String description) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, description);

  }

  /**
  * Adds an optional argument with given name, default value, and data type. 
  * @param  argname, the String value that is the name of the optional argument being added
  *         defaultValue, the string value taht is the value of the optional argument being added.
  *		    dataType, the String value that is the data type of the optional argument to be added
  * @return nothing 
  */  
  public void addOptArg(String argname, String defaultValue, Arg.DataType dataType) {
    addOptArg(argname, defaultValue, dataType, "");

  }

  /**
  * Adds an optional argument with given name, default value, data type, and description. 
  * @param  argname, the String value that is the name of the optional argument being added
  *         defaultValue, the string value taht is the value of the optional argument being added.
  *		    dataType, the String value that is the data type of the optional argument to be added
  *			description, the String value that is the description of the optional argument to be added
  * @return nothing 
  */  
  public void addOptArg(String argname, String defaultValue, Arg.DataType dataType, String description) {
    arguments.put(argname, new OptArg(argname, defaultValue, dataType, description));
  }

  /**
  * Add a flag, which is a boolean optional argument.
  * @param  argname, the string value that is the name of the flag
  * @return nnothing 
  */
  public void addFlag(String argname) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN));
    flagNames.add(argname);
  }

  /**
  * Add a flag, which is a boolean optional argument with a name and description. 
  * @param  argname, the string value that is the name of the flag
  *			description, the String value that is the description of teh flag 
  * @return nnothing 
  */
  public void addFlag(String argname, String description) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN, description));
    flagNames.add(argname);
  }

  /**
  * Adds a flag to the list of flag names 
  * @param  flagName, the String value that is the name  of the flag 
  * @return nothing 
  */
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

  /**
  *Returns the argument object that is specified by the given argument name 
  *@param  argument, the String value that is the ame of the argument object you want to return 
  *@return the object argument associated with the argument name 
  */
  public Arg getArgument(String argument) {
    return arguments.get(argument);
  }

  /**
  * Returns the map of argument names and argument objects 
  * @param  none 
  * @return map of argument names and argument objects 
  */
  public Map<String, Arg> getAllArgs() {
    return this.arguments;
  }

  /**
  * Returns all of the flag names 
  * @param  none 
  * @return HashSet of all of the flag names 
  */
  public HashSet<String> getFlagNames() {
    return this.flagNames;
  }

  /**
  * Returns the value that the argument holds. If no value has been set for the argument then it will return null
  * and if the argument doesn't exist then an error will be thrown.
  * @param  argument, the name of the arugment you want the value of
  * @return the value associated with that argument
  */
  public String getArgValue(String argument) {
    return arguments.get(argument).getValue();
  }

  /**
  * Returns the description of the argument. If no description has been set for the argument then it will
  * return an empty string. If the arugment doesn't exists an error will be thrown.
  * @param  argument, the name of the arugment you want the description of
  * @return the value associated with that argument
  */
  public String getArgDescription(String argument) {
    return arguments.get(argument).getDescription();
  }

  /**
  * Returns the data type of the argument. If no data type has been set for the argument then it will
  * return an empty string. If the argument doesn't exists an error will be thrown.
  * @param   argument  the name of the arugment you want the data type of
  * @return  the value associated with that argument
  */
  public Arg.DataType getArgDataType(String argument) {
    return arguments.get(argument).getDataType();
  }

  /**
  * Returns the string form of the data type of the argument. If no data type has been set for the argument then it will
  * return an empty string. If the argument doesn't exists and error will be thrown.
  * @param   argument  the name of the arugment you want the data type of
  * @return  the String form of the value associated with that argument
  */
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

  /**
  * Returns the number of arguments that have been given.
  * If no arguments have been given then it should return 0.
  * @param   none
  * @return  an int value specifying the number of given arguments
  */
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

  /**
  * Sets the value for the name of the program. 
  * @param  programName, the String value that is the name of the program 
  * @return nothing 
  */
  public String setProgramName(String programName) {
    return this.programName = programName;
  }

  /**
  * Sets the value for the description of the program. 
  * @param  programDescription, the String value that is the description of the program 
  * @return nothing 
  */
  public String setProgramDescription(String programDescription) {
    return this.programDescription = programDescription;
  }

  
  /**
  * Returns the value for the name of the program. 
  * @param  none
  * @return the String value that is the name of the program
  */
  public String getProgramName() {
    return programName;
  }

  /**
  * Returns the value for the description of the program. 
  * @param  none
  * @return the String description of the program 
  */
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

  /**
  * Parses the values passed in  
  * @param  args, a String array of argument values
  * @return nothing 
  */
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

            throw new HelpException(message);
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


/**
* Sets a short form name for the given argument 
* @param argument, the String value that is the the argument to which you want to set a short form name 
*		 shortFormName, String value that is the short form of the argument name 
* @return nothing 
*/
public void setArgShortFormName(String argument, String shortFormName) {
  arguments.get(argument).setShortFormName(shortFormName);
  if (shortToLong.get(shortFormName) != null || shortFormName.equals("h")) {
    throw new IllegalArgumentException("The short form name " + shortFormName + " is already in uses");
  }
  shortToLong.put(shortFormName, argument);
}

/**
* Sets restricted values for this argument
* @param  argument, the String value for which you want to set restricted values 
*		  restrictedValues, the String that contains the restricted values, separated by spaces
* @return nothing 
*/
public void setArgRestricedValues(String argument, String restrictedValues) {
  arguments.get(argument).setRestrictedValues(restrictedValues);

}

/**
*Makes an argument a required argument
*@param  argument, the String value that is the argument that you want to make required
*@return nothing 
*/
public void setArgAsRequired(String argument) {
  if(arguments.get(argument).isArgRequired()==false){
    arguments.get(argument).makeArgRequired();
    requiredArgs.add(argument);
  }
}
}
