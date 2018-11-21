package physics.external;

import java.util.List;

public class CollisionHandler {


    List<PhysicsObject> bodies;

    public CollisionHandler(List<Collision> collisions){
        for (Collision c : collisions) {
            PhysicsObject one, two;
            one = c.getCollider1();
            two = c.getCollider2();
            // body+attack
            if(one.isPhysicsBody() && two.isPhysicsAttack()){
                PhysicsVector force = new PhysicsVector()
                one.applyForce();
            }
            // attack+body
            if(one.isPhysicsAttack() && two.isPhysicsBody()){

            }
            // body+ground
            if(one.isPhysicsBody() && two.isPhysicsGround()){

            }
            // ground+body
            if(one.isPhysicsGround() && two.isPhysicsBody()){

            }
            // body+body (useless)
            if(one.isPhysicsBody() && two.isPhysicsBody()){

            }
            // body+body
            if(one.isPhysicsBody() && two.isPhysicsBody()){

            }
            // ground+attack (useless)
            if(one.isPhysicsGround() && two.isPhysicsAttack()){

            }
            // attack+ground
            if(one.isPhysicsAttack() && two.isPhysicsGround()){

            }
            // ground+ground
            if(one.isPhysicsGround() && two.isPhysicsGround()){

            }
            // attack+attack
            if(one.isPhysicsAttack() && two.isPhysicsAttack()){

            }
        }
    }





}
