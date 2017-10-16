package edu.wofford;
import java.util.*;

public class CheckArguments {
    protected String[] arguments;
    //how to get positional arguments from demo
    //program name seperated from scanned input? impossible
    private String demo_args = "length width height";
    private Map<String, String> dictionary;

    public void main(){
        dictionary = new HashMap<String, String>();
    }

//checks that arrays exist and

    private boolean ArrayCheck(String filename, String[] keys, String[] values){
      for (int i = 0; i < keys.length(); i++){
        String key_string =+ " " + keys[i];
      }
      if (keys.length == 0){
        String message = "No arguments have been supplied.";
        throw new InvalidArgsException(message);
        return false;
      }
      if (values.length == 0){
        String message = "No argument values have been supplied.";
        throw new InvalidArgsException(message);
        return false;
      }
      else if (keys.length() < values.length()){
        for (int i = keys.length(); i < values.length(); i++){
          String message = "usage: java " + filename + key_string + "\n"
          +filename + ".java: error: unrecognized arguments" + values[i];
          throw new InvalidArgsException(message);
          return false;
        }
      }
      else if (keys.length() > values.length()){
        for (int i = values.length(); i < keys.length(); i++){
          String message = "usage: java " + filename + key_string + "\n"
          +filename + ".java: error: the following arguments are required:" + keys[i];
          throw new InvalidArgsException(message);
          return false;
        }
      }
    }

    //builds the dictionary

    private void writeDictionary(){
      for (int i = 0; i < key.length; i++){

          String key = "";
          if (key.length() > i && keys[i] != null){
          key = keys[i];
          }

          String value = "";
          if (values.length() > i && values[i] != null){
          value = values[i];
          }

          dictionary.put(key,value);
    }


    public void setArgs(filename, String[] keys, String[] values){
      if(ArrayCheck(filename, keys, values)){
          writeDictionary(keys, values);
      }
    }

    public int getLength(String[] args){
      return Integer.valueOf(args[2]);
    }

    public int getWidth(String[] args){
      return Integer.valueOf(args[3]);
    }

    public int getHeight(String[] args){
      return Integer.valueOf(args[4]);
    }
}
