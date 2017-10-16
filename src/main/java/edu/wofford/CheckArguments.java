package edu.wofford;
import java.util.*;

public class CheckArguments {
    protected String[] arguments;
    //how to get positional arguments from demo
    //program name seperated from scanned input? impossible
    String demo_args = "length width height";


    //dictionry





    public void main(){

        //dictionary




        Scanner scanner = new Scanner(System.in);

        String input_arguments = scanner.nextLine();
        String[] arguments_array = input_arguments.split(" ");

        String program_call = scanner.nextLine();
        String[] call_array = input_arguments.split(" ");

        if (arguments_array.length() < 3) {
          throw new InvalidArgsException();
        }

        else if (arguments_array.length()  ) {
          throw new InvalidArgsException();
        }





        String[] string_array = input.split(" ");

        String program_name = "" + string_array[0] + string_array[1];

        Strng no_args = "usage:" + program_name + demo_args +"\n"
        + string_array[1]+"."+string_array[0]": " + "error: no arguments";

        String 4_args = "usage:" + program_name + demo_args +"\n"
        + string_array[1]+"."+string_array[0]": " + "error: the following arguments are required: width height";

        String 5_args = "usage:" + program_name + demo_args +"\n"
        + string_array[1]+"."+string_array[0]": " + "error: the following arguments are required: height";

        // if (string_array.length > 5){
        //   for(int i = 5; i < string_array.length i++){
        //
        //   }
        //
        // }


        String 6_args = "" + input + "\n" +
        "usage:" + program_name + demo_args +"\n"
        + string_array[1]+"."+string_array[0]": " + "unrecognized arguments:" string_array[5];




        try {
          if(string_array.length<3){
            throw new InvalidArgsException();
            }
          else if (string_array.length<4){
            throw new InvalidArgsException(
          }
          else if (string_array.length<5){
            throw new InvalidArgsException

          }catch(InvalidArgsException ex){
            //stop program
          }
    }


    public void SetArgs(String[] keys, String[] values){
      
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
