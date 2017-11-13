package keywords;

import edu.wofford.*;

public class Feature04 {
    private ArgumentParser argCheck;
    private String helpmessage;

    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgumentParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("length", "the length of the box",Argument.DataType.FLOAT);
        argCheck.addArg("width", "the width of the box", Argument.DataType.FLOAT);
        argCheck.addArg("height", "the height of the box", Argument.DataType.FLOAT);
        argCheck.addOptionalArgument("type","box");
        argCheck.addOptionalArgument("digits","4");
        try{argCheck.parse(args);
            }
        catch(HelpException e){
            helpmessage = e.getMessage();
				}
}


public String getLength() {
    return argCheck.getArgumentValue("length");
}

public String getWidth() {
    return argCheck.getArgumentValue("width");
}

public String getHeight() {
    return argCheck.getArgumentValue("height");
}

public String getType() {
    return argCheck.getOptionalArgumentValue("type");
}

public String getDigits() {
    return argCheck.getOptionalArgumentValue("digits");
}


}