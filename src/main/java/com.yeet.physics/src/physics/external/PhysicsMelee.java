package physics.external;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class of PhysicsAttack representing a close-range melee attack
 *
 * @author skm44
 * @author jrf36
 */

public class PhysicsMelee extends PhysicsAttack {

    private List<Rectangle2D> frames;
    private int currentFrame;


    PhysicsMelee(int id, int parentID, double direction, double mass, Coordinate start, Dimensions dims, CoordinateObject cord) {
        super(id, parentID, direction, mass, start, dims, cord);
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
        this.frames = new ArrayList<>();
        for(int i = 0; i < 150; i++){
            this.frames.add(new Rectangle2D.Double(cord.getPos().getX(), cord.getPos().getY(), dims.getSizeX(), dims.getSizeY()));
        }
        this.frames.add(new Rectangle2D.Double(0,0,0,0));
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

    public void step(){
        if(currentFrame < this.getFrames().size()){
            Rectangle2D myRect = this.frames.get(this.currentFrame);
            double x = myRect.getX();
            double y = myRect.getY();
            Dimensions newWandH = new Dimensions(myRect.getWidth(), myRect.getHeight());
            this.getMyCoordinateBody().setPos(x,y);
            this.getMyCoordinateBody().setDims(newWandH);
            this.setCurrentFrame(this.getCurrentFrame()+1);
        }
    }

    public void setCurrentFrame(int cur){
        this.currentFrame = cur;
    }

    public int getCurrentFrame(){
        return this.currentFrame;
    }

    public List<Rectangle2D> getFrames(){
        return this.frames;
    }
}
