package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;



public class ArgTest {
  private Arg argOnlyName;
  private Arg argOnlyNameDescription;
  private Arg regArg;

  @Before
  public final void setup() {
    argOnlyName = new Arg("length");
    argOnlyNameDescription = new Arg("width", "width of the box");
    regArg=new Arg("height", "height of the box", Arg.DataType.FLOAT);

}


@Test
public final void testSetArgDescription(){
    argOnlyName.setDescription("length of the box");
    assertEquals("length of the box", argOnlyName.getDescription());
} 

@Test
public final void testArgWithDescriptionConstructor(){
    assertEquals("width of the box", argOnlyNameDescription.getDescription());
} 



@Test
public final void testDefaultArgDataType(){
    assertEquals(Arg.DataType.STRING, argOnlyName.getDataType());
} 

@Test
public final void testSetArgDataType(){
    argOnlyName.setDataType(Arg.DataType.INT);
    assertEquals(Arg.DataType.INT, argOnlyName.getDataType());
} 

@Test
public final void testArgWithDataTypeConstructor(){
    assertEquals(Arg.DataType.FLOAT, regArg.getDataType());
}


@Test
public final void testSetArgValue(){
    regArg.setValue("3");
    assertEquals("3", regArg.getValue());
}

@Test
public final void testSetArgShortName(){
    regArg.setShortFormName("w");
    assertEquals("w", regArg.getShortFormName());
}
@Test
public final void testArgDefaultRestrictedValues(){
    assertEquals("", regArg.getRestrictedValuesString());
}

@Test
public final void testSetArgRestrictedValues(){
    regArg.setRestrictedValues("1 2 3 4");
    assertEquals("1 2 3 4", regArg.getRestrictedValuesString());
}

@Test
public final void testSetArgRestrictedValuesAsHashSet(){
    HashSet<String> testHasMap = new HashSet<>();
    testHasMap.add("1");
    testHasMap.add("2");
    testHasMap.add("3");
    testHasMap.add("4");
    
    regArg.setRestrictedValues("1 2 3 4");
    assertEquals(true, regArg.getRestrictedValues().equals(testHasMap) );
}

@Test
public final void testSetEachTypeArgDataType(){
    assertEquals("float", regArg.getDataType().toString());
    regArg.setDataType(Arg.DataType.STRING);
    assertEquals("string", regArg.getDataType().toString());
    regArg.setDataType(Arg.DataType.BOOLEAN);
    assertEquals("boolean", regArg.getDataType().toString());
    regArg.setDataType(Arg.DataType.INT);
    assertEquals("int", regArg.getDataType().toString());
}


@Test
public final void testBooleanValue() {
    Arg a = new Arg("something", "", Arg.DataType.BOOLEAN);
    a.setValue("true");
    assertTrue(a.getVal());
}


@Test
public final void testFloatValue() {
    Arg a = new Arg("something", "", Arg.DataType.FLOAT);
    a.setValue("7.0");
    Float f = a.getVal();
    assertEquals( 7.0 , f, 0.0001);
}



@Test
public final void testIntegerValue() {
    Arg a = new Arg("something", "", Arg.DataType.INT);
    a.setValue("7");
    int i = a.getVal();
    assertEquals(7,i);
}

@Test
public final void testStringValue() {
    Arg a = new Arg("something", "", Arg.DataType.STRING);
    a.setValue("something");
    assertEquals("something",a.getVal());
}



}