package edu.wofford;
import java.util.*;

public class CheckArguments {
    protected String[] arguments;

    public void main(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] string_array = input.split(" ");
        if(string_array.length!=5){
            System.out.println("usage: java VolumeCalculator length width height.  VolumeCalculator.java: error: the following arguments are required: height");
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
