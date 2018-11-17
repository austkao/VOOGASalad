package physics.external;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector {

    private List<PhysicsBody> bodies;

    public CollisionDetector(List<PhysicsBody> bodies){
        this.bodies = bodies;
    }


    public List<PhysicsBody> detectCollisions(List<PhysicsBody> bodies){
        List<PhysicsBody> colliders = new ArrayList<>();

        for(PhysicsBody bod: bodies){
            for(PhysicsBody bod2: bodies){
                if(!bod.equals(bod2)){

                }
            }
        }

        return null;
    }

}
