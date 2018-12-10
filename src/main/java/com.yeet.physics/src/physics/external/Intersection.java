package physics.external;

import java.util.List;

import static java.lang.Math.PI;

public class Intersection {


    private List<Side> sides;
    private double collisionDirection;

    public Intersection(List<Side> sides) {
        this.sides = sides;
        calculateDirection();
    }


    private void calculateDirection() {
        if (this.containsSide(new Side("TOP")) && this.containsSide(new Side("BOTTOM"))) {
            if (this.containsSide(new Side("LEFT"))) {
                this.setCollisionDirection(0);
            } else {
                this.setCollisionDirection(PI);
            }

        } else {
            if (this.containsSide(new Side("BOTTOM"))) {
                this.setCollisionDirection(-PI/2);
            } else {
                this.setCollisionDirection(PI/2);
            }
        }
    }

    public List<Side> getSides() {
        return sides;
    }

    public boolean containsSide(Side s){
        for(Side side: this.getSides()){
            if(side.getMySide().equals(s.getMySide())){
                return true;
            }
        }
        return false;
    }

    public void setSides(List<Side> sides) {
        this.sides = sides;
    }

    public double getCollisionDirection() {
        return collisionDirection;
    }

    public void setCollisionDirection(double collisionDirection) {
        this.collisionDirection = collisionDirection;
    }
}
