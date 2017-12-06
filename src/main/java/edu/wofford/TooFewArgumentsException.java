package edu.wofford;

import java.util.*;

/**
  *  
  * The TooFewArgumentsException is thrown to indicate that too few arguments have been provided on the command line.
  * For example, suppose the following code was executed:
  * <pre>
  *   argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");   
  *   String[] cla = { "7", "3"}; 
  *    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);  
  *    argCheck.parse(cla);
  * </pre>
  *
  *  The TooFewArgumentsException would be thrown with the following error message:
  *  <p>  
  * "usage: java VolumeCalculator length width height
  *  VolumeCalculator.java: error: the following arguments are required: height"
  *  </p>
  *
  */

public class TooFewArgumentsException extends RuntimeException {
  private String message;

  /**
  * Constructor for a TooFewArguments exception that is thrown when too few arguments are provided in the user 
  * input. 
  * @param argChecker the argparser currently being used to parse arguments
  * @param usedArguments the number of required arguments that been set by parse already
  * @param argumentNames a list of all required arguments required by the program
  * @param requiredArgs optional args that have been set by product owner as required
  */
  public TooFewArgumentsException(ArgParser argChecker, int usedArguments, ArrayList<String> argumentNames, HashSet<String> requiredArgs) {
    super();
    String missingArguments = "";
    for (int i = usedArguments; i < argumentNames.size(); i++) {
      missingArguments += " " + argumentNames.get(i);
    }
    if(requiredArgs.size()>0){
      for (String requireOptArg : requiredArgs) {
        missingArguments += " " + requireOptArg;
      }
    }

    this.message = argChecker.getErrorUsage() + ".java: error: the following arguments are required:"
        + missingArguments;
  }

  // Overrides Exception's getMessage()
  @Override
  public String getMessage() {
    return this.message;
  }
}