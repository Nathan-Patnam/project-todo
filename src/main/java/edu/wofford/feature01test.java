import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class feature01test{
    private feature01 argCheck;

    @Before
    public final void setup(){
        argCheck = new feature01();
    }


    @Test
    public final void testMissingArgument(){
        assertEquals(false, argCheck.testMissingArgument());
    }

    

}