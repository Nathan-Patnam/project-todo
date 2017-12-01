package edu.wofford;

public class RestrictedValueException extends RuntimeException {
    private String message;

    public RestrictedValueException(Arg argument, String value) {
        super();

        this.message = value + " is not an allowed value for " + argument.getName();

    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
