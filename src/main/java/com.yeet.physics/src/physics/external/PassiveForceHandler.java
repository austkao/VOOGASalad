package physics.external;

import java.util.Map;

import static java.lang.Math.PI;

public class PassiveForceHandler {

    public static final double DEFAULT_GRAVITY_ACCELERATION = 600;
    public static final double DEFAULT_GRAVITY_DIRECTION = PI / 2;
    public static final double TERMINAL_VELOCITY = 400;
    public static final double frictionCoefficient = .5;
    public static final double timeOfFrame = 0.016666666; // Assume each frame is 1/8 of a sec


    private Map<Integer, PhysicsObject> myObjects;

    public PassiveForceHandler(Map<Integer, PhysicsObject> objects) {
        this.myObjects = objects;
    }

    public void update() {
        for (PhysicsObject o : myObjects.values()) {
            if ((o.isPhysicsBody() || o.isPhysicsAttack()) && (o.getYVelocity().getMagnitude() < TERMINAL_VELOCITY)) {
                o.addCurrentForce(new PhysicsVector(Math.round(o.getMass() * DEFAULT_GRAVITY_ACCELERATION), DEFAULT_GRAVITY_DIRECTION)); // always add gravity


                /*if (o.getId() == 1) {
                    //System.out.println("APPLYING GRAVITY");
                    if (o.isGrounded && (Math.abs(o.getXVelocity().getMagnitude()) > 13)) {
                        o.addCurrentForce(new PhysicsVector((int) Math.signum(o.getXVelocity().getMagnitude()) * (-1) * o.getMass() * DEFAULT_GRAVITY_ACCELERATION * frictionCoefficient, o.getXVelocity().getDirection()));
                    } else if (o.isGrounded()) {
                        o.setVelocity(new PhysicsVector(0, 0));
                        //o.addCurrentForce(new PhysicsVector(o.getXVelocity().getMagnitude()*o.getMass()/timeOfFrame, Math.signum(o.getXVelocity().getMagnitude()) * (-1) * o.getXVelocity().getDirection()));
                    }
                }*/
            }
        }
    }
}
