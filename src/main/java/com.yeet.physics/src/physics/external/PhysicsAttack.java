package physics.external;

public class PhysicsAttack extends PhysicsObject {

    PhysicsAttack(int id, double mass, Coordinate start, Dimensions dims) {
        super(id, mass, start, dims);
    }

    @Override
    public boolean isPhysicsAttack() {
        return true;
    }

    @Override
    public PhysicsVector getXVelocity(){
        double xMag = this.getVelocity().getMagnitude() * Math.cos(this.getVelocity().getDirection());
        PhysicsVector xVel = new PhysicsVector(xMag, 0);
        return xVel;
    }
}
