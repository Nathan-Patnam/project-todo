package edu.wofford;
import java.util.*;

public class ArgumentParser {

    private String programName;
    //give this dictionary a better name
    private Map<String, String> dictionary;
    private List<String> argumentNames;
    private String programDescription;
    private Map<String, String> argDescriptions;
    private Map<String, String> argDataTypes;
    private Boolean help = false;
    private List<String> dataTypes;

    public ArgumentParser(String programName) {
      this.programName = programName;
      dictionary = new HashMap<>();
      argumentNames = new ArrayList<>();
      dataTypes = new ArrayList<>();
      dataTypes.add("string");
      dataTypes.add("float");
      dataTypes.add("int");
      dataTypes.add("boolean");
      argDescriptions = new HashMap<>();
      argDataTypes = new HashMap<>();
    }

    public ArgumentParser(String programName, String description){
      this.programName = programName;
      this.programDescription = description;
      dictionary = new HashMap<>();
      argumentNames = new ArrayList<>();
      argDescriptions = new HashMap<>();
      argDataTypes = new HashMap<>();
      dataTypes = new ArrayList<>();

      dataTypes.add("string");
      dataTypes.add("float");
      dataTypes.add("int");
      dataTypes.add("boolean");
    }

    public String getProgramName() {
      return programName;
    }

    public String getProgramDescription() {
      return programDescription;
    }

    public int getNumArguments() {
      return argumentNames.size();
    }

    public String getDataType(String argname){
        return argDataTypes.get(argname);
    }

    public void addArg(String argname) {
      argumentNames.add(argname);
    }

    public void addArg(String argname, String param2){
      argumentNames.add(argname);

      if(dataTypes.contains(param2)){
          argDataTypes.put(argname, param2);
      }
      else{
          argDescriptions.put(argname, param2);
      }
    }

    public void addArg(String argname, String description, String dataType){
        argumentNames.add(argname);
        argDescriptions.put(argname, description);
        argDataTypes.put(argname, dataType);
    }

    private String getParameterString(){
      String key_string = "";
        for (int i = 0; i < argumentNames.size(); i++){
          key_string += " " + argumentNames.get(i);
        }
        return key_string;
    }

    public String getHelpMessage(){
      String message="";
      if(this.help){
         message = "usage: java " + programName + getParameterString() + "\n" + programDescription + "\n" + "positional arguments:";
         for (int i = 0; i < argumentNames.size(); i++){
           message +="\n   "+ argumentNames.get(i) + " " + argDescriptions.get(argumentNames.get(i));
         }
       }
       return message;
    }

    public void parse(String[] args) {

      String key_string = "";
        for (int i = 0; i < argumentNames.size(); i++){
          key_string += " " + argumentNames.get(i);
        }


      for(int i = 0; i < args.length; i++){
        if(args[i].equals("-h")){
            this.help = true;
            String message = getHelpMessage();
            throw new HelpException(message);
        }
      }

      if(args.length<argumentNames.size()){
          String missingArguements="";
          for (int i = args.length; i < argumentNames.size(); i++){
          missingArguements+=" " + argumentNames.get(i);
        }
        String message = "usage: java " + programName + key_string + "\n"
        +programName + ".java: error: the following arguments are required:" + missingArguements;
        throw new TooFewArguments(message);
      }


      else if(args.length>argumentNames.size()){
        String tooManyArguments="";
        for (int i = argumentNames.size(); i < args.length; i++){
          tooManyArguments+=" "+args[i];
        }
        String message = "usage: java " + programName + key_string + "\n"
        +programName + ".java: error: unrecognized arguments:" + tooManyArguments;

        throw new TooManyArguments(message);
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
}
