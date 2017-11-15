package edu.wofford;

class TooManyArguments extends RuntimeException {
  public TooManyArguments(String message) {
    super(message);
  }
}
