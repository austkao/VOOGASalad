package physics.external;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsBodyTest {

    private PhysicsBody body = new PhysicsBody(50);

    @Test
    void createPhysicsBody() {
        Acceleration expectedAcc = new Acceleration(0,0);
        Velocity expectedVel = new Velocity(0,0);
        assertEquals(body.acceleration.magnitude, expectedAcc.magnitude);
        assertEquals(body.velocity.magnitude, expectedVel.magnitude);
    }

    @Test
    void applyForce() {
        Acceleration expectedAcc = new Acceleration(20,0);
        Velocity expectedVel = new Velocity(20,0);
        body.applyForce(new Force(20, 0));
        assertEquals(body.acceleration.magnitude, expectedAcc.magnitude);
        assertEquals(body.velocity.magnitude, expectedVel.magnitude);
    }
}