package keywords;

import edu.wofford.*;

public class Feature02 {
	private ArgumentParser argCheck;
    
    public void startProgramWithArguments(String[] args) {
        argCheck = new ArgumentParser("VolumeCalculator", "Calculate the volume of a box.");
        argCheck.addArg("length", "the length of the box");
        argCheck.addArg("width", "the width of the box");
        argCheck.addArg("height", "the height of the box");
        argCheck.parse(args);
}

public String getProgramOutput() {
argCheck.getHelpMessage();
//yyyyy
}


}
