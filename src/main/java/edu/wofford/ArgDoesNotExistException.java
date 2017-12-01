package edu.wofford;

public class ArgDoesNotExistException extends RuntimeException {
    private String message;
    public ArgDoesNotExistException(String argumentName) {
        super();
    
        this.message = "";
        message = "argument " + argumentName + " does not exist";
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
