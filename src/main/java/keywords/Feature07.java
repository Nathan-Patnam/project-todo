package keywords;

import edu.wofford.*;

public class Feature07 {
	private ArgParser argCheck;
	private String helpmessage;

    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("type");
        argCheck.setArgShortFormName("type", "t");

        argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
        argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
        argCheck.addArg("digits");
        argCheck.setArgShortFormName("digits", "d");
        argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);
        try{argCheck.parse(args);
				}
				catch(HelpException e){
					helpmessage = e.getMessage();
					System.out.println(helpmessage);

				}
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

public String getDigits() {
    return argCheck.getArgValueString("digits");
}


}