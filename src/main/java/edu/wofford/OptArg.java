package edu.wofford;

public class OptArg extends Arg {

    OptArg(String name, String defaultValue) {
        this(name, defaultValue, Arg.DataType.STRING, "");
    }

    OptArg(String name, String defaultValue, String description) {
        this(name, defaultValue, Arg.DataType.STRING, description);
    }

    OptArg(String optionalArgumentName, String defaultValue, Arg.DataType dataType) {
        this(optionalArgumentName, defaultValue, dataType, "");
    }

    //flag constructor
    OptArg(String optionalArgumentName, boolean turnedOff, Arg.DataType dataType) {
        super("-" + optionalArgumentName, "", Arg.DataType.BOOLEAN);
        this.value = "false";
    }
    OptArg(String optionalArgumentName, boolean turnedOff, Arg.DataType dataType, String description) {
        super("-" + optionalArgumentName, description, Arg.DataType.BOOLEAN);
        this.value = "false";
    }

    OptArg(String optionalArgumentName, String defaultValue, Arg.DataType datatype, String description) {
        super("--" + optionalArgumentName, description, datatype);
        this.value = defaultValue;
        this.required=false;

    }


}
