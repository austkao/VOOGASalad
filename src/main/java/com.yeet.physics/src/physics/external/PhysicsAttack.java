package physics.external;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a general attack
 *
 * @author skm44
 * @author jrf36
 */

public abstract class PhysicsAttack extends PhysicsObject {

    private int myParentID;
    private int knockForce;
    private List<Rectangle2D> frames;
    private int currentFrame;

    PhysicsAttack(int id, int parentID, double direction, double mass, Coordinate start, Dimensions dims, CoordinateObject cord) {
        super(id, mass, start, dims, cord);
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
    public PhysicsVector getXVelocity() {
        return null;
    }

    @Override
    public PhysicsVector getYVelocity() {
        return null;
    }

    public int getKnockForce() {
        return knockForce;
    }

    public void setKnockForce(int knockForce) {
        this.knockForce = knockForce;
    }

}
