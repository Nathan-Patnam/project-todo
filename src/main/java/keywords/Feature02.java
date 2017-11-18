package keywords;

import edu.wofford.*;

public class Feature02 {
	private ArgParser argCheck;
	private String helpmessage;

	public void startProgramWithArguments(String[] args) {
		argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
		argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
		argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
		argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);
		try {
			argCheck.parse(args);
		} catch (HelpException e) {
			helpmessage = e.getMessage();
			System.out.println(helpmessage);

		}
	}

	public String getProgramOutput() {
		return helpmessage;
	}

}