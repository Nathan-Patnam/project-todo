package edu.wofford;

public class Flag{

    private Boolean isFlagPresent;
    private String flagName;
    private String shortFormName; 

Flag(String argname){
    this.isFlagPresent=false;
    this.flagName = ("-" + argname);
}

Flag(String argname, String shortFormName){
    this.flagName = ("-" + argname);
    this.shortFormName = ("-" + shortFormName);

}


public void flagIsPresent(){
    this.isFlagPresent=true;
}


public Boolean isFlagPresent(){
    return this.isFlagPresent;
}

}