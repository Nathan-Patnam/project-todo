import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar xmlDemoProgram.java
// java -cp .:../build/libs/argparse-1.0.jar xmlDemoProgram 7 --myarg mval 3 2


public class xmlDemoProgram {
    public static void main(String[] args) {
    ArgParser argchecker = new ArgParser("VolumeCalculator", 
    "a program that calculates the volume of a ellipsoid");
    argchecker.addArg("length", "length of the box", Arg.DataType.FLOAT);
    argchecker.addOptArg("width", "1738",Arg.DataType.FLOAT, "width of the box" );
    argchecker.getArgInfoAsXML("yourXML.xml");
    }
  }
