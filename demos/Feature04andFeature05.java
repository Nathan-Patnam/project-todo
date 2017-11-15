
import edu.wofford.ArgumentParser;
// javac -cp .:../build/libs/argparse-1.0.jar Feature01AndFeature02.java
// java -cp .:../build/libs/argparse-1.0.jar Feature01AndFeature02 -h


public class Feature01AndFeature02 {
    public static void main(String[] args) {


        ArgumentParser argchecker = new ArgumentParser("VolumeCalculator", "a program that calculates the volume of a box");

        argchecker.addArg("length", "length of the box");
        argchecker.addArg("width", "width of the box");
        argchecker.addArg("height", "height of the box");

        argchecker.addOptionalArgument("testOne", "1", "test of the first constructor");
        argchecker.addOptionalArgument("testTwo", "2", Argument.DataType.STRING);
        argchecker.addOptionalArgument("testThree", "3", "test of the third constructor", Argument.DataType.FLOAT);


        argchecker.parse(args);

    }
}
