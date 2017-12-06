
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar EllipsoidCalculator.java  
// java -cp .:../build/libs/argparse-1.0.jar EllipsoidCalculator 7 --decimalPlaces 3 -h 3 -t ellipsoid 2     show help message
// java -cp .:../build/libs/argparse-1.0.jar EllipsoidCalculator 7 --decimalPlaces 3 3 -t ellipsoid 2 2     to many arguments
// java -cp .:../build/libs/argparse-1.0.jar EllipsoidCalculator 7 --decimalPlaces 3 3 -t ellipsoid      to few arguments
// java -cp .:../build/libs/argparse-1.0.jar EllipsoidCalculator 7 --decimalPlaces 3  3 -t ellipsoid two   bad datatypes
// java -cp .:../build/libs/argparse-1.0.jar EllipsoidCalculator 7 --decimalPlaces 3 --someval 2 3 -tp ellipsoid 2    adding a argument that doesn't exist  
// java -cp .:../build/libs/argparse-1.0.jar EllipsoidCalculator 7 --decimalPlaces 3  3 -t ellipsoid 2   one optional Argument is set/one is left as default value and a optional Argument is given a default value



public class EllipsoidCalculator {
            public static void main(String[] args) {
                ArgParser argchecker = new ArgParser("VolumeCalculator", "a program that calculates the volume of a ellipsoid");
                argchecker.addArg("length", "length of the ellipsoid",Arg.DataType.FLOAT);
                argchecker.addOptArg("units", "gallons", Arg.DataType.STRING, "units that the measurements and volume will be given in" );
                argchecker.addOptArg("decimalPlaces", "2", Arg.DataType.INT, "desired accuracy of calculated volume");
                argchecker.addArg("width", "width of the ellipsoid",Arg.DataType.FLOAT);
                argchecker.addArg("type", "shape you want to calculate the volume of",Arg.DataType.STRING);
                argchecker.setArgShortFormName("type", "t");
                argchecker.addArg("height", "height of the ellipsoid",Arg.DataType.FLOAT);
                argchecker.parse(args);

                float length = Float.parseFloat(argchecker.getArgValue("length"));
                float height = Float.parseFloat(argchecker.getArgValue("height"));
                float width = Float.parseFloat(argchecker.getArgValue("width"));
                String desiredDecimals= argchecker.getArgValue("decimalPlaces");
                String desiredVolume= String.format("%." + desiredDecimals+ "f", (length *width*height * Math.PI * (4/3)));
                System.out.println("The volume of the ellipsoid is " + desiredVolume +"." );

            }
        }
