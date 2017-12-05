package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;



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
  ArgParser argcheckerOne =  XML.loadFromFile("./src/test/java/edu/wofford/realXMLFiles/realXML.xml");
  XML.saveToFile("./src/test/java/edu/wofford/testXMLFiles/yourXML.xml", argcheckerOne);
  ArgParser argcheckerTwo =  XML.loadFromFile("./src/test/java/edu/wofford/testXMLFiles/yourXML.xml");
  int h1 = argcheckerOne.getArgValue("height");
  int h2 = argcheckerTwo.getArgValue("height");
  assertEquals(h1, h2);
  assertEquals(argcheckerOne.getArgument("height").getRestrictedValuesString(), argcheckerTwo.getArgument("height").getRestrictedValuesString());

}



@Test
public void testLoadArgsAsXML(){
  try{
    //ClassLoader.getSystemResourceAsStream( 
    ArgParser argchecker =  XML.loadFromFile("./src/test/java/edu/wofford/realXMLFiles/realXML.xml");
    argchecker.setProgramName("volume calculator");
    argchecker.setProgramDescription("calculates the volume of a object");
    int i = argchecker.getArgValue("height");
    int j =argchecker.getArgValue("precision");

    assertEquals("length of the box", argchecker.getArgDescription("length"));
    assertEquals(1738, i);
    assertEquals(3, j);
    assertEquals(false, argchecker.getArgValue("metric"));
    }
    catch(Exception e){
      e.printStackTrace();
      assertTrue(false);
    }
}

@Test
public void testLoadPositionalArgsInDifferentOrder(){
  try{
    //ClassLoader.getSystemResourceAsStream( 
    String[] cla = {"1","2", "3","4","5","6","7" };
    ArgParser argchecker =  XML.loadFromFile("./src/test/java/edu/wofford/realXMLFiles/outOfOrderPositionalArgs.xml");
    argchecker.setProgramName("volume calculator");
    argchecker.setProgramDescription("calculates the volume of a object");
    argchecker.parse(cla);

    assertEquals("6", argchecker.getArgValue("argOne"));
    assertEquals("5", argchecker.getArgValue("argTwo"));
    assertEquals("4", argchecker.getArgValue("argThree"));
    assertEquals("1", argchecker.getArgValue("argFour"));
    assertEquals("2", argchecker.getArgValue("argFive"));
    assertEquals("3", argchecker.getArgValue("argSix"));
    assertEquals("7", argchecker.getArgValue("argSeven"));
  }
    catch(Exception e){
      e.printStackTrace();
      assertTrue(false);
    }

}


@Test
public final void testCreateXMLFromArg(){
  //barebones arg
   argCheck.addArg("length");
   //robust arg
   argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
   argCheck.setArgShortFormName("width", "w");
   argCheck.setArgRestricedValues("width", "7 8 9");

    XML.saveToFile("./src/test/java/edu/wofford/testXMLFiles/argTest.xml", argCheck);

}

@Test
public final void loadOnlyOptionalArgs(){
  ArgParser argchecker =  XML.loadFromFile("./src/test/java/edu/wofford/realXMLFiles/addingOnlyOptionalArgs.xml");
  argchecker.setProgramName("volume calculator");
  argchecker.setProgramDescription("calculates the volume of a object");

  assertEquals(Arg.DataType.BOOLEAN, argchecker.getArgDataType("type"));
  assertEquals(Arg.DataType.INT, argchecker.getArgDataType("volume"));
}

}
