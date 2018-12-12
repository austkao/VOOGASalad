package physics.external;

import static java.lang.Math.PI;
import static physics.external.CollisionHandler.timeOfFrame;
import static physics.external.PassiveForceHandler.DEFAULT_GRAVITY_ACCELERATION;

/**
 * Concrete class of PhysicsObject representing a player
 *
 * @author skm44
 * @author jrf36
 */

public class PhysicsBody extends PhysicsObject {

    private int respawnX;
    private int respawnY;

    public PhysicsBody(int id, double mass, Coordinate start, Dimensions dims, CoordinateObject cord){
         super(id, mass, start, dims, cord);
         this.respawnX = 250;
         this.respawnY = 250; 
     }

    public PhysicsBody(int id, double mass, Coordinate start, Dimensions dims, CoordinateObject cord, int respawnX, int respawnY){
        super(id, mass, start, dims, cord);
        this.respawnX = respawnX;
        this.respawnY = respawnY;
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

    public void respawn(){
        double gravityMag = Math.round(this.getMass() * DEFAULT_GRAVITY_ACCELERATION);
        this.addCurrentForce(new PhysicsVector(-this.myMass*this.getXVelocity().getMagnitude()/timeOfFrame, 0));
        this.addCurrentForce(new PhysicsVector(Math.round(this.myMass*this.getYVelocity().getMagnitude()/(timeOfFrame) + gravityMag), -PI/2));
        this.getMyCoordinateBody().setPos(this.getRespawnX(), this.getRespawnY());
    }

    public double getRespawnX(){
        return this.respawnX;
    }

    public double getRespawnY(){
        return this.respawnY;
    }

}