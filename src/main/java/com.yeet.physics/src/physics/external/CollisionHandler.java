package physics.external;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler {

    public static double defaultAttackMagnitude = 10;

    private List<Collision> myCollisions;
    private List<Integer> groundCollisions = new ArrayList<>();
    private List<List<Integer>> attackCollisions = new ArrayList<>();

    public CollisionHandler(List<Collision> collisions){
        this.myCollisions = collisions;
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
            // attack+body
            if(one.isPhysicsAttack() && two.isPhysicsBody()){
                PhysicsVector force = new PhysicsVector(defaultAttackMagnitude, one.getDirection());
                two.addCurrentForce(force);
                List<Integer> collisions = new ArrayList<>();
                collisions.add(two.getId(), one.getId());
                attackCollisions.add(collisions);
            }
            // body+ground
            if(one.isPhysicsBody() && two.isPhysicsGround()){
//                PhysicsVector blankVector = new PhysicsVector(0,0);
//                PhysicsVector oppositeGravity = new PhysicsVector(one.getMass()*9.8,-PI/2);
//                one.myVelocity = blankVector;
//                one.myAcceleration = blankVector;
//                one.clearCurrentForces();
//                one.addCurrentForce(oppositeGravity);
//                one.getMyCoordinateBody().setPos(one.getMyCoordinateBody().getPos().getX(), two.getMyCoordinateBody().getPos().getY() - one.getMyCoordinateBody().getDims().getSizeY());
                one.setGrounded(true);
                groundCollisions.add(one.getId());
                if (one.getId() == 1)
                    System.out.println("COLLIDING WITH GROUND");

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
