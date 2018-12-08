package physics.external;

import java.util.Map;

import static java.lang.Math.PI;

public class PassiveForceHandler {

    public static final double defaultGravityAcceleration = 600;
    public static final double defaultGravityDirection = PI / 2;
    public static final double frictionCoefficient = .5;
    public static final double timeOfFrame = 0.016666666; // Assume each frame is 1/8 of a sec


    private Map<Integer, PhysicsObject> myObjects;

    public PassiveForceHandler(Map<Integer, PhysicsObject> objects) {
        this.myObjects = objects;
    }

    public void update() {
        for (PhysicsObject o : myObjects.values()) {
            if ((o.isPhysicsBody() || o.isPhysicsAttack())) {
                o.addCurrentForce(new PhysicsVector(Math.round(o.getMass() * defaultGravityAcceleration), defaultGravityDirection)); // always add gravity
            }
        }
    }
}
