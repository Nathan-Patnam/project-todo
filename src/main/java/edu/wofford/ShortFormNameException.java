package edu.wofford;

/**
  * <pre> 
  * Thrown to indicate that two arguments have been given the same short form name
  * {@code 
  *   String[] cla = { "3","4" };
  *    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT); 
  *    argCheck.setArgShortFormName("length", "l");
  *    argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT); 
  *    argCheck.setArgShortFormName("width", "l");
  *    argCheck.parse(cla);
  * }
  *
  *  The ShortFormNameException would be thrown with the following error message:
  *  {@code    
  *"usage: java VolumeCalculator length width
  * VolumeCalculator.java: error: the short form name d is already in use"
  *  }
  *</pre>
  */

public class ShortFormNameException extends RuntimeException {
  private String message;

  public ShortFormNameException(String usageMessage,String shortFormName) {
    super();
    this.message = usageMessage + ".java: error: the short form name " + shortFormName + " is already in use";
  }

  // Overrides Exception's getMessage()
  @Override
  public String getMessage() {
    return this.message;
  }
}