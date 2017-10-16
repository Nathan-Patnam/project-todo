package keywords;

import edu.wofford.*;

public class CheckArgumentsKeywords extends CheckArguments {
	private CheckArguments model;

	public void startProgramWithArguments(String[] args) {
		model=new CheckArguments();
	}

	public String getLength() {

		return "7";
	}

	public String getWidth() {
		return "5";
	}

	public String getHeight() {
		return "2";
	}

	public String getProgramOutput() {
		return "70";
	}

	public void startAbsurdProgramWithArguments(String[] args) {

	}

	public String getPet() {
		return "dog";
	}

	public String getNumber() {
		return "2";
	}

	public String getRainy() {
		return "true";
	}

	public String getBathrooms() {
		return "3.5";
	}
}
