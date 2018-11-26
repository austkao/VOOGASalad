package physics.external;

public class PhysicsAttack extends PhysicsObject {

    public PhysicsAttack(double mass, Coordinate start, Dimensions dims) {
        super(mass, start, dims);
    }

    @Override
    public boolean isPhysicsAttack() {
        return true;
    }
}
