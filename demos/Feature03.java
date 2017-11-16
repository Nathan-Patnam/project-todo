
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar Feature03.java
// java -cp .:../build/libs/argparse-1.0.jar Feature03 1 one two


public class Feature03 {
            public static void main(String[] args) {


                ArgumentParser argchecker = new ArgumentParser("VolumeCalculator", "a program that calculates the volume of a box");

                argchecker.addArg("length", "length of the box",Argument.DataType.FLOAT);
                argchecker.addArg("width", "width of the box", Argument.DataType.INT);
                argchecker.addArg("height", "height of the box", Argument.DataType.INT);
                argchecker.parse(args);
                /*
                int length=Integer.parseInt(argchecker.getArgValue("length"));
                int width=Integer.parseInt(argchecker.getArgValue("width"));
                int height=Integer.parseInt(argchecker.getArgValue("height"));
                int volume= length*width*height;

                System.out.println("The length arguement is " + String.valueOf(length));
                System.out.println("The width arguement is " + String.valueOf(width));
                System.out.println("The height arguement is " + String.valueOf(height));
                System.out.println("The volume is " + String.valueOf(volume));


        */

            }
        }
