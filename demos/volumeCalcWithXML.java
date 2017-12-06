
import edu.wofford.*;
// javac -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML.java  
// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML -h                                               help message
// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML 1 2 3 --type box                                 restricted values
// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML one 2 3 --type pyramid                           bad data type
// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML 1 --type pyramid                                 to few arguments
// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML 1 2 3 --type pyramid 4                           to many arguments
// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML 1 2 3 --shape pyramid 4                          argument doesn't exist


// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML 1 2 3 --type rectangular-prism
// java -cp .:../build/libs/argparse-1.0.jar volumeCalcWithXML 1 2 --metric 3 --type rectangular-prism

public class volumeCalcWithXML {
    public static void main(String[] args) {
    ArgParser argumentParser =  XML.loadFromFile("./XML Files/volumeCalculatorTemplate.xml");    
    argumentParser.parse(args);

    String volume = "";
    String units;
    Boolean metric = argumentParser.getArgValue("metric");
    float length = argumentParser.getArgValue("length");
    float width = argumentParser.getArgValue("width");
    float height = argumentParser.getArgValue("height");
    String type = argumentParser.getArgValue("type");

    int precision = argumentParser.getArgValue("precision");
    String numberOfDecimalPlaces = String.valueOf(precision);

    if(metric){
        units = "liters";
    }
    else{
        units = "gallons";
    }
    
    if(type.equals("rectangular-prism")){
        volume = String.format("%." + numberOfDecimalPlaces+ "f", (length *width*height));
    }
    else if(type.equals("pyramid")){
        volume = String.format("%." + numberOfDecimalPlaces+ "f", ((length *width*height)/3) );
    }
    else if(type.equals("ellipsoid")){
        volume = String.format("%." + numberOfDecimalPlaces+ "f", (length *width*height * Math.PI * (4/3)));
    }

    System.out.println("The volume of the " + type + " is " + volume +" " + units);

    XML.saveToFile("./XML Files/volumeCalculatorSavedValues.xml", argumentParser);
    }
}
