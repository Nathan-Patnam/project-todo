import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar xmlFeature12.java
// java -cp .:../build/libs/argparse-1.0.jar xmlFeature12


public class xmlFeature12 {
    public static void main(String[] args) {
    ArgParser argchecker = new ArgParser("VolumeCalculator", 
    "a program that calculates the volume of a ellipsoid");
    argchecker.addArg("length", "length of the box", Arg.DataType.FLOAT);
    argchecker.setArgShortFormName("length","l");
    argchecker.addArg("width", "width of the box", Arg.DataType.FLOAT);
    argchecker.setArgShortFormName("width","w");
    argchecker.setArgRestricedValues("width", "3 4 5");
    argchecker.addOptArg("height", "1738",Arg.DataType.FLOAT, "height of the box" );
    argchecker.setArgShortFormName("height","g");
    argchecker.setArgRestricedValues("height","7 8 9");
    argchecker.addOptArg("type", "pyramid",Arg.DataType.STRING, "shape of the box" );
    argchecker.setArgShortFormName("type","t");
    argchecker.setArgRestricedValues("type","cube ellipsoid sphere");
    argchecker.setArgAsRequired("type");
    argchecker.addFlag("s");
    argchecker.getArgInfoAsXML("feature12.xml");
    }
  }
