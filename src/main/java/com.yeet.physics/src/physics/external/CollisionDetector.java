package physics.external;

import java.util.ArrayList;
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
        for(PhysicsObject bod: bodies.values()){
            for(PhysicsObject bod2: bodies.values()){
                if (bod.getId() == 0 && bod2.getId() == 100) {
                    System.out.println("HitBox Position: " + bod.getMyCoordinateBody().getCoordinateBox().getMinX() + ", " + bod.getMyCoordinateBody().getCoordinateBox().getMaxX() + ", " + bod.getMyCoordinateBody().getCoordinateBox().getMinY() + ", " + bod.getMyCoordinateBody().getCoordinateBox().getMaxY());
                    System.out.println("HurtBox Position: " + bod2.getMyCoordinateBody().getCoordinateBox().getMinX() + ", " + bod2.getMyCoordinateBody().getCoordinateBox().getMaxX() + ", " + bod2.getMyCoordinateBody().getCoordinateBox().getMinY() + ", " + bod2.getMyCoordinateBody().getCoordinateBox().getMaxY());
                    System.out.println("Intersects? " + bod.getMyCoordinateBody().intersects(bod2.getMyCoordinateBody()));
                }
                if(!bod.equals(bod2)){
                    Intersection myInt = bod.getMyCoordinateBody().intersects(bod2.getMyCoordinateBody());
                    Side s = myInt.getIntersector();
                    if(!s.getMySide().equals("None")){
                        List<PhysicsObject> colliders = new ArrayList<>();
                        colliders.add(bod);
                        colliders.add(bod2);
                        Collision col = new Collision(colliders, s);
                        collisions.add(col);
                    }
                }
            }
        }
        return collisions;
    }

}
