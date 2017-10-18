package edu.wofford;

public class Feature01 {
    protected String [] arguments;

    public Feature01(){

    }


    public boolean testMissingArgument(String input){
        
        if(input.length()==3){
            return true;
        }
        else{
            return false;
        }
       
    }

    




}