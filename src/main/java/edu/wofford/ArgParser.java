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
    positionalArgumentNames = new ArrayList<>();

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
    positionalArgumentNames.add(argname);
  }

  public void addArg(Arg arg) {
    arguments.put(arg.getName(), arg);
    argumentNames.add(arg.getName());
    positionalArgumentNames.add(arg.getName());

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
    addFlag(argname, "");
  }

  public void addFlag(String argname, String description) {
    arguments.put(argname, new OptArg(argname, false, Arg.DataType.BOOLEAN, description));
    flagNames.add(argname);
  }

  public void addFlagToList(String flagName) {
    flagNames.add(flagName);
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

  public ArrayList<String> getPostionalArgNames() {
    return this.positionalArgumentNames;
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
          argumentName =removeHyphens(curr);
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
          argumentName=getArgNameFromShortName(sname);
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
          if(doesArgHaveRestrictedValues(a)){
            assertIsValARestrictedVal(a, next);
          };

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
        if(doesArgHaveRestrictedValues(a)){
          assertIsValARestrictedVal(a, curr);
        };

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
      throw new ArgDoesNotExistException(commandLineName);
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

  private void assertFlagExists(String flagName){
    if (isArgAFlag(flagName)) {
    } else {
      throw new FlagDoesNotExistException(flagName);
    }

  }

  private void assertShortNameExists(String shortName) {

    if (shortToLong.get(shortName) == null) {
      throw new ArgDoesNotExistException(shortName);
    }

  }

  private String getArgNameFromShortName(String shortName){
    return shortToLong.get(shortName);
  }

  private boolean doesArgHaveBoolVal(Arg a) {
    return a.getDataType() == Arg.DataType.BOOLEAN;
  }

  private void assertBadDataType(Arg a, String value) {
    Arg.DataType type = a.getDataType();
    switch (type) {
    case BOOLEAN:
      if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false")) {
        throw new BadDataTypeException(this, a, value);
      }
      ;
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
    return(argument.getRestrictedValuesString() != null && 
    argument.getRestrictedValuesString().length() > 0); 

  }

  private void assertIsValARestrictedVal(Arg argument, String argValue){
    HashSet<String> argRestrictedValues = argument.getRestrictedValues();
    if (!argRestrictedValues.contains(argValue)){
      throw new RestrictedValueException(argument, argValue);
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
      throw new RequiredArgException(requiredArgs);

    }
  };

}
