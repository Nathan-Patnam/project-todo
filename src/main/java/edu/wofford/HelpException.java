package edu.wofford;
import java.util.*;
public class HelpException extends RuntimeException {
  private String message;
  
  public HelpException(ArgParser argChecker) {
    super();
    this.message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n" + argChecker.getProgramDescription();
    Map<String, Arg> arguments = argChecker.getAllArgs();
    HashSet<String> positionalArgumentNames = argChecker.getPostionalArgNames();
   
    if(positionalArgumentNames.size()>0){
    message+="\npositional arguments:";
    for (String argNameIterator : arguments.keySet()) {
      if (!argNameIterator.equals("help") && !argNameIterator.equals("h") && positionalArgumentNames.contains(argNameIterator)) {
        Arg currentArgumentIterator = arguments.get(argNameIterator);
        this.message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
            + currentArgumentIterator.getDataType().toString() + ")";
      }

   }

    }
    if(positionalArgumentNames.size()< arguments.size()){
      message+="\noptional arguments:";
      for (String argNameIterator : arguments.keySet()) {
        if (!argNameIterator.equals("help") && !argNameIterator.equals("h") && !positionalArgumentNames.contains(argNameIterator)) {
          Arg currentArgumentIterator = arguments.get(argNameIterator);
          this.message += "\n   " + argNameIterator + " " + currentArgumentIterator.getDescription() + " ("
              + currentArgumentIterator.getDataType().toString() + ")" + " default value: " + currentArgumentIterator.getValue();
        }
  
     }


    }
  }

     // Overrides Exception's getMessage()
     @Override
     public String getMessage(){
         return this.message;
     }
    }

