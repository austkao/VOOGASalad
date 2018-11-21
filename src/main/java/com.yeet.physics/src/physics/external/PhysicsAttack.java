package physics.external;

public class PhysicsAttack extends PhysicsObject {

    private CoordinateBody myCoordinateBody;
    private int myDirection;

    PhysicsAttack(double mass, Coordinate start, Dimensions dims) {
        super(mass, start, dims);
        this.myMass = 0;
    }

    @Override
    boolean isPhysicsAttack() {
        return true;
    }
}
