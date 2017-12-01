package edu.wofford;
import java.util.*;
public class HelpException extends RuntimeException {
  private String message;
  
  public HelpException(ArgParser argChecker) {
    super();
    this.message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n" + argChecker.getProgramDescription() + "\n";
    Map<String, Arg> arguments = argChecker.getAllArgs();
    message+="positional arguments:";
    for (String argNameIterator : arguments.keySet()) {
      if (!argNameIterator.equals("help") && !argNameIterator.equals("h")) {
        Arg currentArgumentIterator = arguments.get(argNameIterator);
        this.message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
            + currentArgumentIterator.getDataType().toString() + ")";
      }
    }
  }

     // Overrides Exception's getMessage()
     @Override
     public String getMessage(){
         return this.message;
     }
    }

