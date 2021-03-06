package edu.wofford;


/**
  *  
  * The RestrictedValueException is thrown when an argument has been given restricted values and is being assigned a value by the
  * argument parser that is not in the restricted value set.
  * For example, suppose the following code was executed
  * <pre> 
  *   argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *   String[] cla = { "7" };
  *    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT); 
  *    argCheck.setArgRestricedValues("length", "5 8 9");
  *    argCheck.parse(cla);
  * </pre>
  * 
  *  The RestrictedValueException would be thrown with the following error message:
  * <p>
  * "usage: java VolumeCalculator length
  * VolumeCalculator.java: error: 7 is not an accepted value for length"
  *  </p>
  *
  */

public class RestrictedValueException extends RuntimeException {
    private String message;

    public RestrictedValueException(String usageMessage,Arg argument, String value) {
        super();

        this.message = usageMessage + ".java: error: " + value + " is not an accepted value for " + argument.getName();

    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
