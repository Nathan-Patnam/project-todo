
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar Test.java
// java -cp .:../build/libs/argparse-1.0.jar Test 7 --myarg mval 3 2


public class Test {
    public static void main(String[] args) {
    ArgumentParser argchecker = new ArgumentParser("VolumeCalculator", 
    "a program that calculates the volume of a ellipsoid");
    argchecker.addArg("length");
    argchecker.addArg("width");
    argchecker.addArg("height");
    argchecker.parse(args);
    }
  }
