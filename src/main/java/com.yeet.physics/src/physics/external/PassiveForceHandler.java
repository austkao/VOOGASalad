package physics.external;

import java.util.Map;

import static java.lang.Math.PI;

public class PassiveForceHandler {

    public static final double defaultGravityAcceleration = 200;
    public static final double defaultGravityDirection = PI/2;
    public static final double frictionCoefficient = 5;

    private Map<Integer, PhysicsObject> myObjects;

    public PassiveForceHandler(Map<Integer, PhysicsObject> objects) {
        this.myObjects = objects;
    }

    public void update() {
        for (PhysicsObject o : myObjects.values()) {
            if ((o.isPhysicsBody())|| (o.isPhysicsAttack())){//((o.isPhysicsBody() && !o.isGrounded()) || (o.isPhysicsAttack() && !o.isGrounded()))
                o.addCurrentForce(new PhysicsVector(Math.round(o.getMass() * defaultGravityAcceleration), defaultGravityDirection)); // always add gravity
                if(o.isGrounded){
                    o.addCurrentForce(new PhysicsVector(o.getMass()*defaultGravityAcceleration, -o.getXVelocity().getDirection()));
                }
                /*
                if (o.getId() == 1) {
                    //System.out.println("APPLYING GRAVITY");
                }
                */
            }
        }
    }
}
