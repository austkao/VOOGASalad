package physics.external;

public class PhysicsGround extends PhysicsObject {

    PhysicsGround(double mass, Coordinate start, Dimensions dims) {
        super(mass, start, dims);
        this.myMass = Integer.MAX_VALUE;
    }

    @Override
    boolean isPhysicsGround(){
        return true;
    }
}
