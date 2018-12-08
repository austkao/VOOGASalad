package physics.external;

public class PhysicsAttack extends PhysicsObject {

    private int myParentID;

    PhysicsAttack(int id, int parentID, double direction, double mass, Coordinate start, Dimensions dims) {
        super(id, mass, start, dims);
        this.myParentID = parentID;
        this.myDirection = direction;
    }

    @Override
    public int getParentID() { return myParentID; }

    @Override
    public boolean isPhysicsAttack() {
        return true;
    }

    @Override
    public PhysicsVector getYVelocity() {
        double yMag = this.getVelocity().getMagnitude() * Math.sin(this.getVelocity().getDirection());
        PhysicsVector yVel = new PhysicsVector(yMag, -Math.PI/2);
        return yVel;
    }

    @Override
    public PhysicsVector getXVelocity(){
        double xMag = this.getVelocity().getMagnitude() * Math.cos(this.getVelocity().getDirection());
        PhysicsVector xVel = new PhysicsVector(xMag, 0);
        return xVel;
    }
}
