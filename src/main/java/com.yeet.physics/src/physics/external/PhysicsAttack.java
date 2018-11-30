package physics.external;

public class PhysicsAttack extends PhysicsObject {

    PhysicsAttack(int id, double mass, Coordinate start, Dimensions dims) {
        super(id, mass, start, dims);
    }

    @Override
    public boolean isPhysicsAttack() {
        return true;
    }
}
