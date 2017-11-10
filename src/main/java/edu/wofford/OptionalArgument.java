
package edu.wofford;

import java.util.*;

public class OptionalArgument {
    private String optionalArgumentName;
    private String optionalArgumentValue;
    private ArrayList<String> multipleArgumentValue = new ArrayList<String>();

    public OptionalArgument(String optionalArgumentName, String optionalArgumentDefaultValue) {
        this.optionalArgumentName = optionalArgumentName;
        this.optionalArgumentValue = optionalArgumentDefaultValue;
    }

    public OptionalArgument(String optionalArgumentName, ArrayList<String> multipleArgumentValue) {
        this.optionalArgumentName = optionalArgumentName;
        this.multipleArgumentValue = multipleArgumentValue;
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