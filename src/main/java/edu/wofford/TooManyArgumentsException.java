package edu.wofford;

/**
  * <pre> 
  * The TooManyArgumentsException is thrown to indicate that too many arguments have been provided on the command line.
  * For example, suppose the following code was executed:
  * {@code 
  *   argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");   
  *   String[] cla = { "7", "5", "2", "43" };
  *    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);  
  *    argCheck.parse(cla);
  * }
  *
  *  The TooManyArgumentsException would be thrown with the following error message:
  *  <p>  
  *"usage: java VolumeCalculator length width height
  * VolumeCalculator.java: error: unrecognized arguments: 43"
  *  </p>
  *</pre>
  */
public class TooManyArgumentsException extends RuntimeException {
  private String message;
 /**
  * Constructor for a TooManyArguments exception that is thrown when too many arguments are provided in the user 
  * input. 
  * @param argChecker the argparser currently being used to parse arguments
  * @param argument the first extra argument that has been encountered by the argument parser
  */
  public TooManyArgumentsException(ArgParser argChecker, String argument) {
    super();

    this.message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n"
        + argChecker.getProgramName() + ".java: error: unrecognized arguments: " + argument;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
