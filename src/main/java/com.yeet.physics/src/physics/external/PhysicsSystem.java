package physics.external;

import java.util.ArrayList;
import java.util.List;


public class PhysicsSystem {

    public static final double defaultMass = 50;
    List<PhysicsObject> bodies = new ArrayList<>();
    private double gravityAcceleration;
    private double gravityDirection;

    PhysicsSystem(double gravityAcc, double gravityDir) {
        this.gravityAcceleration = gravityAcc;
        this.gravityDirection = gravityDir;
    }

    void update() {
        CollisionDetector detector = new CollisionDetector(bodies);
        List<Collision> collisions = new ArrayList<>(detector.detectCollisions(bodies));
        CollisionHandler handler = new CollisionHandler(collisions);
        applyAcceleration(bodies, gravityAcceleration); // always apply gravity
    }

    void addPhysicsBodies(int num) {
        int count = 0;
        while (count < num) {
            bodies.add(new PhysicsBody(defaultMass, new Coordinate(0,0), new Dimensions(0,0)));
            count ++;
        }
    }

    private void applyAcceleration(List<PhysicsObject> bodies, double acceleration) {
        PhysicsVector gravity;
        for (PhysicsObject b : bodies) {
            gravity = new PhysicsVector(b.getMass()*acceleration, gravityDirection);
            b.applyForce(gravity);
        }
    }

    List<PhysicsObject> getBodies() {
        return this.bodies;
    }
}
