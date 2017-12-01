package edu.wofford;

class TooFewArguments extends RuntimeException {
  /**
  * Constructor for a TooFewArguments exception that is thrown when too few arguments are provided in the user 
  * input. The message should include helpful usage information about the missing arguments.
  * @param  message, a String value that is the message that should contain usage information
  * @return a TooFewArguments exception 
  */
  public TooFewArguments(String message) {
    super(message);
  }
}
