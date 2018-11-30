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
            if (bod.getId() == 0 || bod.getId() < 1005) {
                System.out.println("Object " + bod.getId() + " WIDTH: " + (bod.getMyCoordinateBody().getHitBox().getMaxX() - bod.getMyCoordinateBody().getHitBox().getMinX()));
                System.out.println("TILE HEIGHT: " + (bod.getMyCoordinateBody().getHitBox().getMaxY() - bod.getMyCoordinateBody().getHitBox().getMinY()));
                System.out.println("TILE POSX: " + bod.getMyCoordinateBody().getHitBox().getMinX());
                System.out.println("TILE POSY: " + bod.getMyCoordinateBody().getHitBox().getMinY());
            }
            for(PhysicsObject bod2: bodies.values()){
                if(!bod.equals(bod2)){
                    if(bod.getMyCoordinateBody().intersects(bod2.getMyCoordinateBody())){
                        if (bod.getId() < 100 || bod2.getId() < 100) {
                            System.out.println("COLLISION BETWEEN " + bod.getId() + " AND " + bod2.getId());
                        }
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
