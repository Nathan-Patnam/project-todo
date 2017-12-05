  package edu.wofford;
  
  import java.util.*;
  
    /**
  * <pre> 
  * Thrown to indicate that the argument parser has encountered a help flag {@code -h} or {@code --help} 
  * For example, suppose the following code was executed
  * {@code
  *   argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");    
  *   String[] cla = { "6","-h", "7" , "2", "ellipsoid"}; 
  *    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT); 
  *    argCheck.addOptArg("type", "cube", "the type of the box to calculate the volume for"); 
  *    argCheck.parse(cla);
  * }
  *
  *  The HelpException would be thrown with the following error message:
  *  {@code    
  * "usage: java VolumeCalculator length width height type\nCalculate the volume of a box.
  *  positional arguments:
  *     length the length of the box (float)
  *     width the width of the box (float)
  *     height the height of the box (float)
  *  optional arguments:
  *     type the type of the box to calculate the volume for (string; default: cube)" 
  *  }
  *</pre>
  */
public class HelpException extends RuntimeException {
  private String message;


  public HelpException(ArgParser argChecker) {
    super();
    this.message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n"
        + argChecker.getProgramDescription();
    Map<String, Arg> arguments = argChecker.getAllArgs();
    ArrayList<String> positionalArgumentNames = argChecker.getPostionalArgNames();

    if (positionalArgumentNames.size() > 0) {
      message += "\npositional arguments:";
      for (String argNameIterator : arguments.keySet()) {
        if (positionalArgumentNames.contains(argNameIterator)) {
          Arg currentArgumentIterator = arguments.get(argNameIterator);
          this.message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
              + currentArgumentIterator.getDataType().toString() + ")";

        }
      }

    }
    if (positionalArgumentNames.size() < arguments.size()) {
      message += "\noptional arguments:";
      for (String argNameIterator : arguments.keySet()) {
        if (!positionalArgumentNames.contains(argNameIterator)) {
          Arg currentArgumentIterator = arguments.get(argNameIterator);
          this.message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
              + currentArgumentIterator.getDataType().toString() + "; default: " + String.valueOf(currentArgumentIterator.getValue());
              + ")";

        }
      }
    }
  }

   /**
  * Gets the help message of the program
  * @return  the help message of the program
  * 
  */
  @Override
  public String getMessage() {
    return this.message;
  }
}
