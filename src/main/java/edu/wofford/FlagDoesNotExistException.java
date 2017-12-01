package edu.wofford;

public class FlagDoesNotExistException extends RuntimeException {
    private String message;
    public FlagDoesNotExistException(String flagName) {
        super();
    
        this.message = "flag " + flagName + " does not exist";
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
