package edu.wofford;

class TooFewArguments extends RuntimeException {
  public TooFewArguments(String message){
    super(message);
  }
}
