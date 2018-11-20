package physics.external;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class PhysicsBodyTest {

    @Test
    void createPhysicsBody() {
        PhysicsBody body1 = new PhysicsBody(50);

        PhysicsVector expectedAcc = new PhysicsVector(0,0);
        PhysicsVector expectedVel = new PhysicsVector(0,0);
        assertEquals(expectedAcc.getMagnitude(), body1.acceleration.getMagnitude());
        assertEquals(expectedAcc.getDirection(), body1.acceleration.getDirection());
        assertEquals(expectedVel.getMagnitude(), body1.velocity.getMagnitude());
        assertEquals(expectedVel.getDirection(), body1.velocity.getDirection());
    }

    @Test
    void applyForce() {
        PhysicsBody body2 = new PhysicsBody(50);

        PhysicsVector expectedAcc = new PhysicsVector(0.4,0);
        PhysicsVector expectedVel = new PhysicsVector(.05,0);

        body2.applyForce(new PhysicsVector(20, 0));
        assertEquals(expectedAcc.getMagnitude(), body2.acceleration.getMagnitude());
        assertEquals(expectedAcc.getDirection(), body2.acceleration.getDirection());
        assertEquals(expectedVel.getMagnitude(), body2.velocity.getMagnitude());
        assertEquals(expectedVel.getDirection(), body2.velocity.getDirection());
    }

    @Test
    void applyMultipleForces() {
        PhysicsBody body3 = new PhysicsBody(50);

        PhysicsVector expectedAcc1 = new PhysicsVector(0.4,0);
        PhysicsVector expectedVel1 = new PhysicsVector(0.05,0);
        PhysicsVector expectedAcc2 = new PhysicsVector(Math.sqrt(2) * 0.4,PI/4);
        PhysicsVector expectedVel2 = new PhysicsVector(Math.sqrt(2) * 0.05,PI/4);

        body3.applyForce(new PhysicsVector(20, 0));
        assertEquals(expectedAcc1.getMagnitude(), body3.acceleration.getMagnitude());
        assertEquals(expectedAcc1.getDirection(), body3.acceleration.getDirection());
        assertEquals(expectedVel1.getMagnitude(), body3.velocity.getMagnitude());
        assertEquals(expectedVel1.getDirection(), body3.velocity.getDirection());

        body3.applyForce(new PhysicsVector(20, PI/2));
        assertEquals(expectedAcc2.getMagnitude(), body3.acceleration.getMagnitude());
        assertEquals(expectedAcc2.getDirection(), body3.acceleration.getDirection());
        //assertEquals(expectedVel2.getMagnitude(), body3.velocity.getMagnitude());
        //assertEquals(expectedVel2.getDirection(), body3.velocity.getDirection());

    }
}