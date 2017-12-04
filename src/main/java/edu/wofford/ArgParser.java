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
  private ArrayList<String> positionalArgumentNames;

  /**
  * Constructor for an ArgParse object given a program name 
  * @param  programName, name of the program
  * 
  */
  public ArgParser(String programName) {
    this(programName, "");
  }

  /**
  * Constructor for an ArgParser object given a program name and a description
  * @param  programName, name of the program
  *	@param	description, description of the program
  * 
  */
  public ArgParser(String programName, String description) {
    this.programName = programName;
    this.programDescription = description;
    arguments = new LinkedHashMap<>();
    argumentNames = new ArrayList<>();
    shortToLong = new HashMap<>();
    flagNames = new HashSet<>();
    requiredArgs = new HashSet<>();
    positionalArgumentNames = new ArrayList<>();

  }

  /**
  * Adds an argument with the given name. 
  * @param  argname, name of the argument to be added 
  * 
  */
  public void addArg(String argname) {
    addArg(argname, "", Arg.DataType.STRING);
  }

  /**
  * Adds an argument with the given name and the given description. 
  * @param argname, name of the argument to be added
  *	@param description, description of the argument to be added
  * 
  */
  public void addArg(String argname, String description) {
    addArg(argname, description, Arg.DataType.STRING);
  }

  /**
  * Adds an argument with the given name and the given data type. 
  * @param argname, the String value that is the name of the argument to be added
  *	@param dataType, the DataType value that is the data type of the argument to be added
  * 
  */
  public void addArg(String argname, Arg.DataType dataType) {
    addArg(argname, "", dataType);
  }

  /**
  * Adds an argument with the given name, the given description, and the given data type. 
  * @param argname, the String value that is the name of the argument to be added
  * @param description, the String value that is the description of the argument to be added
  *	@param dataType, the DataType value that is the data type of the argument to be added
  * 
  */
  public void addArg(String argname, String description, Arg.DataType dataType) {
    arguments.put(argname, new Arg(argname, description, dataType));
    argumentNames.add(argname);
    positionalArgumentNames.add(argname);
  }

  /**
  * Adds an argument that has already been created. 
  * @param  arg, an argument object
  * 
  */
  public void addArg(Arg arg) {
    arguments.put(arg.getName(), arg);
    argumentNames.add(arg.getName());
    positionalArgumentNames.add(arg.getName());

  }

  /**
  * Adds an optional argument with given name and default value. 
  * @param  argname, the String value that is the name of the optional argument being added
  * @param  defaultValue, the string value taht is the value of the optional argument being added.
  * 
  */
  public void addOptArg(String argname, String defaultValue) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, "");

  }

  /**
  * Adds an optional argument with given name, default value, and description. 
  * @param  argname, the String value that is the name of the optional argument being added
  * @param  defaultValue, the string value taht is the value of the optional argument being added.
  * @param  description, the String value that is the description of the optional argument to be added
  * 
  */
  public void addOptArg(String argname, String defaultValue, String description) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, description);

  }

  /**
  * Adds an optional argument with given name, default value, and data type. 
  * @param  argname, the String value that is the name of the optional argument being added
  * @param defaultValue, the string value taht is the value of the optional argument being added.
  *	@param dataType, the String value that is the data type of the optional argument to be added
  * 
  */
  public void addOptArg(String argname, String defaultValue, Arg.DataType dataType) {
    addOptArg(argname, defaultValue, dataType, "");

  }

  /**
  * Adds an optional argument with given name, default value, data type, and description. 
  * @param  argname, the String value that is the name of the optional argument being added
  * @param defaultValue, the string value taht is the value of the optional argument being added.
  *	@param dataType, the String value that is the data type of the optional argument to be added
  * @param description, the String value that is the description of the optional argument to be added
  * 
  */
  public void addOptArg(String argname, String defaultValue, Arg.DataType dataType, String description) {
    arguments.put(argname, new OptArg(argname, defaultValue, dataType, description));
  }

  public void addOptArg(OptArg arg) {
    arguments.put(arg.getName(), arg);
    argumentNames.add(arg.getName());

  }

  /**
  * Add a flag, which is a boolean optional argument.
  * @param  argname, the string value that is the name of the flag
  * 
  */
  public void addFlag(String argname) {
    addFlag(argname, "");
  }

  /**
  * Add a flag, which is a boolean optional argument with a name and description. 
  * @param  argname, the string value that is the name of the flag
  * @param  description, the String value that is the description of teh flag 
  * 
  */
  public void addFlag(String argname, String description) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN, description));
    flagNames.add(argname);
  }

  /**
  * Adds a flag to the list of flag names 
  * @param  flagName, the String value that is the name  of the flag 
  * 
  */
  public void addFlagToList(String flagName) {
    flagNames.add(flagName);
  }

  /**
  * Returns the argument object that is specified by the given argument name 
  * @param  argument, the String value that is the ame of the argument object you want to return 
  * @return the object argument associated with the argument name 
  */
  public Arg getArgument(String argument) {
    return arguments.get(argument);
  }

  /**
  * Returns the map of argument names and argument objects 
  * @return map of argument names and argument objects 
  */
  public Map<String, Arg> getAllArgs() {
    return this.arguments;
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

  public ArrayList<String> getPostionalArgNames() {
    return this.positionalArgumentNames;
  }

  /**
  * Returns the number of arguments that have been given.
  * If no arguments have been given then it should return 0.
  * @return the number of given arguments
  */
  public int getNumberArgs() {
    return arguments.size();
  }

  public String getParameterString(){ 
    String key_string = "";
    for (String argNameIterator : arguments.keySet()) {
      
        key_string += " " + argNameIterator;
      
    }
    return key_string;
  }

  /**
  * Sets the name of the program. 
  * @param  programName, the String value that is the name of the program 

  */
  public void setProgramName(String programName) {
     this.programName = programName;
  }
    /**
  * Returns the value for the name of the program. 
  * @return the String value that is the name of the program
  */
  public String getProgramName() {
    return programName;
  }


  /**
  * Sets the value for the description of the program. 
  * @param  programDescription, the String value that is the description of the program 
  * 
  */
  public void setProgramDescription(String programDescription) {
    this.programDescription = programDescription;
  }

  /**
  * Returns the value for the description of the program. 
  * @return the String description of the program 
  */
  public String getProgramDescription() {
    return programDescription;
  }

  public String getErrorUsage() {
    String errorMessage = "usage: java " + this.programName + getParameterString() + "\n" + this.programName;
    return errorMessage;
  }

  /**
  * Sets a short form name for the given argument 
  * @param argument, the String value that is the the argument to which you want to set a short form name 
  *	@param shortFormName, String value that is the short form of the argument name 
  * 
  */
  public void setArgShortFormName(String argument, String shortFormName) {
    arguments.get(argument).setShortFormName(shortFormName);
    if (shortToLong.get(shortFormName) != null || shortFormName.equals("h")) {
      throw new ShortFormNameException(this.getErrorUsage(), shortFormName);
    }
    shortToLong.put(shortFormName, argument);
  }

  /**
  * Sets restricted values for this argument
  * @param  argument, the String value for which you want to set restricted values 
  *	@param  restrictedValues, the String that contains the restricted values, separated by spaces
  * 
  */
  public void setArgRestricedValues(String argument, String restrictedValues) {
    arguments.get(argument).setRestrictedValues(restrictedValues);

  }

  /**
  *Makes an argument a required argument
  *@param  argument, the String value that is the argument that you want to make required
  */
  public void setArgAsRequired(String argument) {
  
      arguments.get(argument).makeArgRequired();
      requiredArgs.add(argument);
    
  }

  /**
  * Parses the input values
  * @param  args, a String array of argument values
  * 
  */
  public void parse(String[] args) {
    Queue<String> commandLineQueue = new ArrayDeque<>();
    for (int i = 0; i < args.length; i++) {
      commandLineQueue.add(args[i]);
    }

    int usedArguments = 0;
    while (!commandLineQueue.isEmpty()) {
      String curr = commandLineQueue.remove();
      String argumentName = "";
      assertHelpMessage(curr);

      if (isOptional(curr)) {
        if (isLongFormArg(curr)) {
          assertOptionalArgExists(removeHyphens(curr));
          argumentName = removeHyphens(curr);
        } else {
          String sname = removeHyphens(curr);
          if (isArgAFlag(sname)) {
            setFlag(sname);
            continue;
          } else if (sname.length() > 1) {
            argIsACollectionOfFlags(sname);
            continue;
          }
          assertShortNameExists(sname);
          argumentName = getArgNameFromShortName(sname);
          usedArguments++;
        }

        //dealing with optional arguments
        Arg a = arguments.get(argumentName);
        if (doesArgHaveBoolVal(a)) {
          a.setValue("true");
        }

        else {
          String next = commandLineQueue.remove();
          assertBadDataType(a, next);
          if (doesArgHaveRestrictedValues(a)) {
            assertIsValARestrictedVal(a, next);
          }
          ;

          removeArgIfRequired(argumentName);
          a.setValue(next);
        }
      }

      else {
        // Regular argument value
        assertToManyArguments(usedArguments, curr);
        argumentName = argumentNames.get(usedArguments);
        Arg a = arguments.get(argumentName);
        assertBadDataType(a, curr);
        if (doesArgHaveRestrictedValues(a)) {
          assertIsValARestrictedVal(a, curr);
        }
        ;

        a.setValue(curr);
        usedArguments++;

      }

    }

    assertTooFewArgs(usedArguments);
    assertRequiredArgs();
  }

  private void assertToManyArguments(int usedArguments, String possibleExtraArg) {
    if (usedArguments == argumentNames.size()) {
      throw new TooManyArguments(this, possibleExtraArg);
    }
  }

  private void assertHelpMessage(String value) {
    if ((value.equals("-h") || value.equals("--help"))) {
      throw new HelpException(this);
    }
    ;
  }

  private boolean isOptional(String s) {
    return s.startsWith("-");
  }

  private boolean isLongFormArg(String s) {
    return s.startsWith("--");
  }

  private void assertOptionalArgExists(String commandLineName) {
    if (arguments.get(commandLineName) == null) {
      throw new ArgDoesNotExistException(this.getErrorUsage() ,commandLineName);
    }
  }

  private String removeHyphens(String argName) {
    return argName.replace("-", "");
  }

  private boolean isArgAFlag(String flagName) {

    return arguments.get(flagName) != null;
  }

  private void setFlag(String flagName) {
    arguments.get(flagName).setValue("true");
  }

  private void argIsACollectionOfFlags(String flagNames) {
    for (int j = 0; j < flagNames.length(); j++) {
      String flagIterator = String.valueOf(flagNames.charAt(j));
      assertFlagExists(flagIterator);
      setFlag(flagIterator);

    }
  }

  private void assertFlagExists(String flagName) {
    if (isArgAFlag(flagName)) {
    } else {
      throw new FlagDoesNotExistException(this.getErrorUsage() ,flagName);
    }
  }

  private void assertShortNameExists(String shortName) {

    if (shortToLong.get(shortName) == null) {
      throw new ArgDoesNotExistException(this.getErrorUsage(), shortName);
    }

  }

  private String getArgNameFromShortName(String shortName) {
    return shortToLong.get(shortName);
  }

  private boolean doesArgHaveBoolVal(Arg a) {
    return a.getDataType() == Arg.DataType.BOOLEAN;
  }

  private void assertBadDataType(Arg a, String value) {
    Arg.DataType type = a.getDataType();
    switch (type) {
    case BOOLEAN:
      if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
        throw new BadDataTypeException(this, a, value);
      }
      
    case INT:
      try {
        Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new BadDataTypeException(this, a, value);
      }
    case FLOAT:
      try {
        Float.parseFloat(value);
      } catch (NumberFormatException e) {
        throw new BadDataTypeException(this, a, value);
      }
    default:
    }
  }

  private boolean doesArgHaveRestrictedValues(Arg argument) {
    return argument.getRestrictedValuesString().length() > 0;

  }

  private void assertIsValARestrictedVal(Arg argument, String argValue) {
    HashSet<String> argRestrictedValues = argument.getRestrictedValues();
    if (!argRestrictedValues.contains(argValue)) {
      throw new RestrictedValueException(this.getErrorUsage() ,argument, argValue);
    }
  }

  private void removeArgIfRequired(String argname) {
    if (requiredArgs.contains(argname)) {
      requiredArgs.remove(argname);
    }

  }

  private void assertTooFewArgs(int usedArguments) {
    if (usedArguments < argumentNames.size()) {
      throw new TooFewArguments(this, usedArguments, argumentNames);

    }

  };

  private void assertRequiredArgs() {
    if (requiredArgs.size() > 0) {
      throw new RequiredArgException(this.getErrorUsage(),requiredArgs);

    }
  };

}
