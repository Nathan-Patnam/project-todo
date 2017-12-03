package edu.wofford;

import java.util.*;
  /**
  * Constructor for a TooFewArguments exception that is thrown when too few arguments are provided in the user 
  * input. The message should include helpful usage information about the missing arguments.
  * a TooFewArguments exception with the help message 
  */
public class TooFewArguments extends RuntimeException {
  private String message;

  public TooFewArguments(ArgParser argChecker, int usedArguments, ArrayList<String> argumentNames) {
    super();
    String missingArguments = "";
    for (int i = usedArguments; i < argumentNames.size(); i++) {
      missingArguments += " " + argumentNames.get(i);
    }
    this.message = argChecker.getErrorUsage() + ".java: error: the following arguments are required:"
        + missingArguments;
  }

  // Overrides Exception's getMessage()
  @Override
  public String getMessage() {
    return this.message;
  }
}