package edu.wofford;

/**
  * 
  * The ShortFormNameException is thrown to indicate that two arguments have been given the same short form name.
  * For example, suppose the following code was executed
  * <pre>  
  *   argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");   
  *   String[] cla = { "3","4" };
  *    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box."); 
  *    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT); 
  *    argCheck.setArgShortFormName("length", "l");
  *    argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT); 
  *    argCheck.setArgShortFormName("width", "l");
  *    argCheck.parse(cla);
  * </pre>
  *
  *  The ShortFormNameException would be thrown with the following error message:
  * <p> 
  *"usage: java VolumeCalculator length width
  * VolumeCalculator.java: error: the short form name d is already in use"
  * </p>
  *
  */

public class ShortFormNameException extends RuntimeException {
  private String message;


    /**
  * Constructor for a ShortFormNameException exception that is thrown when two arguments have been given the same short form name
  * input. 
  * @param usageMessage the boiler plate first portion of the error message
  * @param shortFormName the short form name that has already been assigned to a argument
  */
  public ShortFormNameException(String usageMessage,String shortFormName) {
    super();
    this.message = usageMessage + ".java: error: the short form name " + shortFormName + " is already in use";
  }

   /**
  Retrieves Short Form Name error exception message
  @return Short Form Name error exception message
  */
  @Override
  public String getMessage() {
    return this.message;
  }
}