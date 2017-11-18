
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator.java  
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator 7 --width 3 --height 2 (unique sides)
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator -sp 7  flag exists, but p does not
// java -cp .:../build/libs/argparse-1.0.jar BoxVolumeCalculator -s 7  same sides

public class BoxVolumeCalculator {
    public static void main(String[] args) {
        ArgParser argchecker = new ArgParser("VolumeCalculator",
                "a program that calculates the volume of a ellipsoid");
        argchecker.addFlag("s");
        argchecker.addArg("length", "length of the box", Arg.DataType.FLOAT);
        argchecker.addOptArg("width", "1", Arg.DataType.FLOAT);
        argchecker.addOptArg("height", "1", Arg.DataType.FLOAT);
        argchecker.parse(args);

        String desiredVolume;
        float length = Float.parseFloat(argchecker.getArgValue("length"));
        if (argchecker.getArgValue("s").equals("true")) {
            desiredVolume = String.valueOf(length * length * length);
            System.out.println("The volume of the cube is " + desiredVolume + " gallons");
        } else {
            float height = Float.parseFloat(argchecker.getArgValue("height"));
            float width = Float.parseFloat(argchecker.getArgValue("width"));
            desiredVolume = String.valueOf(length * height * width);
            System.out.println("The volume of the box is " + desiredVolume + " gallons");
        }

    }
}
