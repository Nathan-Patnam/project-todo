package keywords;

import edu.wofford.*;

public class Feature08 {
	private ArgParser argCheck;
	private String errorMessageThrown;
	public void startProgramWithNonexistentArgument(String[] args) {
		argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
		argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
		argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
		argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);
		try {
			argCheck.parse(args);
		} catch (IllegalArgumentException e) {
			errorMessageThrown = e.getMessage();
			System.out.println(errorMessageThrown);

		}
	}
	
	public String getNonexistentProgramOutput() {
		return errorMessageThrown;
	}
	
	public void startProgramWithBadDatatypeArgument(String[] args) {
		argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
		argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
		argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
		argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);
		try {
			argCheck.parse(args);
		} catch (BadDataType e) {
			errorMessageThrown = e.getMessage();
			System.out.println(errorMessageThrown);

		}
	}
	
	public String getBadDatatypeProgramOutput() {
		return errorMessageThrown;
	}




}