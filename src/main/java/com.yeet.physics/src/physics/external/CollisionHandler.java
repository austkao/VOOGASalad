package physics.external;

import java.util.List;

public class CollisionHandler {


    List<PhysicsObject> bodies;

    public CollisionHandler(List<Collision> collisions){
        for (Collision c : collisions) {
            // body+attack
            // body+ground
            // body+body (useless)
            // ground+attack (useless)
        }
    }





}
