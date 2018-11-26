package physics.external;

public class PhysicsGround extends PhysicsObject {

    public PhysicsGround(double mass, Coordinate start, Dimensions dims) {
        super(mass, start, dims);
        this.myMass = Integer.MAX_VALUE;
    }

    @Override
    public boolean isPhysicsGround(){
        return true;
    }
}
