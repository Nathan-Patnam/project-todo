package edu.wofford;

import java.util.*;

/**
  * 
  * The RequiredArgException is thrown when an optional argument has been set as required, but the user has forgot to include it
  * in the command line arguments.
  * For example, suppose the following code was executed
  * <pre>
  *  argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");   
  *  String[] cla = {"7" };
  *  argCheck.addArg("length",Arg.DataType.FLOAT);
  *  argCheck.addOptArg("type", "ellipsoid", Arg.DataType.STRING, "shape you want to calculate the volume of");
  *  argCheck.setArgAsRequired("type");
  *  argCheck.parse(cla);
  * </pre>
  *
  *  The RequiredArgException would be thrown with the following error message:
  * <p>
  *"usage: java VolumeCalculator length type
  * VolumeCalculator.java: error: the argument(s) type are required"
  * </p>
  *
  */

public class RequiredArgException extends RuntimeException {
    private String message;

    public RequiredArgException(String usageMessage, HashSet<String> requiredArgs) {
        super();
        String requiredArgString = "";
        for (String requiredArgsIterator : requiredArgs) {
            requiredArgString += requiredArgsIterator + " ";
        }
        this.message = usageMessage + ".java: error: the argument(s) " + requiredArgString + "are required";
    }

    // Overrides Exception's getMessage()
    @Override
    public String getMessage() {
        return this.message;
    }
}