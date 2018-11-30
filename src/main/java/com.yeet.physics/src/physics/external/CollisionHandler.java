package physics.external;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;

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
                one.myVelocity = new PhysicsVector(0,0);
                one.myAcceleration = new PhysicsVector(0,0);
                PhysicsVector balancingForce = new PhysicsVector(one.getMass()*9.8, -PI/2);
                one.addCurrentForce(balancingForce);
                groundCollisions.add(one.getId());
                System.out.println("HITTING GROUND");
            }
            // ground+body
//            if(one.isPhysicsGround() && two.isPhysicsBody()){
//                PhysicsVector balancingForce = new PhysicsVector(two.getMass()*9.8, -PI/2);
//                two.addCurrentForce(balancingForce);
//                groundCollisions.add(two.getId());
//                System.out.println("HITTING GROUND");
//            }
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
