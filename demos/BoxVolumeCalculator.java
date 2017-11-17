
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator.java  
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator 7 --width 3 --height 2 (unique sides)
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator -sp 7  flag exists, but p does not
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator -s 7  same sides

public class BoxVolumeCalculator {
    public static void main(String[] args) {
        ArgumentParser argchecker = new ArgumentParser("VolumeCalculator",
                "a program that calculates the volume of a ellipsoid");
        argchecker.addFlag("s");
        argchecker.addArg("length", "length of the box", Argument.DataType.FLOAT);
        argchecker.addOptionalArgument("width", "1", Argument.DataType.FLOAT);
        argchecker.addOptionalArgument("height", "1", Argument.DataType.FLOAT);
        argchecker.parse(args);

        String desiredVolume;
        float length = Float.parseFloat(argchecker.getArgumentValue("length"));
        if (argchecker.getArgumentValue("s").equals("true")) {
            desiredVolume = String.valueOf(length * length * length);
            System.out.println("The volume of the cube is " + desiredVolume + " gallons");
        } else {
            float height = Float.parseFloat(argchecker.getArgumentValue("height"));
            float width = Float.parseFloat(argchecker.getArgumentValue("width"));
            desiredVolume = String.valueOf(length * height * width);
            System.out.println("The volume of the box is " + desiredVolume + " gallons");
        }

    }
}
