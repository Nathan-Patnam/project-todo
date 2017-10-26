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
			return argCheck.getArgValue("length");
	}

	public String getWidth() {
		return argCheck.getArgValue("width");
	}

	public String getHeight() {
		return argCheck.getArgValue("height");
	}

	public String getProgramOutput() {
		if(this.message.equals("")){
			int l = Integer.valueOf(argCheck.getArgValue("length"));
			int w = Integer.valueOf(argCheck.getArgValue("width"));
			int h = Integer.valueOf(argCheck.getArgValue("height"));
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
		return argCheck.getArgValue("pet");
	}

	public String getNumber() {
		return argCheck.getArgValue("number");
	}

	public String getRainy() {
		return argCheck.getArgValue("rainy");
	}

	public String getBathrooms() {
		return argCheck.getArgValue("bathrooms");
		}
}
