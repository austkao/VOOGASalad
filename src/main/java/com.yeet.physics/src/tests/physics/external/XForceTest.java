package physics.external;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XForceTest {

    private double expected = 120;

    @Test
    public void getMagnitude() {
        Force XTest = new Force(120, 0);
        Force YTest = new Force(120, -90);
        assertEquals(XTest.magnitude, expected, 1);
        assertEquals(YTest.magnitude, expected, 1);
    }
}