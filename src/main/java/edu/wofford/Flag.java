package edu.wofford;

public class Flag{

    private Boolean isFlagPresent;

Flag(String argname){
    this.isFlagPresent=false;
}


public void flagIsPresent(){
    this.isFlagPresent=true;
}


public Boolean isFlagPresent(){
    return this.isFlagPresent;
}

}