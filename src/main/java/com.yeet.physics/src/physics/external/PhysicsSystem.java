package physics.external;

import java.util.ArrayList;
import java.util.List;


public class PhysicsSystem {

    public static final double defaultMass = 50;

    private int numBodies;
    private double gravityAcceleration;
    private double gravityDirection;

    PhysicsSystem(int numBods, double gravityAcc, double gravityDir) {
        this.numBodies = numBods;
        this.gravityAcceleration = gravityAcc;
        this.gravityDirection = gravityDir;
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
