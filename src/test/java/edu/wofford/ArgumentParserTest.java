
package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;



public class ArgumentParserTest {
  private ArgParser argCheck;
  private ArgParser argCheckSimple;

  @Before
  public final void setup() {
    argCheckSimple = new ArgParser("VolumeCalculator");
    argCheck = new ArgParser("VolumeCalculator", "Calculate the volume of a box.");
    

  }

  @Test
  public final void setThenTestProgramDescription() {
    argCheckSimple.setProgramName("Calculate the volume of a box.");
    assertEquals("Calculate the volume of a box.", argCheck.getProgramDescription());
  }


  @Test
  public final void testProgramName() {
    assertEquals("VolumeCalculator", argCheck.getProgramName());
  }

  @Test
  public final void testProgramDescription() {
    assertEquals("Calculate the volume of a box.", argCheck.getProgramDescription());
  }

  @Test
  public final void testOneProgramArgument() {
    argCheck.addArg("length");
    argCheck.getArgument("length").setDescription("side of a box");
    assertEquals("side of a box", argCheck.getArgDescription("length"));
  }

  @Test
  public final void testGetNumArguments() {
    assertEquals(0, argCheck.getNumberArgs());
  }

  @Test
  public final void testAddArgument() {
    assertEquals(0, argCheck.getNumberArgs());
    argCheck.addArg("length");
    assertEquals(1, argCheck.getNumberArgs());
  }

  @Test(expected = TooFewArguments.class)
  public final void testTooFewArguments() {
    argCheck.addArg("length");
    argCheck.addArg("width");
    String[] cla = { "5" };
    argCheck.parse(cla);
  }

  @Test(expected = HelpException.class)
  public final void helpMessage() {
    argCheck.addArg("length");
    argCheck.addArg("width");
    String[] cla = { "--help" };
    argCheck.parse(cla);
  }

  @Test(expected = TooManyArguments.class)
  public final void testTooManyArguments() {
    argCheck.addArg("length");
    String[] cla = { "5", "3" };
    argCheck.parse(cla);
  }

