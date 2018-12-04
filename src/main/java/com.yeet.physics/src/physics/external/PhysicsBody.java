package physics.external;

public class PhysicsBody extends PhysicsObject {

    
    public PhysicsBody(int id, double mass, Coordinate start, Dimensions dims){
        super(id, mass, start, dims);
    }

    @Override
    public PhysicsVector getXVelocity(){
        double xMag = this.getVelocity().getMagnitude() * Math.cos(this.getVelocity().getDirection());
        PhysicsVector xVel = new PhysicsVector(xMag, 0);
        return xVel;
    }

    @Override
    public boolean isPhysicsBody(){
        return true;
    }

}