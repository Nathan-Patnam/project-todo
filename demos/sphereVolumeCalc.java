import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar sphereVolumeCalc.java
// java -cp .:../build/libs/argparse-1.0.jar sphereVolumeCalc 3.43 -p 8 --metric

public class sphereVolumeCalc {
public void withXML(){
String[] cla = { "3.43" , "-p" , "3", "--metric"};  
ArgParser argparser = new ArgParser("Sphere Volume Calculator", 
"Calculates the volume of a sphere.");

argparser.addArg("radius", "the radius of the sphere", Arg.DataType.FLOAT);

argparser.addOptArg("precision", "2", Arg.DataType.INT ,
"number of decimal places volume will be given in");

argparser.setArgShortFormName("precision", "p");

argparser.addOptArg("metric", "false", Arg.DataType.BOOLEAN,
"if true volume will be given in liters, if false volume will be given in gallons");
argparser.parse(args);
}

}