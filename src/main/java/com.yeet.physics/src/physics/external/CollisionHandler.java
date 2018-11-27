package physics.external;

import java.util.List;

import static java.lang.Math.PI;

public class CollisionHandler {

    public static double defaultAttackMagnitude = 10;

    private List<Collision> myCollisions;

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
            }
            // attack+body
            if(one.isPhysicsAttack() && two.isPhysicsBody()){
                PhysicsVector force = new PhysicsVector(defaultAttackMagnitude, one.getDirection());
                two.addCurrentForce(force);
            }
            // body+ground
            if(one.isPhysicsBody() && two.isPhysicsGround()){
                PhysicsVector balancingForce = new PhysicsVector(one.getMass()*9.8, -PI/2);
                one.addCurrentForce(balancingForce);
            }
            // ground+body
            if(one.isPhysicsGround() && two.isPhysicsBody()){
                PhysicsVector balancingForce = new PhysicsVector(two.getMass()*9.8, -PI/2);
                two.addCurrentForce(balancingForce);
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



}
