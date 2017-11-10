
package keywords;

import edu.wofford.*;

public class Feature01 {
	private ArgumentParser argCheck;
	private String message = "";

	public void startProgramWithArguments(String[] args) {
			argCheck = new ArgumentParser("VolumeCalculator");
			argCheck.addArg("length");
			argCheck.addArg("width");
			argCheck.addArg("height");
			try {argCheck.parse(args);}
			catch(RuntimeException e){
				this.message = e.getMessage();
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

	public String getProgramOutput() {
		if(this.message.equals("")){
			int l = Integer.valueOf(argCheck.getArgumentValue("length"));
			int w = Integer.valueOf(argCheck.getArgumentValue("width"));
			int h = Integer.valueOf(argCheck.getArgumentValue("height"));
			int volume = l * w * h;

			return Integer.toString(volume);
		}
		else{
			return this.message;
		}
	}

	public void startAbsurdProgramWithArguments(String[] args) {
			argCheck = new ArgumentParser("AbsurdProgram");
			argCheck.addArg("pet");
			argCheck.addArg("number");
			argCheck.addArg("rainy");
			argCheck.addArg("bathrooms");
			argCheck.parse(args);
	}
	public String getPet() {
		return argCheck.getArgumentValue("pet");
	}

	public String getNumber() {
		return argCheck.getArgumentValue("number");
	}

	public String getRainy() {
		return argCheck.getArgumentValue("rainy");
	}

	public String getBathrooms() {
		return argCheck.getArgumentValue("bathrooms");
		}
}
