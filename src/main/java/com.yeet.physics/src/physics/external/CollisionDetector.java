package physics.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollisionDetector {

    private Map<Integer, PhysicsObject> bodies;

    public CollisionDetector(Map<Integer, PhysicsObject> bodies){
        this.bodies = bodies;
    }

    public List<Collision> detectCollisions(Map<Integer, PhysicsObject> bodies){
        List<Collision> collisions = new ArrayList<>();
        for(PhysicsObject bod: bodies.values()){
            for(PhysicsObject bod2: bodies.values()){
                if(!bod.equals(bod2)){
                    if(bod.getMyCoordinateBody().intersects(bod2.getMyCoordinateBody())){
                        List<PhysicsObject> colliders = new ArrayList<>();
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
