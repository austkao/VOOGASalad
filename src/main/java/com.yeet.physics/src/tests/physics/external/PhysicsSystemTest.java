package physics.external;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class PhysicsSystemTest {

    @Test
    void createPhysicsBodies() {
        PhysicsSystem testSystem = new PhysicsSystem();
        List<PhysicsBody> testBodies = testSystem.createPhysicsBodies(10);
        double expected = 0;

        for (int i = 0; i < 10; i++){
            assertEquals(expected, testBodies.get(i).getAcceleration().getMagnitude());
            assertEquals(expected, testBodies.get(i).getAcceleration().getDirection());
            assertEquals(expected, testBodies.get(i).getVelocity().getMagnitude());
            assertEquals(expected, testBodies.get(i).getVelocity().getDirection());
        }
    }

    @Test
    void applyGravity() {
        PhysicsSystem testSystem = new PhysicsSystem();
        List<PhysicsBody> testBodies = testSystem.createPhysicsBodies(10);
        double expectedAccMag = 9.8;
        double expectedVelMag = 9.8*0.125;
        double expectedDir = PI/2;

        testSystem.applyGravity(testBodies);
        for (PhysicsBody b : testBodies) {
            assertEquals(expectedAccMag, b.getAcceleration().getMagnitude());
            assertEquals(expectedVelMag, b.getVelocity().getMagnitude());
            assertEquals(expectedDir, b.getAcceleration().getDirection());
            assertEquals(expectedDir, b.getVelocity().getDirection());
        }
    }
}