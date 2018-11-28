package physics.external;

public class PhysicsGround extends PhysicsObject {

    PhysicsGround(int id, double mass, Coordinate start, Dimensions dims) {
        super(id, mass, start, dims);
        this.myMass = Integer.MAX_VALUE;
    }

    @Override
    public boolean isPhysicsGround(){
        return true;
    }
}
