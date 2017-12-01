package edu.wofford;
import java.util.*;

public class RequiredArgException extends RuntimeException {
  private String message;
  
  public RequiredArgException (HashSet<String> requiredArgs) {
    super();
    String requiredArgString = "";
    for (String requiredArgsIterator : requiredArgs) {
        requiredArgString += requiredArgsIterator + " ";
    }
    this.message="The argument(s) " + requiredArgString + "are required";
  }

     // Overrides Exception's getMessage()
     @Override
     public String getMessage(){
         return this.message;
     }
    }