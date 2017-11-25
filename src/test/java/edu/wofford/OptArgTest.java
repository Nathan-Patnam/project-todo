package edu.wofford;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;



public class OptArgTest {
  private OptArg optArgNameOnly;

  @Before
  public final void setup() {
    optArgNameOnly = new OptArg("type", "ellipsoid");
    
}
@Test
public final void TestSetThenGetOptArgDefaultValue(){
    assertEquals("ellipsoid", optArgNameOnly.getValue());
} 

@Test
public final void TestGetOptArgDefaultDescription(){
    OptArg optArgNameDescriptionOnly =  new OptArg("type", "ellipsoid", "the type of shape you are calculating the volume for");
    assertEquals("the type of shape you are calculating the volume for", optArgNameDescriptionOnly.getDescription());
} 


@Test
public final void TestGetArgRequired(){
    assertEquals(false, optArgNameOnly.isArgRequired());
} 

@Test
public final void TestMakeArgRequired(){
    optArgNameOnly.makeArgRequired();
    assertEquals(true, optArgNameOnly.isArgRequired());
} 

@Test
public final void TestGetArgDatatype(){
    OptArg optArgEverything =  new OptArg("length", "3", Arg.DataType.FLOAT);
    assertEquals(Arg.DataType.FLOAT, optArgEverything.getDataType());
}

@Test
public final void TestCreateFlag(){
    OptArg flag =  new OptArg("metric", false , Arg.DataType.FLOAT);
    assertEquals("false", flag.getValue());
}

@Test
public final void TestCreateFlagWithDescription(){
    OptArg flag =  new OptArg("metric", false , Arg.DataType.FLOAT, "units that measurement and volume will be given in");
    assertEquals("units that measurement and volume will be given in", flag.getDescription());
}
}