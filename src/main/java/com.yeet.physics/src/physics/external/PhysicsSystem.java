package physics.external;

import java.util.ArrayList;
import java.util.List;


public class PhysicsSystem {


<<<<<<< HEAD
    public static final double defaultMass = 50;
    List<PhysicsBody> bodies = new ArrayList<>();
    private double gravityAcceleration;
    private double gravityDirection;

    PhysicsSystem(double gravityAcc, double gravityDir) {
        this.gravityAcceleration = gravityAcc;
        this.gravityDirection = gravityDir;
    }

    void updatePhysics() {
        applyAcceleration(bodies, gravityAcceleration); // always apply gravity
    }

    void addPhysicsBodies(int num) {
        int count = 0;
        while (count < num) {
            bodies.add(new PhysicsBody(defaultMass));
            count ++;
        }
    }

    private void applyAcceleration(List<PhysicsBody> bodies, double acceleration) {
        PhysicsVector gravity;
        for (PhysicsBody b : bodies) {
            gravity = new PhysicsVector(b.getMass()*acceleration, gravityDirection);
            b.applyForce(gravity);
        }
    }

    List<PhysicsBody> getBodies() {
        return this.bodies;
=======
    public void update(){

>>>>>>> 3a63c53e08738dca3a6ef60ba4ac594f87251060
    }
}
