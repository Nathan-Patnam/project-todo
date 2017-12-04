package edu.wofford;

/**
  * <pre> 
  * Thrown to indicate that the argument parser has encountered a argument that doesn't exist 
  * For example, suppose the following code was executed
  * {@code 
  *   argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");   
  *   String[] cla = { "7", "--myarg","myval","3","2" }; 
  *    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT); 
  *    argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);  
  *    argCheck.parse(cla);
  * }
  *
  *  The ArgDoesNotExistException would be thrown with the following error message:
  *  {@code    
  * "usage: java VolumeCalculator length width height
  *  VolumeCalculator.java: error: argument myarg does not exist";
  *  }
  *</pre>
  */
public class ArgDoesNotExistException extends RuntimeException {

    private String message;
    public ArgDoesNotExistException(String usageMessage, String argumentName) {
        super();
    
        this.message = "";
        this.message = usageMessage + ".java: error: argument " + argumentName + " does not exist";
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
