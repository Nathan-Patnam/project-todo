package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class ArgumentParserTest{
    private ArgumentParser argCheck;
    String[] string_array;

    @Before
    public final void setup(){
        argCheck = new ArgumentParser("VolumeCalculator");
				argCheck.setArgs("length width height", "2 3 4");
    }

    @Test
    public final void testGetLength(){
        assertEquals("4", argCheck.getArgs("height"));
    }

    @Test
    public final void testGetWidth(){
        assertEquals("3", argCheck.getArgs("width"));
    }

    @Test
    public final void testGetHeight(){
        assertEquals("2", argCheck.getArgs("length"));
    }
		

}
