package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;



public class XMLTest {
  private ArgParser argCheck;
  private ArgParser argCheckSimple;

  @Before
  public final void setup() {
    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
    argCheckSimple = new ArgParser("VolumeCalculator");



}
}
