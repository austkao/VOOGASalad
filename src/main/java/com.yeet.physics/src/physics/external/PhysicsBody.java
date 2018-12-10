package physics.external;

/**
 * Concrete class of PhysicsObject representing a player
 *
 * @author skm44
 * @author jrf36
 */

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
    public PhysicsVector getYVelocity() {
        double yMag = this.getVelocity().getMagnitude() * Math.sin(this.getVelocity().getDirection());
        PhysicsVector yVel = new PhysicsVector(yMag, -Math.PI/2);
        return yVel;
    }

    @Override
    public boolean isPhysicsBody(){
        return true;
    }

}