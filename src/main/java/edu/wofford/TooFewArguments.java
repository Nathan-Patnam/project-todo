package edu.wofford;
import java.util.*;
class TooFewArguments extends RuntimeException {
  private String message;
  
  public TooFewArguments(ArgParser argChecker, int usedArguments, ArrayList<String> argumentNames ) {
    super();
    String missingArguments = "";
    for (int i = usedArguments; i < argumentNames.size(); i++) {
      missingArguments += " " + argumentNames.get(i);
    }
    this.message = argChecker.getErrorUsage()
        + ".java: error: the following arguments are required:" + missingArguments;
  }

     // Overrides Exception's getMessage()
     @Override
     public String getMessage(){
         return this.message;
     }
    }