package edu.wofford;
import java.util.*;

public class ArgumentParser {

    private String filename;
    private String[] keys;
    private String[] values;
    private Map<String, String> dictionary;

    public void main(){
      // how to get file name???
        dictionary = new HashMap<String, String>();
    }

// method product owner uses.  Must pass in filename, a string of arguments seperated by spaces, and a string of values seprated by spaces
// example argumentparser.setArgs("VolumeCalculator","length width height","7 5 2");

//alternatively we could make product owner pass in a string array for the keys and values, and a string for filename.


     void setArgs(String file, String keys_string, String values_string){
      this.filename = file;
      this.keys = keys_string.split(" ");
      this.values = values_string.split(" ");

      if(ArrayCheck()){
          writeDictionary();
      }
    }


//checks that arrays exist and

    private boolean ArrayCheck(){
      String key_string = "";
      for (int i = 0; i < keys.length; i++){
        key_string += " " + keys[i];
      }
      if (keys.length == 0){
        String message = "No arguments have been supplied.";
        throw new InvalidArgsException(message);
      }
      else if (values.length == 0){
        String message = "No argument values have been supplied.";
        throw new InvalidArgsException(message);
      }
      else if (keys.length < values.length){
        for (int i = keys.length; i < values.length; i++){
          String message = "usage: java " + filename + key_string + "\n"
          +filename + ".java: error: unrecognized arguments" + values[i] + "\n";
          throw new InvalidArgsException(message);
        }
      }
      else if (keys.length > values.length){
        for (int i = values.length; i < keys.length; i++){
          String message = "usage: java " + filename + key_string + "\n"
          +filename + ".java: error: the following arguments are required:" + keys[i] + "\n";
          throw new InvalidArgsException(message);
        }
      }

      return true;

    }

  //builds the dictionary

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
}
