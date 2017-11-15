package keywords;

import edu.wofford.*;

public class Feature07 {
	private ArgumentParser argCheck;
	private String helpmessage;

    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgumentParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("type");
        argCheck.setArgumentShortFormName("type", "t");

        argCheck.addArg("length", "the length of the box", Argument.DataType.FLOAT);
        argCheck.addArg("width", "the width of the box", Argument.DataType.FLOAT);
        argCheck.addArg("digits");
        argCheck.setArgumentShortFormName("digits", "d");
        argCheck.addArg("height", "the height of the box", Argument.DataType.FLOAT);
        try{argCheck.parse(args);
				}
				catch(HelpException e){
					helpmessage = e.getMessage();
					System.out.println(helpmessage);

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
    return argCheck.getArgumentValue("type");
}

public String getDigits() {
    return argCheck.getArgumentValue("digits");
}


}