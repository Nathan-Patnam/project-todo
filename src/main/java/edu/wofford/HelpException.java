package edu.wofford;
import java.util.*;
public class HelpException extends RuntimeException {
  
  public HelpException(ArgParser argChecker) {
    super(getHelpMessage(argChecker));
  }

  private static String getHelpMessage(ArgParser argChecker){

    String message = "";
    message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n" + argChecker.getProgramDescription() + "\n";
    Map<String, Arg> arguments = argChecker.getAllArgs();
    message+="positional arguments:";
    for (String argNameIterator : arguments.keySet()) {
      if (!argNameIterator.equals("help") && !argNameIterator.equals("h")) {
        Arg currentArgumentIterator = arguments.get(argNameIterator);
        message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
            + currentArgumentIterator.getDataType().toString() + ")";
      }
    }
    return message;
  }
}
