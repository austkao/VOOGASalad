package physics.external;

import java.util.List;

public class PositionCalculator {

    private List<PhysicsObject> myObjects;

    PositionCalculator(List<PhysicsObject> objects) {
        this.myObjects = objects;
    }

    void updatePositions() {
        for (PhysicsObject o : myObjects) {
            double XVelocity = o.getVelocity().getMagnitude() * Math.cos(o.getVelocity().getDirection());
            double YVelocity = o.getVelocity().getMagnitude() * Math.sin(o.getVelocity().getDirection());
            //Coordinate newCoordinate = new Coordinate(o.getMyCoordinateBody().getHitBox() + ,0);
//            o.getVelocity();
//            o.getMyCoordinateBody().update();
        }
    }
}
