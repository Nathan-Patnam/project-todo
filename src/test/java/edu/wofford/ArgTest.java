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
public final void TestSetThenGetArgDescription(){
    argOnlyName.setDescription("length of the box");
    assertEquals("length of the box", argOnlyName.getDescription());
} 

@Test
public final void TestGetArgDescription(){
    assertEquals("width of the box", argOnlyNameDescription.getDescription());
} 



@Test
public final void TestDefaultArgDataType(){
    assertEquals(Arg.DataType.STRING, argOnlyName.getDataType());
} 

@Test
public final void TestSetThenGetArgDataType(){
    argOnlyName.setDataType(Arg.DataType.INT);
    assertEquals(Arg.DataType.INT, argOnlyName.getDataType());
} 

@Test
public final void TestGetArgDataType(){
    assertEquals(Arg.DataType.FLOAT, regArg.getDataType());
}


@Test
public final void TestSetThenGetArgValue(){
    regArg.setValue("3");
    assertEquals("3", regArg.getValue());
}

@Test
public final void TestSetThenGetArgShortName(){
    //would have set it to h, but that's reserved for help message
    regArg.setShortFormName("w");
    assertEquals("w", regArg.getShortFormName());
}
@Test
public final void TestSetThenGetArgDefaultRestrictedValues(){
    assertEquals("", regArg.getRestrictedValuesString());
}

@Test
public final void TestSetThenGetArgRestrictedValues(){
    regArg.setRestrictedValues("1 2 3 4");
    assertEquals("1 2 3 4", regArg.getRestrictedValuesString());
}

@Test
public final void TestSetThenGetArgRestrictedValuesAsHashSet(){
    HashSet<String> testHasMap = new HashSet<>();
    testHasMap.add("1");
    testHasMap.add("2");
    testHasMap.add("3");
    testHasMap.add("4");
    
    regArg.setRestrictedValues("1 2 3 4");
    assertEquals(true, regArg.getRestrictedValues().equals(testHasMap) );
}

@Test
public final void TestSetThenGetEachTypeArgDataType(){
    assertEquals("float", regArg.getDataType().toString());
    regArg.setDataType(Arg.DataType.STRING);
    assertEquals("string", regArg.getDataType().toString());
    regArg.setDataType(Arg.DataType.BOOLEAN);
    assertEquals("boolean", regArg.getDataType().toString());
    regArg.setDataType(Arg.DataType.INT);
    assertEquals("int", regArg.getDataType().toString());
}

}