package physics.external;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsBodyTest {

    private PhysicsBody body = new PhysicsBody(50);

    @Test
    void createPhysicsBody() {
        PhysicsVector expectedAcc = new PhysicsVector(0,0);
        PhysicsVector expectedVel = new PhysicsVector(0,0);
        assertEquals(expectedAcc.magnitude, body.acceleration.magnitude);
        assertEquals(expectedVel.magnitude, body.velocity.magnitude);
    }

    @Test
    void applyForce() {
        PhysicsVector expectedAcc = new PhysicsVector(0.4,0);
        PhysicsVector expectedVel = new PhysicsVector(20,0);
        body.applyForce(new PhysicsVector(20, 0));
        assertEquals(expectedAcc.getMagnitude(), body.acceleration.getMagnitude());
        assertEquals(expectedVel.getMagnitude(), body.velocity.magnitude);
    }
}