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

@Test
public void testSaveArgsAsXML(){
  ArgParser argcheckerOne =  XML.loadFromFile("/Users/Patnamnv/Desktop/practice-project-todo/src/test/java/edu/wofford/realXMLFiles/realXML.xml");
  XML.saveToFile("/Users/Patnamnv/Desktop/practice-project-todo/src/test/java/edu/wofford/testXMLFiles/yourXML.xml", argcheckerOne);
  ArgParser argcheckerTwo =  XML.loadFromFile("/Users/Patnamnv/Desktop/practice-project-todo/src/test/java/edu/wofford/testXMLFiles/yourXML.xml");
  assertEquals(argcheckerOne.getArgValue("height"), argcheckerTwo.getArgValue("height"));
  assertEquals(argcheckerOne.getArgument("height").getRestrictedValuesString(), argcheckerTwo.getArgument("height").getRestrictedValuesString());

}


@Test
public void testLoadArgsAsXML(){
  try{
    //ClassLoader.getSystemResourceAsStream( 
    ArgParser argchecker =  XML.loadFromFile("/Users/Patnamnv/Desktop/practice-project-todo/src/test/java/edu/wofford/realXMLFiles/realXML.xml");
    argchecker.setProgramName("volume calculator");
    argchecker.setProgramDescription("calculates the volume of a object");

    assertEquals("length of the box", argchecker.getArgDescription("length"));
    assertEquals("1738", argchecker.getArgValue("height"));
    assertEquals("3", argchecker.getArgValue("precision"));
    assertEquals("false", argchecker.getArgValue("metric"));
    }
    catch(Exception e){
      e.printStackTrace();
      assertTrue(false);
    }
}
}
