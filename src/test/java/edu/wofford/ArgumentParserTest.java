package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;

public class ArgumentParserTest {
  private ArgumentParser argCheck;
  private ArgumentParser arhCheckSimple;

  @Before
  public final void setup() {
    argCheck = new ArgumentParser("VolumeCalculator", "Calculate the volume of a box.");
    arhCheckSimple=new ArgumentParser("VolumeCalculator");

  }

  @Test
  public final void setProgramName() {
    arhCheckSimple.setProgramName("Calculate the volume of a box.");
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
    assertEquals("side of a box", argCheck.getArgumentDescription("length"));
  }

  @Test
  public final void testGetNumArguments() {
    assertEquals(0, argCheck.getNumberArguments());
  }

  @Test
  public final void testAddArgument() {
    assertEquals(0, argCheck.getNumberArguments());
    argCheck.addArg("length");
    assertEquals(1, argCheck.getNumberArguments());
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
    assertEquals("5", argCheck.getArgumentValue("length"));
  }

  @Test
  public void addDataType() {
    argCheck.addArg("length");
    argCheck.getArgument("length").setDataType(Argument.DataType.FLOAT);
    assertEquals(Argument.DataType.FLOAT, argCheck.getArgumentDataType("length"));
  }

  @Test
  public void TestgetDataType() {
    argCheck.addArg("Length", "Length of the box.", Argument.DataType.FLOAT);
    assertEquals("float", argCheck.getArgumentDataTypeString("Length"));
  }

  @Test
  public void TestGetDefaultStringType() {
    argCheck.addArg("Length");
    assertEquals(Argument.DataType.STRING, argCheck.getArgumentDataType("Length"));
  }

  @Test
  public void Test2ParamConstructor() {
    argCheck.addArg("Length", Argument.DataType.FLOAT);
    assertEquals(Argument.DataType.FLOAT, argCheck.getArgumentDataType("Length"));
  }

