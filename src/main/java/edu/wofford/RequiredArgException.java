package edu.wofford;

import java.util.*;

/**
  * <pre> 
  * Thrown when an optional argument has been set as required, but the user has forgot to include
  * in the command line arguments
  * {@code 
  *  String[] cla = {"7" };
  *  argCheck.addArg("length",Arg.DataType.FLOAT);
  *  argCheck.addOptArg("type", "ellipsoid", Arg.DataType.STRING, "shape you want to calculate the volume of");
  *  argCheck.setArgAsRequired("type");
  * }
  *
  *  The RequiredArgException would be thrown with the following error message:
  *  {@code    
  *"usage: java VolumeCalculator length type
  * VolumeCalculator.java: error: the argument(s) type are required"
  *  }
  *</pre>
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