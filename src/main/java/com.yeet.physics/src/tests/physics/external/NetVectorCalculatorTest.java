package physics.external;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class NetForceCalculatorTest {

    List<Vector> forces = new ArrayList<>();

    @Test
    void getNetForce() {
        forces.add(new Force(100, 0));
        forces.add(new Force(100, PI/2));
        NetVectorCalculator calc = new NetVectorCalculator(forces);
        Force expected = new Force(Math.sqrt(20000), PI/4);

        assertEquals(expected.getMagnitude(), calc.getNetForce().getMagnitude());
        assertEquals(expected.getDirection(), calc.getNetForce().getDirection());
        System.out.println("Result Magnitude: " + calc.getNetForce().getMagnitude());
        System.out.println("Result Direction: " + calc.getNetForce().getDirection());
    }

    @Test
    void manyForces() {
        forces.add(new Force(100, PI/2));
        forces.add(new Force(100, -PI/2));
        forces.add(new Force(100, PI/2));
        forces.add(new Force(100, -PI/2));
        forces.add(new Force(200, 0));
        forces.add(new Force(100, 1*PI));
        NetVectorCalculator calc = new NetVectorCalculator(forces);
        Force expected = new Force(100, 0);

        assertEquals(expected.getMagnitude(), calc.getNetForce().getMagnitude(), 1);
        assertEquals(expected.getDirection(), calc.getNetForce().getDirection(), 1);
        System.out.println("Result Magnitude: " + calc.getNetForce().getMagnitude());
        System.out.println("Result Direction: " + calc.getNetForce().getDirection());
    }
}