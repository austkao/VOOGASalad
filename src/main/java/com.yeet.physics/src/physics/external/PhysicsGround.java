package physics.external;

public class PhysicsGround extends PhysicsObject {

    private CoordinateBody myCoordinateBody;

    PhysicsGround(Coordinate start, Dimensions dims) {
        super(start, dims);
        this.myCoordinateBody = new CoordinateBody(start, dims);
    }
}
