package edu.wofford;

public class test {
    public void rWithoutXML(){
        String[] cla = { "3.43" , "--metric"};  
        ArgParser argparser = new ArgParser("Sphere Volume Calculator", 
        "Calculates the volume of a sphere.");
        
        argparser.addArg("radius", "the radius of the sphere", Arg.DataType.FLOAT);
        
        argparser.addOptArg("precision", "2", Arg.DataType.INT ,
        "number of decimal places volume will be given in");
        
        argparser.setArgShortFormName("precision", "p");

        argparser.setArgRestricedValues("precision", "1 2 3 4 5 6");

        argparser.setArgAsRequired("precision");

        argparser.addOptArg("metric", "false", Arg.DataType.BOOLEAN,
        "if true volume will be given in liters, if false volume will be given in gallons");
        
        //if you wanted to create the argument template
        XML.saveToFile("relative or absolute file path", argparser);
        argparser.parse(cla);
        //wanted to save the parsed information 
        XML.saveToFile("relative or absolute file path", argparser);

    }

}