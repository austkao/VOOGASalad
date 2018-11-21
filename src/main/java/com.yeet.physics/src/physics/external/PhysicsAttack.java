package physics.external;

public class PhysicsAttack extends PhysicsObject {

    PhysicsAttack(double mass, Coordinate start, Dimensions dims) {
        super(mass, start, dims);
    }

    @Override
    boolean isPhysicsAttack() {
        return true;
    }
}
