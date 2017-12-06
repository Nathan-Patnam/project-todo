package edu.wofford;

/**
  *  
  * A BadDataTypeException is thrown to indicate that a argument has been set a value that is not of its own data type.
  * For example, suppose the following code was executed:
  * <pre>
  *  argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");   
  *  String[] cla = { "three", "3.1", "3.2" };
  *  argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
  *  argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
  *  argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);
  *  argCheck.parse(cla);
  * </pre>
  *
  *  The BadDataTypeException would be thrown with the following error message:
    <p>
  *  "usage: java VolumeCalculator length width height
  *   VolumeCalculator.java: error: argument length: invalid float value: three"
  </p>
  *  
  *
  */


public class BadDataTypeException extends RuntimeException {
    private String message;
    public BadDataTypeException(ArgParser argChecker, Arg argument, String value) {
        super();
    
        this.message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n" + argChecker.getProgramName() + ".java: error: "
            + "argument " + argument.getName() + ":" + " invalid " + argument.getDataType().toString() + " value: " + value;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
