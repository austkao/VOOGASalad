package physics.external;

import java.util.Map;

import static java.lang.Math.PI;

public class PassiveForceHandler {

    public static final double defaultGravityAcceleration = 9.8;
    public static final double defaultGravityDirection = PI/2;

    private Map<Integer, PhysicsObject> myObjects;

    public PassiveForceHandler(Map<Integer, PhysicsObject> objects) {
        this.myObjects = objects;
    }

    public void update() {
        for (PhysicsObject o : myObjects.values()) {
            if (o.isPhysicsBody() || o.isPhysicsAttack()) {
                o.addCurrentForce(new PhysicsVector(o.getMass() * defaultGravityAcceleration, defaultGravityDirection)); // always add gravity
            }
        }
    }
}
