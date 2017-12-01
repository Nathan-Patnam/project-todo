package keywords;

import edu.wofford.*;

public class Feature13 {
    private ArgParser argCheck;
    private String helpmessage;

    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("length", "the length of the box",Arg.DataType.FLOAT);
        argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
        argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);
        argCheck.addOptArg("type", "box", Arg.DataType.STRING);
        argCheck.setArgAsRequired("type");

        try{argCheck.parse(args);
            }
        catch(RequiredArgException e){
            helpmessage = e.getMessage();
				}
}

public String getProgramOutput() {
	return helpmessage;
}

public String getLength() {
    return argCheck.getArgValue("length");
}

public String getWidth() {
return argCheck.getArgValue("width");
}

public String getHeight() {
return argCheck.getArgValue("height");
}

public String getType() {
    return argCheck.getArgValue("type");
 }




}