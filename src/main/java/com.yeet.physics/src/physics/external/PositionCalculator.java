package physics.external;

import java.util.List;

public class PositionCalculator {

    public static final double timeOfFrame = 0.016666666;
    private List<PhysicsObject> myObjects;

    PositionCalculator(List<PhysicsObject> objects) {
        this.myObjects = objects;
    }

    void updatePositions() {
        for (PhysicsObject o : myObjects) {
            Coordinate currentPosition = o.getMyCoordinateBody().getPos();
            double XVelocity = o.getVelocity().getMagnitude() * Math.cos(o.getVelocity().getDirection());
            double YVelocity = o.getVelocity().getMagnitude() * Math.sin(o.getVelocity().getDirection());
            Coordinate newCoordinate = new Coordinate(currentPosition.getX() + XVelocity*timeOfFrame, currentPosition.getY() + YVelocity*timeOfFrame);
            o.getMyCoordinateBody().update(newCoordinate);
        }
    }
}
