package physics.external;

import java.util.Map;

public class PositionCalculator {

    public static final double timeOfFrame = 0.016666666;
    private Map<Integer, PhysicsObject> myObjects;

    PositionCalculator(Map<Integer, PhysicsObject> objects) {
        this.myObjects = objects;
    }

    void updatePositions() {
        for (PhysicsObject o : myObjects.values()) {
            Coordinate currentPosition = o.getMyCoordinateBody().getPos();
            double XVelocity;
            double YVelocity;
            if (o.getVelocity().getMagnitude() == 0 || o.isPhysicsGround()) {
                XVelocity = 0;
                YVelocity = 0;
            } else {
                XVelocity = o.getVelocity().getMagnitude() * Math.cos(o.getVelocity().getDirection());
                YVelocity = o.getVelocity().getMagnitude() * Math.sin(o.getVelocity().getDirection());
            }
            Coordinate newCoordinate = new Coordinate(currentPosition.getX() + XVelocity*timeOfFrame, currentPosition.getY() + YVelocity*timeOfFrame);
            o.getMyCoordinateBody().update(newCoordinate);
        }
    }
}
