package keywords;

import edu.wofford.*;

public class Feature12 {
    private ArgParser argCheck;
    private String helpmessage;

    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("length", "the length of the box",Arg.DataType.FLOAT);
        argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
        argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);
        argCheck.addOptArg("type", "box", Arg.DataType.STRING);
        argCheck.setArgRestricedValues("type", "box pyramid ellipsoid");

        try{argCheck.parse(args);
            }
        catch(RestrictedValueException e){
            helpmessage = e.getMessage();
				}
}

public String getProgramOutput() {
	return helpmessage;
}

public String getLength() {
    return argCheck.getArgValueString("length");
}

public String getWidth() {
return argCheck.getArgValueString("width");
}

public String getHeight() {
return argCheck.getArgValueString("height");
}

public String getType() {
    return argCheck.getArgValueString("type");
 }




}