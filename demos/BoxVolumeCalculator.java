
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator.java  
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator 7 --decimalPlaces 3 3 2  unique sides
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator -m 7 --decimalPlaces 3 3 2  metric sides
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator 7 --decimalPlaces 3 3 2  non-metric sides

public class BoxVolumeCalculator {
    public static void main(String[] args) {
        ArgumentParser argchecker = new ArgumentParser("VolumeCalculator",
                "a program that calculates the volume of a ellipsoid");
        argchecker.addFlag("m");
        argchecker.addArg("length", "length of the box", Argument.DataType.FLOAT);
        argchecker.addOptionalArgument("decimalPlaces", "2", Argument.DataType.INT,
                "desired accuracy of calculated volume");
        argchecker.addArg("width", "width of the ellipsoid", Argument.DataType.FLOAT);
        argchecker.addArg("height", "height of the ellipsoid", Argument.DataType.FLOAT);
        argchecker.parse(args);

        String desiredVolume;
        float length = Float.parseFloat(argchecker.getArgumentValue("length"));
        String desiredDecimals = argchecker.getArgumentValue("decimalPlaces");
        if (argchecker.getArgumentValue("m").equals("true")) {
            desiredVolume = String.format("%." + desiredDecimals + "f", (length * length * length));
            System.out.println("The volume of the cube is " + desiredVolume + " liters");
        } else {
            float height = Float.parseFloat(argchecker.getArgumentValue("length"));
            float width = Float.parseFloat(argchecker.getArgumentValue("length"));
            desiredVolume = String.format("%." + desiredDecimals + "f", (length * width * height));
            System.out.println("The volume of the box is " + desiredVolume + " gallons");
        }

    }
}
