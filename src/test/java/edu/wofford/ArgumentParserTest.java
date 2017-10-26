package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class ArgumentParserTest{
    private ArgumentParser argCheck;

    @Before
    public final void setup(){
        argCheck = new ArgumentParser("VolumeCalculator");
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
    }catch(TooFewArguments expected)
      {
      String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: the following arguments are required: height";
      assertEquals(msg, expected.getMessage());
      }
    }

    @Test
    public void TestHelp(){
      argCheck.addArg("length","the length of the box");
      argCheck.addArg("width","the width of the box");
      argCheck.addArg("height","the height of the box");
      String msg="usage: java VolumeCalculator length width height\nCalculate the volume of a box.\npositional arguments:\n   length the length of the box\n   width the width of the box\n   height the height of the box";
      assertEquals(msg, );
    }
    @Test
    public void TestProgramDescription(){
      argCheck2 = ArgumentParser("VolumeCalculator", "Calculate the volume of a box.");
      String msg = "Calculate the volume of a box.";
      assertEquals(msg, );
    }
}