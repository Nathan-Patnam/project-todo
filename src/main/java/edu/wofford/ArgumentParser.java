package edu.wofford;
import java.util.*;

public class ArgumentParser {

    private String programName;
    private Map<String, String> dictionary;
    private List<String> argumentNames;

    public ArgumentParser(String programName) {
      this.programName = programName;
      dictionary = new HashMap<>();
      argumentNames = new ArrayList<>();
    }

    public String getProgramName() {
      return programName;
    }

    public int getNumArguments() {
      return argumentNames.size();
    }

    public void addArg(String argname) {
      argumentNames.add(argname);
    }

    public void parse(String[] args) {

      String key_string = "";
        for (int i = 0; i < argumentNames.size(); i++){
          key_string += " " + argumentNames.get(i);
        }


        if(args.length<argumentNames.size()){
          String missingArguements="";
        for (int i = args.length; i < argumentNames.size(); i++){
          missingArguements+=argumentNames.get(i)+" ";
        }
        String message = "usage: java " + programName + key_string + "\n"
        +programName + ".java: error: unrecognized arguments" + missingArguements + "\n";
        throw new InvalidArgsException(message);
      }


      else if(args.length>argumentNames.size()){
        String tooManyArguments="";
        for (int i = argumentNames.size(); i < args.length; i++){
          tooManyArguments+=args[i]+" ";
        }
        String message = "usage: java " + programName + key_string + "\n"
        +programName + ".java: error: unrecognized arguments" + tooManyArguments + "\n";

        throw new InvalidArgsException(message);

      }

      else{
      for(int i = 0; i < argumentNames.size(); i++) {
        dictionary.put(argumentNames.get(i), args[i]);
      }
      }
    }

    public String getArgValue(String argname) {
      return dictionary.get(argname);
    }

    /*
    // sets args using string array
    public void setArgs(String[] args){
      try{
        if(keys.length == 0){
          String message = "You must add arguments before using setArgs";
          throw new InvalidArgsException(message);
        }
      }catch(InvalidArgsException e){
        e.printStackTrace();
      }
      this.values = args;

      if(ArrayCheck()){
          writeDictionary();
      }
    }


    //add arguments one at a time
    public void addArgs(String key){
      // if(keys.length == 0){
        String[] temp = {key};
        this.keys = temp;
      // }
      // else{
      //   String[] oldKeysArray = this.keys;
      //   String[] newKeysArray = new String[oldKeysArray.length+1];
      //   System.arraycopy(arr1, 0, arr, 0, arr1.length)
      // }

    }

    //returns value of argument key selected
    public String getArgs(String arg){
      return dictionary.get(arg);
    }

      */
    //
    // private boolean ArrayCheck(){
    //
    //   String key_string = "";
    //   for (int i = 0; i < keys.length; i++){
    //     key_string += " " + keys[i];
    //   }
    //   if (keys.length == 0){
    //     String message = "No arguments have been supplied.";
    //     throw new InvalidArgsException(message);
    //   }
    //   else if (values.length == 0){
    //     String message = "No argument values have been supplied.";
    //     throw new InvalidArgsException(message);
    //   }
    //   else if (keys.length < values.length){
    //     for (int i = keys.length; i < values.length; i++){
    //       String message = "usage: java " + filename + key_string + "\n"
    //       +filename + ".java: error: unrecognized arguments" + values[i] + "\n";
    //       throw new InvalidArgsException(message);
    //     }
    //   }
    //   else if (keys.length > values.length){
    //     for (int i = values.length; i < keys.length; i++){
    //       String message = "usage: java " + filename + key_string + "\n"
    //       +filename + ".java: error: the following arguments are required:" + keys[i] + "\n";
    //       throw new InvalidArgsException(message);
    //     }
    //   }
    //
    //
    //
    // }
    //   return true;
    //
    // }

/*

    private void writeDictionary(){
      for (int i = 0; i < keys.length; i++){
        String key = "";
        if (keys.length > i && keys[i] != null){
          key = keys[i];
        }

        String value = "";
        if (values.length > i && values[i] != null){
          value = values[i];
        }
        dictionary.put(key,value);
      }
   }
   */
}
