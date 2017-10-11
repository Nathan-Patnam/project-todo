package keywords;

import edu.wofford.*;

public class Feature01Keywords extends Feature01 {
	private Feature01 model;

	public void startProgramWithArguments(String[] args) {
		model=new Feature01();
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