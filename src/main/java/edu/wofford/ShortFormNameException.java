package edu.wofford;
import java.util.*;
class ShortFormNameException extends RuntimeException {
  private String message;
  
  public ShortFormNameException(String shortFormName) {
    super();
    this.message="The short form name " + shortFormName + " is already in use";
  }

     // Overrides Exception's getMessage()
     @Override
     public String getMessage(){
         return this.message;
     }
    }