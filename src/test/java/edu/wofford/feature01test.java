package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class feature01test{
    private feature01 argCheck;

    @Before
    public final void setup(){
        argCheck = new feature01();
    }


    @Test
    public final void testMissingArgument(){
        assertEquals(true, argCheck.testMissingArgument("java VolumeCalculator 7 5 2"));
    }
}