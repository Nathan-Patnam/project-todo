package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class CheckArgumentsTest{
    private CheckArguments argCheck;
    String[] string_array;

    @Before
    public final void setup(){
        argCheck = new CheckArguments();
        String test_string = "java VolumeCalculator 7 5";
        string_array = test_string.split(" ");
    }


    // @Test
    // public final void testRightNumberOfArgument(){
    //     assertEquals(true, argCheck.testMissingArgument("java VolumeCalculator 7 5 2"));
    // }
    //
    // @Test
    // public final void testTooManyArguements(){
    //     assertEquals(false, argCheck.testMissingArgument("java VolumeCalculator 7 5 2 1"));
    // }
    //
    // @Test
    // public final void testToFewArguements(){
    //     assertEquals(false, argCheck.testMissingArgument("java VolumeCalculator 7 5 "));
    // }

    @Test
    public final void testGetLength(){
        assertEquals(7, argCheck.getLength(string_array));
    }

    @Test
    public final void testGetWidth(){
        assertEquals(5, argCheck.getWidth(string_array));
    }

    @Test
    public final void testGetHeight(){
        assertEquals(6, argCheck.getHeight(string_array));
    }

}
