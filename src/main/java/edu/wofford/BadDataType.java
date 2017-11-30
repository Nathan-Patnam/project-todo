package edu.wofford;

public class BadDataType extends RuntimeException {
    private String message;
    public BadDataType(ArgParser argChecker, Arg argument, String value) {
        super();
    
        this.message = "";
        message = "usage: java " + argChecker.getProgramName() + argChecker.getParameterString() + "\n" + argChecker.getProgramName() + ".java: error: "
            + "argument " + argument.getName() + ":" + " invalid " + argument.getDataType().toString() + " value: " + value;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
