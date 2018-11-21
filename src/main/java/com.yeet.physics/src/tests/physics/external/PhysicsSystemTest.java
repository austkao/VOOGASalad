package physics.external;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class PhysicsSystemTest {

    public static final double defaultGravityAcceleration = 9.8;
    public static final double defaultGravityDirection = PI/2;

    @Test
    void createPhysicsBodies() {
        PhysicsSystem testSystem = new PhysicsSystem(defaultGravityAcceleration, defaultGravityDirection);
        testSystem.addPhysicsBodies(10);
        double expected = 0;

        for (int i = 0; i < 10; i++){
            assertEquals(expected, testSystem.getGameObjects().get(i).getAcceleration().getMagnitude());
            assertEquals(expected, testSystem.getGameObjects().get(i).getAcceleration().getDirection());
            assertEquals(expected, testSystem.getGameObjects().get(i).getVelocity().getMagnitude());
            assertEquals(expected, testSystem.getGameObjects().get(i).getVelocity().getDirection());
        }
    }

    @Test
    void updatePhysics() {
        PhysicsSystem testSystem = new PhysicsSystem(defaultGravityAcceleration, defaultGravityDirection);
        testSystem.addPhysicsBodies(10);
        double expectedAccMag = 9.8;
        double expectedVelMag = 9.8*0.125;
        double expectedDir = PI/2;

        testSystem.update();
        for (PhysicsObject b : testSystem.getGameObjects()) {
            assertEquals(expectedAccMag, b.getAcceleration().getMagnitude());
            assertEquals(expectedVelMag, b.getVelocity().getMagnitude());
            assertEquals(expectedDir, b.getAcceleration().getDirection());
            assertEquals(expectedDir, b.getVelocity().getDirection());
        }
    }
}