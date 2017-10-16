package edu.wofford;
import java.util.*;

public class CheckArguments {
    protected String[] arguments;
    //how to get positional arguments from demo
    //program name seperated from scanned input? impossible
    String demo_args = "length width height"

    public void main(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] string_array = input.split(" ");
        String program_name = "" + string_array[0] + string_array[1];
        try {
          if(string_array.length<3){
            throw new InvalidArgsException("usage:" + program_name + demo_args +"\n"
            + string_array[1]+"."+string_array[0]": " + "error: no arguments");
            }
          else if (string_array.length<4){
            throw new InvalidArgsException("usage:" + program_name + demo_args +"\n"
            + string_array[1]+"."+string_array[0]": " + "error: the following arguments are required: width height");
          }
          else if (string_array.length<5){
            throw new InvalidArgsException("usage:" + program_name + demo_args +"\n"
            + string_array[1]+"."+string_array[0]": " + "error: the following arguments are required: height");  

          }catch(InvalidArgsException ex){
            //stop program
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
