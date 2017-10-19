package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class ArgumentParserTest{
    private ArgumentParser argCheck;

    @Before
    public final void setup(){
        argCheck = new ArgumentParser("VolumeCalculator");
        // argCheck.addArgs("width");
        // argCheck.addArgs("height");
				//argCheck.setArgs(args);
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

    @Test(expected = InvalidArgsException.class)
    public final void testTooFewArguments(){
      argCheck.addArg("length");
      argCheck.addArg("width");
      String[] cla = {"5"};

      argCheck.parse(cla);


    }


    @Test(expected = InvalidArgsException.class)
    public final void testTooManyArguments(){
      argCheck.addArg("length");
      String[] cla = {"5","3"};
      argCheck.parse(cla);


    }
    /*
    @Test
    public final void testLengthKeyExist(){
        assertEquals("length", argCheck.keys[0]);
    }
    */


    // @Test
    // public final void testGetWidth(){
    //     assertEquals("3", argCheck.getArgs("width"));
    // }
    //
    // @Test
    // public final void testGetHeight(){
    //     assertEquals("2", argCheck.getArgs("length"));
    // }
		// @Test
		// public final void testGetHeight(){
		// 		assertEquals(, argCheck.getArgs("length"));
		// }


}
