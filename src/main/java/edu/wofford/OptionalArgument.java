package edu.wofford;

public class OptionalArgument extends Argument {

    private String optionalArgumentValue;

    OptionalArgument(String optionalArgumentName, String defaultValue){
        super("--"+optionalArgumentName);
        this.optionalArgumentValue=defaultValue;
    }
    OptionalArgument(String optionalArgumentName, String defaultValue,  String description){
        super("--"+optionalArgumentName, description);
        this.optionalArgumentValue=defaultValue;
    }

    OptionalArgument(String optionalArgumentName, String defaultValue, Argument.DataType dataType){
        super("--"+optionalArgumentName, dataType);
        this.optionalArgumentValue=defaultValue;
    }


    OptionalArgument(String optionalArgumentName, String defaultValue, Argument.DataType datatype, String description) {

        super("--"+optionalArgumentName, description, datatype);
        this.optionalArgumentValue=defaultValue;
    }



    public void setOptionalArgumentValue(String optionalArgumentValue) {
        this.optionalArgumentValue = optionalArgumentValue;
    }




    public String getOptionalArgumentValue() {
        return this.optionalArgumentValue;
    }

    public String getOptionalDescription(){
        return super.getArgumentDescription();
    }

    public DataType getOptionalArgumentDataType(){
        return super.getArgumentDataType();
    }


}
