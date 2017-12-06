package edu.wofford;

/**
  *  
  * A FlagDoesNotExistException is thrown when a flag has been encountered by the argument parser, but has not been added by the user.
  * For example, suppose the following code was executed:
  * <pre> 
  * argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");   
  * String[] cla = { "-lw" };
  *  argCheck.addFlag("l");
  *  argCheck.addFlag("h");
  *  argCheck.parse(cla);
  * }
  *
  *  The FlagDoesNotExistException would be thrown with the following error message:
  *  </pre>
  *  <p>
  *  "usage: java VolumeCalculator l h
  *   VolumeCalculator.java: error: flag w does not exist"
  *  </p>
  *
  */

public class FlagDoesNotExistException extends RuntimeException {
    private String message;

    
    public FlagDoesNotExistException(String usageMessage,String flagName) {
        super();
    
        this.message = usageMessage + ".java: error: flag " + flagName + " does not exist";
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
