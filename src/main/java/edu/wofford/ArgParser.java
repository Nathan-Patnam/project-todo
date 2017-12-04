package edu.wofford;

import java.util.*;

/**
* <pre>
*  Provides a way to set argument information and parses command-line values for the given arguments.
*  One can specify arguments, create optional arguments and flags, set required arguments, and set restricted values 
*  
*	For example:
*  {@code
*     argCheck = ArgParser("Volume Calculator", "Calculates the volume of shapes.");
*  	  String[] cla = { "6", "7" , "rectangle"}; 
*	  argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT); 
*	  argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT); 
*	  argCheck.addOptArg("shape", "square", "the default shape is a square"); 
* 	  argCheck.setArgRestricedValues("height", "6 7 8"); 
*	  argCheck.parse(cla); 
*  }

* 	Now "height" is associated with a value of 6, width is associated with a value of 7, and the shape is now set as "rectangle" instead of "square."
*	value for height was included in its restricted values, so it was set accordingly; otherwise an error would have been thrown. 

* </pre>
*/
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
  * @param argname,  the name of the argument to be added
  *	@param dataType, the data type of the argument to be added
  * 
  */
  public void addArg(String argname, Arg.DataType dataType) {
    addArg(argname, "", dataType);
  }

  /**
  * Adds an argument with the given name, the given description, and the given data type. 
  * @param argname, the name of the argument to be added
  * @param description, the description of the argument to be added
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
  * @param  argname, the name of the optional argument being added
  * @param  defaultValue, the string value taht is the value of the optional argument being added.
  * 
  */
  public void addOptArg(String argname, String defaultValue) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, "");

  }

  /**
  * Adds an optional argument with given name, default value, and description. 
  * @param  argname, the name of the optional argument being added
  * @param  defaultValue, the string value taht is the value of the optional argument being added.
  * @param  description, the description of the optional argument to be added
  * 
  */
  public void addOptArg(String argname, String defaultValue, String description) {
    addOptArg(argname, defaultValue, Arg.DataType.STRING, description);

  }

  /**
  * Adds an optional argument with given name, default value, and data type. 
  * @param  argname, the name of the optional argument being added
  * @param defaultValue, the string value taht is the value of the optional argument being added.
  *	@param dataType, the data type of the optional argument to be added
  * 
  */
  public void addOptArg(String argname, String defaultValue, Arg.DataType dataType) {
    addOptArg(argname, defaultValue, dataType, "");

  }

  /**
  * Adds an optional argument with given name, default value, data type, and description. 
  * @param  argname, the name of the optional argument being added
  * @param defaultValue, the string value taht is the value of the optional argument being added.
  *	@param dataType, the data type of the optional argument to be added
  * @param description, the description of the optional argument to be added
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
  * @param  argname, the name of the flag
  * 
  */
  public void addFlag(String argname) {
    addFlag(argname, "");
  }

  /**
  * Add a flag, which is a boolean optional argument, with a name and description. 
  * @param  argname, the name of the flag
  * @param  description, the description of teh flag 
  * 
  */
  public void addFlag(String argname, String description) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN, description));
    flagNames.add(argname);
  }

  /**
  * Adds a flag to the list of flag names 
  * @param  flagName, the name  of the flag 
  * 
  */
  public void addFlagToList(String flagName) {
    flagNames.add(flagName);
  }

  /**
  * Returns the argument object that is specified by the given argument name 
  * @param  argument, the ame of the argument object you want to return 
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
  * Returns the value that the argument holds. If no value has been set for the argument then it will return null.
  * @param  argument, the name of the arugment you want the value of
  * @return the value associated with that argument
  */
  public String getArgValue(String argument) {
    return arguments.get(argument).getValue();
  }

  /**
  * Returns the description of the argument. If no description has been set for the argument then it will
  * return an empty string. 
  * @param  argument, the name of the arugment you want the description of
  * @return the description of that argument
  */
  public String getArgDescription(String argument) {
    return arguments.get(argument).getDescription();
  }

  /**
  * Returns the data type of the argument. If no data type has been set for the argument then it will
  * return an empty string. 
  * @param   argument  the name of the arugment you want the data type of
  * @return  the data type associated with that argument
  */
  public Arg.DataType getArgDataType(String argument) {
    return arguments.get(argument).getDataType();
  }

  /**
  * Returns the string form of the data type of the argument. If no data type has been set for the argument then it will
  * return an empty string. 
  * @param   argument, the name of the arugment you want the data type of
  * @return  the data type associated with that argument
  */
  public String getArgDataTypeString(String argument) {
    return arguments.get(argument).getDataType().toString();
  }

  /**
  * Returns list of positional arguments 
  * @return list of positional argumemts 
  */
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

  /**
  * Returns list of arguments that have been given 
  * @return list of given arguments 
  *
  */
  public String getParameterString(){ 
    String key_string = "";
    for (String argNameIterator : arguments.keySet()) {
      
        key_string += " " + argNameIterator;
      
    }
    return key_string;
  }

  /**
  * Sets the name of the program. 
  * @param  programName, the name of the program 

  */
  public void setProgramName(String programName) {
     this.programName = programName;
  }
    /**
  * Returns the name of the program. 
  * @return the name of the program
  */
  public String getProgramName() {
    return programName;
  }


  /**
  * Sets the description of the program. 
  * @param  programDescription, the description of the program 
  * 
  */
  public void setProgramDescription(String programDescription) {
    this.programDescription = programDescription;
  }

  /**
  * Returns the description of the program. 
  * @return description of the program 
  */
  public String getProgramDescription() {
    return programDescription;
  }

  /**
  * Gives error message, including program name and parameter list
  * @returns an error message 
  */
  public String getErrorUsage() {
    String errorMessage = "usage: java " + this.programName + getParameterString() + "\n" + this.programName;
    return errorMessage;
  }

  /**
  * Sets a short form name for argument, so that it can be referred to with short or long form name
  * @param argument, the argument for which you want to set a short form name 
  *	@param shortFormName, the short form of the argument name 
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
  * Give the argument restricted values, so that only those values can be assigned to it. 
  * @param  argument, argument to give restricted values to
  *	@param  restrictedValues, the restricted values
  * 
  */
  public void setArgRestricedValues(String argument, String restrictedValues) {
    arguments.get(argument).setRestrictedValues(restrictedValues);

  }

  /**
  * Makes argument required 
  * @param  argument, the argument that you want to make required
  */
  public void setArgAsRequired(String argument) {
  
      arguments.get(argument).makeArgRequired();
      requiredArgs.add(argument);
    
  }

  /**
  * Parses the input values
  * @param  args, argument values
  * @exception ArgDoesNotExistException    thrown if an optional argument appears in the String array of argument values, but has not been added to the argument parser
  * @exception BadDataTypeException        thrown if a argument is being set to a value that is not of its datatype
  * @exception FlagDoesNotExistException   thrown if a flag is in args, but has not been added to the argument parser
  * @exception HelpException               thrown if a -h or --help is in args
  * @exception RequiredArgException               thrown if a -h or --help is in args
  * @exception HelpException               thrown if a -h or --help is in args

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
      break;
    case INT:
      try {
        Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new BadDataTypeException(this, a, value);
      }
      break;
    case FLOAT:
      try {
        Float.parseFloat(value);
      } catch (NumberFormatException e) {
        throw new BadDataTypeException(this, a, value);
      }
      break;
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
