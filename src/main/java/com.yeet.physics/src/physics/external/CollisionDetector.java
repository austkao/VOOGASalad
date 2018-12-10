package physics.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class

CollisionDetector {

    private Map<Integer, PhysicsObject> bodies;

    public CollisionDetector(Map<Integer, PhysicsObject> bodies){
        this.bodies = bodies;
    }

    public List<Collision> detectCollisions(Map<Integer, PhysicsObject> bodies){
        List<Collision> collisions = new ArrayList<>();
        Map<Integer, PhysicsObject> currentBodies = new HashMap<>(bodies);
        for(PhysicsObject bod: currentBodies.values()){
            for(PhysicsObject bod2: currentBodies.values()){
                if(!bod.equals(bod2)){
                    Intersection myInt = bod.getMyCoordinateBody().intersects(bod2.getMyCoordinateBody());
                    List<Side> s = myInt.getSides();
                    if(s.size() > 0){
                        List<PhysicsObject> colliders = new ArrayList<>();
                        colliders.add(bod);
                        colliders.add(bod2);
                        Collision col = new Collision(colliders, myInt);
                        collisions.add(col);
                    }
                }
            }
        }
        return collisions;
    }

}
