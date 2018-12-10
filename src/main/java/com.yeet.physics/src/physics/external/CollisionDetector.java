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
        Map<Integer, PhysicsObject> currentBodies = bodies;
        for(PhysicsObject bod: currentBodies.values()){
            for(PhysicsObject bod2: currentBodies.values()){
                if (bod.getId() == 0 && bod2.getId() == 100) {
                    System.out.println("HitBox Position: " + bod.getMyCoordinateBody().getHitBox().getMinX() + ", " + bod.getMyCoordinateBody().getHitBox().getMaxX() + ", " + bod.getMyCoordinateBody().getHitBox().getMinY() + ", " + bod.getMyCoordinateBody().getHitBox().getMaxY());
                    System.out.println("HurtBox Position: " + bod2.getMyCoordinateBody().getHitBox().getMinX() + ", " + bod2.getMyCoordinateBody().getHitBox().getMaxX() + ", " + bod2.getMyCoordinateBody().getHitBox().getMinY() + ", " + bod2.getMyCoordinateBody().getHitBox().getMaxY());
                    System.out.println("Intersects? " + bod.getMyCoordinateBody().intersects(bod2.getMyCoordinateBody()));
                }
                if(!bod.equals(bod2) && !(bod.isPhysicsGround() && bod2.isPhysicsGround())){
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
