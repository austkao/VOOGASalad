package physics.external;

public class PhysicsAttack extends PhysicsObject {

    private CoordinateBody myCoordinateBody;
    private int myDirection;

    PhysicsAttack(Coordinate start, Dimensions dims) {
        super(start, dims);
        this.myCoordinateBody = new CoordinateBody(start, dims);
    }

    @Override
    boolean isPhysicsAttack() {
        return true;
    }

    void setDirection(int dir) {
        this.myDirection = dir;
    }

    int getDirection() {
        return myDirection;
    }
}
