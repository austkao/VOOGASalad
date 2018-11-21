package physics.external;

public class PhysicsAttack extends PhysicsObject {

    private CoordinateBody myCoordinateBody;

    PhysicsAttack(Coordinate start, Dimensions dims) {
        super(start, dims);
        this.myCoordinateBody = new CoordinateBody(start, dims);
    }

    @Override
    boolean isPhysicsAttack() {
        return true;
    }
}
