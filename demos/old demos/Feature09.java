
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar Feature09.java
// java -cp .:../build/libs/argparse-1.0.jar Feature09


public class Feature09 {
    public static void main(String[] args) {
      ArgParser argchecker = new ArgParser("VolumeCalculator", 
      "a program that calculates the volume of a ellipsoid");
      argchecker.addArg("length", "length of the box", Arg.DataType.FLOAT);
      argchecker.setArgShortFormName("length","l");
      argchecker.addArg("width", "width of the box", Arg.DataType.FLOAT);
      argchecker.setArgShortFormName("width","w");
      argchecker.addOptArg("height", "1738",Arg.DataType.FLOAT, "height of the box" );
      argchecker.setArgShortFormName("height","g");
      argchecker.addFlag("s");
      argchecker.getArgInfoAsXML("yourXML.xml");
    }
  }