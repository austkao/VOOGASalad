package physics.external;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;

public class PhysicsSystem {

    public static final double defaultMass = 50;
    public static final double gravityDirection = PI/2;
    public static final double gravityAcceleration = 9.8;

    PhysicsSystem() {

    }

    List<PhysicsBody> createPhysicsBodies(int num) {
        List<PhysicsBody> newBodies = new ArrayList<>();
        int count = 0;
        while (count < num) {
            newBodies.add(new PhysicsBody(defaultMass));
            count ++;
        }
        return newBodies;
    }

    void applyGravity(List<PhysicsBody> bodies){
        PhysicsVector gravity;
        for (PhysicsBody b : bodies) {
            gravity = new PhysicsVector(b.getMass()*gravityAcceleration, gravityDirection);
            b.applyForce(gravity);
        }
    }
}
