package keywords;

import edu.wofford.*;

public class Feature02 {
	private ArgumentParser argCheck;
	private String helpmessage;

    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgumentParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("length", "the length of the box (float)");
        argCheck.addArg("width", "the width of the box (float)");
        argCheck.addArg("height", "the height of the box (float)");
        try{argCheck.parse(args);
				}
				catch(HelpException e){
					helpmessage = e.getMessage();
					System.out.println("print message below");

					System.out.println(helpmessage);

					System.out.println("bye bye");

				}
}

public String getProgramOutput() {
	return helpmessage;
}


}
