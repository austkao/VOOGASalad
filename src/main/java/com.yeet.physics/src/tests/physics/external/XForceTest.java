package physics.external;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XForceTest {

    private double expected = 120;

    @Test
    public void getMagnitude() {
        Force XTest = new XForce(120);
        Force YTest = new YForce(120);
        assertEquals(XTest.myMagnitude, expected, 1);
        assertEquals(YTest.myMagnitude, expected, 1);

    }
}