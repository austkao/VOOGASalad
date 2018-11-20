package physics.external;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsSystemTest {

    @Test
    void createPhysicsBodies() {
        PhysicsSystem system = new PhysicsSystem();
        system.createPhysicsBodies(10);
        double expected = 0;

        for (int i = 0; i < 10; i++){
            assertEquals(expected, system.currentBodies.get(i).getAcceleration().getMagnitude());
            assertEquals(expected, system.currentBodies.get(i).getAcceleration().getDirection());
            assertEquals(expected, system.currentBodies.get(i).getVelocity().getMagnitude());
            assertEquals(expected, system.currentBodies.get(i).getVelocity().getDirection());
        }
    }
}