package physics.external;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector {

    private List<PhysicsObject> bodies;

    public CollisionDetector(List<PhysicsObject> bodies){
        this.bodies = bodies;
    }

    public List<Collision> detectCollisions(List<PhysicsObject> bodies){
        List<Collision> collisions = new ArrayList<Collision>();
        for(PhysicsObject bod: bodies){
            for(PhysicsObject bod2: bodies){
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
