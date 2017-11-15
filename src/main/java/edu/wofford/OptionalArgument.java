package edu.wofford;

public class OptionalArgument extends Argument {


    OptionalArgument(String name, String defaultValue) {
        this(name, defaultValue, Argument.DataType.STRING, "");
    }

    OptionalArgument(String name, String defaultValue, String description) {
        this(name, defaultValue, Argument.DataType.STRING, description);
    }

    OptionalArgument(String optionalArgumentName, String defaultValue, Argument.DataType dataType) {
        this(optionalArgumentName, defaultValue, dataType, "");
    }

    OptionalArgument(String optionalArgumentName, String defaultValue, Argument.DataType datatype, String description) {
        super("--" + optionalArgumentName, description, datatype);
        this.value = defaultValue;

    }


 

}
