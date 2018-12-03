package physics.external;

import java.util.Map;

public class PositionCalculator {

    public static final double timeOfFrame = 0.016666666;
    private Map<Integer, PhysicsObject> myObjects;

    PositionCalculator(Map<Integer, PhysicsObject> objects) {
        this.myObjects = objects;
    }

    void updatePositions() {
        Coordinate newCoordinate;
        for (PhysicsObject o : myObjects.values()) {
            Coordinate currentPosition = o.getMyCoordinateBody().getPos();
            double XVelocity;
            double YVelocity;
            if (o.isPhysicsGround()) {
                newCoordinate = new Coordinate(currentPosition.getX(), currentPosition.getY());
            } else {
                XVelocity = o.getVelocity().getMagnitude() * Math.cos(o.getVelocity().getDirection());
                if (o.isGrounded()) {
                    YVelocity = 0;
                } else {
                    YVelocity = o.getVelocity().getMagnitude() * Math.sin(o.getVelocity().getDirection());
                }
                newCoordinate = new Coordinate(currentPosition.getX() + XVelocity * timeOfFrame, currentPosition.getY() + YVelocity * timeOfFrame);
            }
            o.getMyCoordinateBody().update(newCoordinate);
        }
    }
}
