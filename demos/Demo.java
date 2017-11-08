
import edu.wofford.ArgumentParser;

public class Demo {
            public static void main(String[] args) {


                ArgumentParser argchecker = new ArgumentParser("VolumeCalculator", "a program that calculates the volume of a box");

                argchecker.addArg("length", "length of the box");
                argchecker.addArg("width", "width of the box");
                argchecker.addArg("height", "height of the box");

                System.out.println("The help message is: \n");
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

                ArgumentParser argchecker2 = new ArgumentParser("AnotherVolumeCalculator", "a program that calculates the volume of a box but has MORE FEATURES!");

                argchecker2.addArg("length", "length of the box", "float");
                argchecker2.addArg("width", "float");
                argchecker2.addArg("height", "height of the box");

                System.out.println("The help message is: \n");
                argchecker.parse(args);
            }
        }