  @Test
  public void TestTooManyArgumentsString() {
    argCheck.addArg("length");
    argCheck.addArg("width");
    argCheck.addArg("height");
    String[] cla = { "7", "5", "2", "43" };
    try {
      argCheck.parse(cla);
      fail("Should have thrown TooManyArguments but did not!");
    } catch (TooManyArguments expected) {
      String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: unrecognized arguments: 43";
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestTooFewArgumentsString() {
    argCheck.addArg("length");
    argCheck.addArg("width");
    argCheck.addArg("height");
    String[] cla = { "7", "5" };
    try {
      argCheck.parse(cla);
      fail("Should have thrown TooFewArguments but did not!");
    } catch (TooFewArguments expected) {
      String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: the following arguments are required: height";
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestHelp() {
    String[] cla = { "-h" };
    argCheck.addArg("length", "the length of the box");
    argCheck.addArg("width", "the width of the box");
    argCheck.addArg("height", "the height of the box");

    String msg = "usage: java VolumeCalculator length width height\nCalculate the volume of a box.\npositional arguments:\n   length the length of the box (string)\n   width the width of the box (string)\n   height the height of the box (string)";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (HelpException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public final void testGetSingleArgumentValue() {
    argCheck.addArg("length");
    String[] cla = { "5" };
    argCheck.parse(cla);
    assertEquals("5", argCheck.getArgValue("length"));
  }

  @Test
  public void addDataType() {
    argCheck.addArg("length");
    argCheck.getArgument("length").setDataType(Arg.DataType.FLOAT);
    assertEquals(Arg.DataType.FLOAT, argCheck.getArgDataType("length"));
  }

  @Test
  public void TestgetDataType() {
    argCheck.addArg("Length", "Length of the box.", Arg.DataType.FLOAT);
    assertEquals("float", argCheck.getArgDataTypeString("Length"));
  }

  @Test
  public void TestGetDefaultStringType() {
    argCheck.addArg("Length");
    assertEquals(Arg.DataType.STRING, argCheck.getArgDataType("Length"));
  }

  @Test
  public void Test2ParamConstructor() {
    argCheck.addArg("Length", Arg.DataType.FLOAT);
    assertEquals(Arg.DataType.FLOAT, argCheck.getArgDataType("Length"));
  }

  @Test
  public void TestValidDataTypes() {
    String[] cla = { "3", "something", "3.0" };
    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
    argCheck.addArg("width", "the width of the box", Arg.DataType.FLOAT);
    argCheck.addArg("height", "the height of the box", Arg.DataType.FLOAT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument width: invalid float value: something";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (BadDataTypeException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void testSingleBadDataType() {
    String[] cla = { "--optionalArgOne", "true" };
    argCheck.addOptArg("optionalArgOne", "6", Arg.DataType.FLOAT);
    String msg = "usage: java VolumeCalculator optionalArgOne\nVolumeCalculator.java: error: argument optionalArgOne: invalid float value: true";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (BadDataTypeException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }


  @Test
  public void TestMultipleBadDataTypes() {
    String[] cla = { "yup", "something", "one" };
    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
    argCheck.addArg("width", "the width of the box", Arg.DataType.BOOLEAN);
    argCheck.addArg("height", "the height of the box", Arg.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument length: invalid float value: yup";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (BadDataTypeException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestMultipleBadDataTypesBoolean() {
    String[] cla = { "yup", "something", "one" };
    argCheck.addArg("length", "the length of the box", Arg.DataType.BOOLEAN);
    argCheck.addArg("width", "the width of the box", Arg.DataType.BOOLEAN);
    argCheck.addArg("height", "the height of the box", Arg.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument length: invalid boolean value: yup";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (BadDataTypeException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestMultipleBadDataTypesInt() {
    String[] cla = { "7", "something", "one" };
    argCheck.addArg("length", "the length of the box", Arg.DataType.INT);
    argCheck.addArg("width", "the width of the box", Arg.DataType.INT);
    argCheck.addArg("height", "the height of the box", Arg.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument width: invalid int value: something";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (BadDataTypeException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestBadArguments() {
    String[] cla = { "yup", "something", "one" };
    argCheck.addArg("length", "the length of the box", Arg.DataType.FLOAT);
    argCheck.addArg("width", "the width of the box", Arg.DataType.BOOLEAN);
    argCheck.addArg("height", "the height of the box", Arg.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument length: invalid float value: yup";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (BadDataTypeException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void testOptionalArgumentDefault() {
    String[] cla = {};
    argCheck.addOptArg("optionalArgOne", "optionalArgOneDefaultValue");
    argCheck.parse(cla);

    assertEquals("optionalArgOneDefaultValue", argCheck.getArgValue("optionalArgOne"));
  }

  @Test
  public void testOptionalArgumentSetValue() {
    String[] cla = { "--optionalArgOne", "8" };
    argCheck.addOptArg("optionalArgOne", "optionalArgOneDefaultValue");
    argCheck.parse(cla);

    assertEquals("8", argCheck.getArgValue("optionalArgOne"));
  }

  @Test
  public void testOptionalArgumentAllConstructors() {
    String[] cla = { "--length", "8" };
    argCheck.addOptArg("length", "3");
    argCheck.addOptArg("height", "4", "height of box");
    argCheck.addOptArg("width", "5", Arg.DataType.FLOAT);
    argCheck.parse(cla);

    assertEquals("8", argCheck.getArgValue("length"));
    assertEquals("height of box", argCheck.getArgDescription("height"));
    assertEquals(Arg.DataType.FLOAT, argCheck.getArgDataType("width"));
  }

  @Test
  public void testOptionalArgument() {
    String[] cla = { "7" };
    argCheck.addArg("length");
    argCheck.addOptArg("optionalArgOne", "optionalArgOneDefaultValue");
    argCheck.parse(cla);

    assertEquals("7", argCheck.getArgValue("length"));
    assertEquals("optionalArgOneDefaultValue", argCheck.getArgValue("optionalArgOne"));
  }

  @Test
  public void testOptionalArgumentWithDifferentConstructor() {
    String[] cla = { "7" };
    argCheck.addArg("length");
    argCheck.addOptArg("optionalArgOne", "optionalArgOneDefaultValue", "this is an optional argument");
    argCheck.parse(cla);

    assertEquals("7", argCheck.getArgValue("length"));
    assertEquals("optionalArgOneDefaultValue", argCheck.getArgValue("optionalArgOne"));
    assertEquals("this is an optional argument", argCheck.getArgDescription("optionalArgOne"));
  }

  @Test
  public void getDataTypeOptionaArgument() {
    String[] cla = { "rip" };
    argCheck.addArg("length");
    argCheck.addOptArg("optionalArgOne", "optionalArgOneDefaultValue", Arg.DataType.STRING);
    argCheck.parse(cla);

    assertEquals(Arg.DataType.STRING, argCheck.getArgDataType("optionalArgOne"));

  }

  @Test
  public void addOptionalArgumentConstructorTests() {
    String[] cla = { "rip" };
    argCheck.addArg("length");
    argCheck.addOptArg("optionalArgOne", "optionalArgOneDefaultValue", Arg.DataType.STRING,
        "my funeral");
    argCheck.addOptArg("type", "typevalue", Arg.DataType.STRING, "my funeral");
    argCheck.parse(cla);
    assertEquals("optionalArgOneDefaultValue", argCheck.getArgValue("optionalArgOne"));
    assertEquals("typevalue", argCheck.getArgValue("type"));

  }

  @Test
  public void checkDifferentArgumentDataTypes() {
    String[] cla = { "true", "7" };
    argCheck.addArg("isNumber", Arg.DataType.BOOLEAN);
    argCheck.addArg("length", Arg.DataType.FLOAT);
    assertEquals(Arg.DataType.BOOLEAN, argCheck.getArgDataType("isNumber"));
    assertEquals(Arg.DataType.FLOAT, argCheck.getArgDataType("length"));

  }

  @Test
  public void addOneFlag() {
    String[] cla = { "-y" };
    argCheck.addFlag("y");
    argCheck.parse(cla);
    assertEquals("true", argCheck.getArgValue("y"));
  }

  @Test
  public void addOneFlagWithDescription() {
    String[] cla = { "-m" };
    argCheck.addFlag("m", "if true units will be given in liters, if false will be given in gallons");
    argCheck.parse(cla);
    assertEquals("true", argCheck.getArgValue("m"));
  }

  @Test
  public void addOneFlagSetValue() {
    String[] cla = { "-y", "7" };
    argCheck.addFlag("y");
    argCheck.addArg("length");
    argCheck.parse(cla);
    assertEquals("true", argCheck.getArgValue("y"));
    assertEquals("7", argCheck.getArgValue("length"));
  }

  @Test
  public void throwFlagError() {
    String[] cla = { "-y" };
    argCheck.addFlag("d");
    String msg = "argument y does not exist";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (ArgDoesNotExistException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void testMultipleBadFlags() {
    String[] cla = { "-yf" };
    argCheck.addFlag("y");
    argCheck.addFlag("d");
    String msg = "flag f does not exist";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (FlagDoesNotExistException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void settingMultipleFlags() {
    String[] cla = { "-yf" };
    argCheck.addFlag("y");
    argCheck.addFlag("f");
    argCheck.parse(cla);
    assertEquals("true", argCheck.getArgValue("y"));
    assertEquals("true", argCheck.getArgValue("f"));
  }

  @Test
  public void settingMultipleFlagsInDifferentOrder() {
    String[] cla = { "-fy" };
    argCheck.addFlag("y");
    argCheck.addFlag("f");
    argCheck.parse(cla);

    assertEquals("true", argCheck.getArgValue("y"));
    assertEquals("true", argCheck.getArgValue("f"));
  }

  @Test
  public void addingTheSameFlagTwiceError() {
    String[] cla = { "-f" };
    argCheck.addArg("yuh");
    argCheck.addArg("fun");
    argCheck.setArgShortFormName("yuh", "d");
    String msg = "The short form name d is already in use";
    try {
      argCheck.setArgShortFormName("fun", "d");
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (ShortFormNameException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }
  

  @Test
  public void usingAShortName() {
    String[] cla = { "-d" , "3" };
    argCheck.addArg("digits");
    argCheck.setArgShortFormName("digits", "d");
    argCheck.parse(cla);
    assertEquals("3", argCheck.getArgValue("digits"));
  }

  @Test
  public void usingAShortNameForTwoArguments() {
    String[] cla = { "-d", "3","-f","1" };
    argCheck.addArg("digits");
    argCheck.setArgShortFormName("digits", "d");
    argCheck.addArg("fucksGiven");
    argCheck.setArgShortFormName("fucksGiven", "f");
    argCheck.parse(cla);
    assertEquals("3", argCheck.getArgValue("digits"));
    assertEquals("1", argCheck.getArgValue("fucksGiven"));
  }

  //time to throw errors like it's the Oprah Show
  @Test
  public void TestArgumentDoesntExist() {
    String[] cla = { "7", "--myarg","myval","3","2" };
    argCheck.addArg("length");
    argCheck.addArg("width");
    argCheck.addArg("height");
   
    String msg = "argument " + "myarg" + " does not exist";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (ArgDoesNotExistException expected) {
      assertEquals(msg, expected.getMessage());
    }

  }
  




   

@Test
public void argGivenValueNotRestricted(){

    String[] cla = { "7"};  
    argCheck.addArg("length");
    argCheck.setArgRestricedValues("length", "7 8 9");
    argCheck.parse(cla);
    assertEquals("7", argCheck.getArgValue("length"));
  
}


@Test
public void argGivenValueNotInRestricted(){

    String[] cla = { "7"};  
    argCheck.addArg("length");
    argCheck.setArgRestricedValues("length", "5 8 9");
    String msg = "7 " + "is not an allowed value for" + " length";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (RestrictedValueException expected) {
      assertEquals(msg, expected.getMessage());
    }
  
}


@Test
public void argGivenValueNotInRestrictedOptional(){

  String[] cla = { "--optionalArgOne", "7","--optionalArgTwo", "7"  };
  argCheck.addOptArg("optionalArgOne", "6", Arg.DataType.FLOAT);
  argCheck.addOptArg("optionalArgTwo", "10", Arg.DataType.FLOAT);
  argCheck.setArgRestricedValues("optionalArgOne", "5 7 9");
  argCheck.setArgRestricedValues("optionalArgTwo", "5 8 9");
    String msg = "7 " + "is not an allowed value for " + "optionalArgTwo";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (RestrictedValueException expected) {
      assertEquals(msg, expected.getMessage());
    }
  
}


@Test
public void argRequiredButNotGiven(){

  String[] cla = {"7" };
  argCheck.addArg("length",Arg.DataType.FLOAT);
  argCheck.addOptArg("optionalArgTwo", "10", Arg.DataType.FLOAT);
  argCheck.setArgAsRequired("optionalArgTwo");
  
    String msg = "The argument(s) optionalArgTwo are required";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (RequiredArgException expected) {
      assertEquals(msg, expected.getMessage());
    }
  
}

@Test
public void argRequiredAndGiven(){

  String[] cla = {"7","--optionalArgTwo", "9" };
  argCheck.addArg("length",Arg.DataType.FLOAT);
  argCheck.addOptArg("optionalArgTwo", "10", Arg.DataType.FLOAT);
  argCheck.setArgAsRequired("optionalArgTwo");
  argCheck.parse(cla);
  assertEquals("7",argCheck.getArgValue("length"));
  assertEquals("9", argCheck.getArgValue("optionalArgTwo"));
  
  
}

}
