package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class Feature01test{
    private Feature01 argCheck;

    @Before
    public final void setup(){
        argCheck = new Feature01();
    }


    @Test
    public final void testRightNumberOfArgument(){
        assertEquals(true, argCheck.testMissingArgument("java VolumeCalculator 7 5 2"));
    }

    @Test
    public final void testToManyArguements(){
        assertEquals(false, argCheck.testMissingArgument("java VolumeCalculator 7 5 2 1"));
    }

    @Test
    public final void testToFewArguements(){
        assertEquals(false, argCheck.testMissingArgument("java VolumeCalculator 7 5 "));
    }
}