package edu.wofford;

import java.util.*;

public class OptionalArgument extends Argument {
    private String optionalArgumentName;
    private String optionalArgumentValue;
    private String optionalDescription;
    private Argument.DataType optionalType;

    public OptionalArgument(String optionalArgumentName, String optionalArgumentDefaultValue, String description, Argument.DataType type) {
        this.optionalArgumentName = "--"+optionalArgumentName;
        this.optionalArgumentValue = optionalArgumentDefaultValue;
        this.optionalDescription = description;
        this.optionalType = type;
    }

    public void setOptionalArgumentValue(String optionalArgumentValue) {
        this.optionalArgumentValue = optionalArgumentValue;
    }

    public void setOptionalArgumentValues(ArrayList<String> multipleArgumentValue) {
        this.multipleArgumentValue = multipleArgumentValue;
    }

    public String getOptionalArgumentValue() {
        return this.optionalArgumentValue;
    }

    public ArrayList<String> getOptionalArgumentValues() {
        return this.multipleArgumentValue;
    }
}
