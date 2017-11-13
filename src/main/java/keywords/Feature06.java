package keywords;

import edu.wofford.*;

public class Feature06 {
	private ArgumentParser argCheck;
	private String helpmessage;

    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgumentParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("length", "the length of the box", Argument.DataType.FLOAT);
        argCheck.addFlag("help");
        argCheck.addArg("width", "the width of the box", Argument.DataType.FLOAT);
        argCheck.addArg("height", "the height of the box", Argument.DataType.FLOAT);
        try{argCheck.parse(args);
				}
				catch(HelpException e){
					helpmessage = e.getMessage();
					System.out.println(helpmessage);

				}
}

public String getProgramOutput() {
	return helpmessage;
}


}