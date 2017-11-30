package edu.wofford;

class TooManyArguments extends RuntimeException {
  private String message;
  public TooManyArguments(ArgParser argChecker, String argument) {
    super();

    this.message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n" + argChecker.getProgramName()
    + ".java: error: unrecognized arguments: " + argument;
  }

  @Override
  public String getMessage(){
      return this.message;
  }
}