  @Test
  public void TestValidDataTypes() {
    String[] cla = { "3", "something", "3.0" };
    argCheck.addArg("length", "the length of the box", Argument.DataType.FLOAT);
    argCheck.addArg("width", "the width of the box", Argument.DataType.FLOAT);
    argCheck.addArg("height", "the height of the box", Argument.DataType.FLOAT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument width: invalid float value: something";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (HelpException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestMultipleBadDataTypes() {
    String[] cla = { "yup", "something", "one" };
    argCheck.addArg("length", "the length of the box", Argument.DataType.FLOAT);
    argCheck.addArg("width", "the width of the box", Argument.DataType.BOOLEAN);
    argCheck.addArg("height", "the height of the box", Argument.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument length: invalid float value: yup";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (HelpException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }


  @Test
  public void TestMultipleBadDataTypesBoolean() {
    String[] cla = { "yup", "something", "one" };
    argCheck.addArg("length", "the length of the box", Argument.DataType.BOOLEAN);
    argCheck.addArg("width", "the width of the box", Argument.DataType.BOOLEAN);
    argCheck.addArg("height", "the height of the box", Argument.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument length: invalid boolean value: yup";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (HelpException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestMultipleBadDataTypesInt() {
    String[] cla = { "7", "something", "one" };
    argCheck.addArg("length", "the length of the box", Argument.DataType.INT);
    argCheck.addArg("width", "the width of the box", Argument.DataType.INT);
    argCheck.addArg("height", "the height of the box", Argument.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument width: invalid int value: something";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (HelpException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void TestBadArguments() {
    String[] cla = { "yup", "something", "one" };
    argCheck.addArg("length", "the length of the box", Argument.DataType.FLOAT);
    argCheck.addArg("width", "the width of the box", Argument.DataType.BOOLEAN);
    argCheck.addArg("height", "the height of the box", Argument.DataType.INT);

    String msg = "usage: java VolumeCalculator length width height\nVolumeCalculator.java: error: argument length: invalid float value: yup";
    try {
      argCheck.parse(cla);
      fail("Should have thrown HelpException but did not!");
    } catch (HelpException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void testOptionalArgumentDefault() {
    String[] cla = { };
    argCheck.addOptionalArgument("optionalArgOne", "optionalArgOneDefaultValue");
    argCheck.parse(cla);

    assertEquals("optionalArgOneDefaultValue", argCheck.getArgumentValue("optionalArgOne"));
  }

  @Test
  public void testOptionalArgumentSetValue() {
    String[] cla = { "--optionalArgOne", "8" };
    argCheck.addOptionalArgument("optionalArgOne", "optionalArgOneDefaultValue");
    argCheck.parse(cla);

    assertEquals("8", argCheck.getArgumentValue("optionalArgOne"));
  }

  @Test
  public void testOptionalArgument() {
    String[] cla = { "7" };
    argCheck.addArg("length");
    argCheck.addOptionalArgument("optionalArgOne", "optionalArgOneDefaultValue");
    argCheck.parse(cla);

    assertEquals("7", argCheck.getArgumentValue("length"));
    assertEquals("optionalArgOneDefaultValue", argCheck.getArgumentValue("optionalArgOne"));
  }

  @Test
  public void testOptionalArgumentWithDifferentConstructor() {
    String[] cla = { "7"};
    argCheck.addArg("length");
    argCheck.addOptionalArgument("optionalArgOne", "optionalArgOneDefaultValue", "this is an optional argument");
    argCheck.parse(cla);

    assertEquals("7", argCheck.getArgumentValue("length"));
    assertEquals("optionalArgOneDefaultValue", argCheck.getArgumentValue("optionalArgOne"));
    assertEquals("this is an optional argument", argCheck.getArgumentDescription("optionalArgOne"));
  }

  @Test
  public void getDataTypeOptionaArgument() {
    String[] cla = { "rip" };
    argCheck.addArg("length");
    argCheck.addOptionalArgument("optionalArgOne", "optionalArgOneDefaultValue", Argument.DataType.STRING);
    argCheck.parse(cla);

    assertEquals(Argument.DataType.STRING, argCheck.getArgumentDataType("optionalArgOne"));

  }

  @Test
  public void addOptionalArgumentConstructorTests() {
    String[] cla = { "rip"};
    argCheck.addArg("length");
    argCheck.addOptionalArgument("optionalArgOne", "optionalArgOneDefaultValue", Argument.DataType.STRING,
        "my funeral");
    argCheck.addOptionalArgument("type", "typevalue", Argument.DataType.STRING, "my funeral");
    argCheck.parse(cla);
    assertEquals("optionalArgOneDefaultValue", argCheck.getArgumentValue("optionalArgOne"));
    assertEquals("typevalue", argCheck.getArgumentValue("type"));
  

  }

  @Test
  public void checkDifferentArgumentDataTypes(){
    String[]cla={"true","7"};
    argCheck.addArg("isNumber", Argument.DataType.BOOLEAN);
    argCheck.addArg("length", Argument.DataType.FLOAT);
    assertEquals( Argument.DataType.BOOLEAN, argCheck.getArgumentDataType("isNumber"));
    assertEquals(Argument.DataType.FLOAT, argCheck.getArgumentDataType("length"));

  }


  @Test
  public void addOneFlag(){
    String[]cla={"-y"};
    argCheck.addFlag("y");
    argCheck.parse(cla);
    assertEquals("true", argCheck.getArgumentValue("y"));
  }

  @Test
  public void throwFlagError(){
    String[]cla={"-y"};
    argCheck.addFlag("d");
    String msg ="argument y does not exist";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (IllegalArgumentException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }

  @Test
  public void testMultipleBadFlags(){
    String[]cla={"-yf"};
    argCheck.addFlag("y");
    argCheck.addFlag("d");
    String msg ="flag f does not exist";
    try {
      argCheck.parse(cla);
      fail("Should have thrown IllegalArgumentException but did not!");
    } catch (IllegalArgumentException expected) {
      assertEquals(msg, expected.getMessage());
    }
  }
}

