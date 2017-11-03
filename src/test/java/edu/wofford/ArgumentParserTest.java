package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class ArgumentParserTest{
    private ArgumentParser argCheck;
    private ArgumentParser argCheck2;

    @Before
    public final void setup(){
        argCheck = new ArgumentParser("VolumeCalculator");
        argCheck2 = new ArgumentParser("VolumeCalculator","Calculate the volume of a box.");
    }



    @Test
    public final void testProgramName() {
      assertEquals("VolumeCalculator", argCheck.getProgramName());
    }

    @Test
    public final void testGetNumArguments() {
      assertEquals(0, argCheck.getNumArguments());
    }

    @Test
    public final void testAddArgument() {
      assertEquals(0, argCheck.getNumArguments());
      argCheck.addArg("length");
      assertEquals(1, argCheck.getNumArguments());
    }

    @Test
    public final void testGetSingleArgumentValue() {
      argCheck.addArg("length");
      String[] cla = {"5"};
      argCheck.parse(cla);
      assertEquals("5", argCheck.getArgValue("length"));
    }

    @Test(expected = TooFewArguments.class)
    public final void testTooFewArguments(){
      argCheck.addArg("length");
      argCheck.addArg("width");
      String[] cla = {"5"};
      argCheck.parse(cla);
    }

    @Test(expected = TooManyArguments.class)
    public final void testTooManyArguments(){
      argCheck.addArg("length");
      String[] cla = {"5","3"};
      argCheck.parse(cla);
    }

    @Test
    public void TestTooManyArgumentsString(){
      argCheck.addArg("length");
      argCheck.addArg("width");
      argCheck.addArg("height");
      String[] cla = {"7","5","2","43"};
      try{
      argCheck.parse(cla);
      fail("Should have thrown TooManyArguments but did not!");
      }catch(TooManyArguments expected)
      {
      String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: unrecognized arguments: 43";
      assertEquals(msg, expected.getMessage());
      }
    }

    @Test
    public void TestTooFewArgumentsString(){
      argCheck.addArg("length");
      argCheck.addArg("width");
      argCheck.addArg("height");
      String[] cla = {"7","5"};
      try{
      argCheck.parse(cla);
      fail("Should have thrown TooFewArguments but did not!");
      }catch(TooFewArguments expected){
      String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: the following arguments are required: height";
      assertEquals(msg, expected.getMessage());
      }
    }


    @Test
    public void TestHelp(){
      String[] cla = {"-h"};
      argCheck2.addArg("length","the length of the box");
      argCheck2.addArg("width","the width of the box");
      argCheck2.addArg("height","the height of the box");


      String msg="usage: java VolumeCalculator length width height\nCalculate the volume of a box.\npositional arguments:\n   length the length of the box\n   width the width of the box\n   height the height of the box";
      try{
      argCheck2.parse(cla);
      fail("Should have thrown HelpException but did not!");
      }catch(HelpException expected){
        assertEquals(msg, expected.getMessage());
      }
    }

    @Test
    public void TestProgramDescription(){
      String msg = "Calculate the volume of a box.";
      assertEquals(msg, argCheck2.getProgramDescription());
    }

    @Test
    public void TestgetDataType(){
        argCheck2.addArg("Length", "Length of the box.", "float");
        assertEquals("float", argCheck2.getDataType("Length"));
    }

    @Test
    public void TestGetDefaultStringType(){
      argCheck2.addArg("Length");
      assertEquals("string", argCheck2.getDataType("Length"));
    }

    @Test
    public void TestValidDataTypes(){
      String[] cla = {"3","something","7"};
      argCheck2.addArg("length","the length of the box","float");
      argCheck2.addArg("width","the width of the box","float");
      argCheck2.addArg("height","the height of the box","float");


      String msg="usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument width: invalid float value: something";
      try{
      argCheck2.parse(cla);
      fail("Should have thrown HelpException but did not!");
      }catch(HelpException expected){
        System.out.println("help message is");
        assertEquals(msg, expected.getMessage());
        System.out.println("end help message");
      }
    }


    @Test
    public void multipleBadDataTypes(){
      String[] cla = {"yup","something","one"};
      argCheck2.addArg("length","the length of the box","float");
      argCheck2.addArg("width","the width of the box","boolean");
      argCheck2.addArg("height","the height of the box","int");


      String msg="usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument length: invalid float value: yup\n"
      + "argument width: invalid boolean value: something\n"
      + "argument height: invalid int value: one\n";
      try{
      argCheck2.parse(cla);
      fail("Should have thrown HelpException but did not!");
      }catch(HelpException expected){
        assertEquals(msg, expected.getMessage());
      }
    }
}
