package physics.external;

public class PhysicsGround extends PhysicsObject {

    private double frictionCoef;

    PhysicsGround(int id, double mass, Coordinate start, Dimensions dims) {
        super(id, mass, start, dims);
        this.frictionCoef = .5;
    }

    PhysicsGround(int id, double mass, Coordinate start, Dimensions dims, double frictionCoef) {
        super(id, mass, start, dims);
        this.frictionCoef = frictionCoef;
    }

    @Override
    public boolean isPhysicsGround(){
        return true;
    }

    @Override
    public PhysicsVector getXVelocity() {
        return null;
    }

    @Override
    public PhysicsVector getYVelocity() {
        return null;
    }

    public double getFrictionCoef() {
        return frictionCoef;
    }

    public void setFrictionCoef(double frictionCoef) {
        this.frictionCoef = frictionCoef;
    }
}
