package physics.external;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector {

    private List<PhysicsBody> bodies;

    public CollisionDetector(List<PhysicsBody> bodies){
        this.bodies = bodies;
    }

    public List<Collision> detectCollisions(List<PhysicsBody> bodies){
        List<Collision> collisions = new ArrayList<Collision>();
        for(PhysicsBody bod: bodies){
            for(PhysicsBody bod2: bodies){
                if(!bod.equals(bod2)){
                    if(bod.getMyCoordinateBody().intersects(bod2.getMyCoordinateBody())){
                        List<PhysicsBody> colliders = new ArrayList<>();
                        colliders.add(bod);
                        colliders.add(bod2);
                        Collision col = new Collision(colliders);
                        collisions.add(col);
                    }
                }
            }
        }
        return collisions;
    }

}
