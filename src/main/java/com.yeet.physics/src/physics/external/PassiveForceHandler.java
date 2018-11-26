package physics.external;

import java.util.List;

import static java.lang.Math.PI;

public class PassiveForceHandler {

    public static final double defaultGravityAcceleration = 9.8;
    public static final double defaultGravityDirection = PI/2;

    private List<PhysicsObject> myObjects;

    PassiveForceHandler(List<PhysicsObject> objects) {
        this.myObjects = objects;
    }

    void update() {
        for (PhysicsObject o : myObjects) {
            if (o.isPhysicsBody() || o.isPhysicsAttack()) {
                o.addCurrentForce(new PhysicsVector(o.getMass() * defaultGravityAcceleration, defaultGravityDirection)); // always add gravity
            }
        }
    }
}
