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
    Float length = argCheck.getArgValue("length");
    int l = Math.round(length);
    return String.valueOf(l);
}

public String getWidth() {
    Float width = argCheck.getArgValue("width");
    int w = Math.round(width);
    return String.valueOf(w);
}

public String getHeight() {
    Float height = argCheck.getArgValue("height");
    int h = Math.round(height);
    return String.valueOf(h);
}

public String getType() {
    String type = argCheck.getArgValue("type");
    return String.valueOf(type);
}
public String getDigits() {
    String digits = argCheck.getArgValue("digits");
    return String.valueOf(digits);
}



}