package edu.wofford;
import java.util.*;

public class ArgumentParser {

    private String programName;
    //give this dictionary a beter name
    private Map<String, String> dictionary;
    private List<String> argumentNames;
    private String programDescription;
    private Map<String, String> argDescriptions;
    private Boolean help = false;

    public ArgumentParser(String programName) {
      this.programName = programName;
      dictionary = new HashMap<>();
      argumentNames = new ArrayList<>();
    }

    public ArgumentParser(String programName, String description){
      this.programName = programName;
      this.programDescription = description;
      dictionary = new HashMap<>();
      argumentNames = new ArrayList<>();
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

    public void addArg(String argname) {
      argumentNames.add(argname);
    }

    public void AddArg(String argname, String description){
      argumentNames.add(argname);
      argDescriptions.put(argname, description);
    }

    public void parse(String[] args) {

      String key_string = "";
        for (int i = 0; i < argumentNames.size(); i++){
          key_string += " " + argumentNames.get(i);
        }


      for(int i = 0; i < args.length; i++){
        if(args[i] == "-h"){
            this.help = true;
        }
      }

      if(help){
        String message = "usage: java " + programName + key_string + "\n" + programDescription + "\n" + "positional arguments:" + "\n";
         for (int i = 0; i < argumentNames.size(); i++){
           message += "   "+ argumentNames.get(i) + " " + argDescriptions.get(argumentNames.get(i)) + "\n";
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
