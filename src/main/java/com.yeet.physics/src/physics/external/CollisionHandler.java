package physics.external;

import java.util.*;

import static physics.external.PassiveForceHandler.DEFAULT_GRAVITY_ACCELERATION;

public class CollisionHandler {

    public static double defaultAttackMagnitude = 10;
    public static final double timeOfFrame = 0.016666666; // Assume each frame is 1/8 of a sec
    public static final double frictionCoefficient = .5;

    private List<Collision> myCollisions;
    private List<Integer> groundCollisions = new ArrayList<>();
    private List<List<Integer>> attackCollisions = new ArrayList<>();

    public CollisionHandler(List<Collision> collisions){
        Iterator<Collision> colIter = collisions.iterator();
        Set<PhysicsObject> groundCols = new HashSet<>();
        this.myCollisions = new ArrayList<>();
        //Filter collisions so that only one ground collides with one body
        while(colIter.hasNext()){
            Collision col = colIter.next();
            PhysicsObject one = col.getCollider1();
            PhysicsObject two = col.getCollider2();
            if(one.isPhysicsBody() && two.isPhysicsGround()){
                if(!groundCols.contains(one)){
                    groundCols.add(one);
                    this.myCollisions.add(col);
                }
            }
        }

    }

    public void update() {
        for (Collision c : myCollisions) {
            PhysicsObject one, two;
            one = c.getCollider1();
            two = c.getCollider2();
            // body+attack
            if(one.isPhysicsBody() && two.isPhysicsAttack()){
                PhysicsVector force = new PhysicsVector(defaultAttackMagnitude, two.getDirection());
                one.addCurrentForce(force);
                List<Integer> collisions = new ArrayList<>();
                collisions.add(one.getId(), two.getId());
                attackCollisions.add(collisions);
            }
            // body+ground
            if(one.isPhysicsBody() && two.isPhysicsGround()){
                one.setGrounded(true);
                double bodyVelocity = one.getYVelocity().getMagnitude();
                double bodyMass = one.getMass();
                PhysicsVector upwardForce = new PhysicsVector(Math.round(bodyMass*bodyVelocity/(timeOfFrame)), -Math.PI/2);
                one.addCurrentForce(upwardForce);
                PhysicsVector gravityOpposition = new PhysicsVector(Math.round(one.getMass() * DEFAULT_GRAVITY_ACCELERATION), -Math.PI/2);
                one.addCurrentForce(gravityOpposition);
                if(Math.abs(one.getXVelocity().getMagnitude()) > 10) { //Should we apply kinetic friction?
                    PhysicsVector friction;
                    if(one.getXVelocity().getMagnitude() > 0) {
                        friction = new PhysicsVector((int) -one.getMass() * DEFAULT_GRAVITY_ACCELERATION * frictionCoefficient, 0);
                    }else{
                        friction = new PhysicsVector((int) one.getMass() * DEFAULT_GRAVITY_ACCELERATION * frictionCoefficient, 0);
                    }
                    one.addCurrentForce(friction);
                }else{
                    PhysicsVector staticFriction;
                    bodyVelocity = one.getXVelocity().getMagnitude();
                    staticFriction = new PhysicsVector(-bodyMass*bodyVelocity/timeOfFrame, 0);
                    one.addCurrentForce(staticFriction);
                }
                groundCollisions.add(one.getId());
            }
            // body+body (do nothing)
            if(one.isPhysicsBody() && two.isPhysicsBody()){

            }
            // body+body (do nothing)
            if(one.isPhysicsBody() && two.isPhysicsBody()){

            }
            // ground+attack ((do nothing)
            if(one.isPhysicsGround() && two.isPhysicsAttack()){

            }
            // attack+ground (do nothing)
            if(one.isPhysicsAttack() && two.isPhysicsGround()){

            }
            // ground+ground (do nothing)
            if(one.isPhysicsGround() && two.isPhysicsGround()){

            }
            // attack+attack (do nothing)
            if(one.isPhysicsAttack() && two.isPhysicsAttack()){

            }
        }
    }

    public List<Integer> getGroundCollisions() {
        return groundCollisions;
    }

    public List<List<Integer>> getAttackCollisions() {
        return attackCollisions;
    }



}
