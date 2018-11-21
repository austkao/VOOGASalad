package physics.external;

public abstract class PhysicsObject {

    private CoordinateBody myCoordinateBody;

    PhysicsObject(Coordinate start, Dimensions dims) {
        this.myCoordinateBody = new CoordinateBody(start, dims);
    }

    boolean isPhysicsBody() {
        return false;
    }
    
    public CoordinateBody getMyCoordinateBody() {
        return myCoordinateBody;
    }
    boolean isPhysicsAttack() {
        return false;
    }
}
