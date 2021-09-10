import static org.junit.Assert.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlinkTest {

    @Test
    public void testIsSameNumber_100(){
        int a = 100;
        int b = 100;
        boolean expected = true;
        assertEquals(expected,Flik.isSameNumber(a, b));
    }

    @Test
    public void testIsSameNumber_200(){
        int a = 200;
        int b = 200;
        boolean expected = true;
        assertEquals(expected,Flik.isSameNumber(a, b));
    }
}
