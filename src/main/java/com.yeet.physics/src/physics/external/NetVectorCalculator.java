package physics.external;

import java.util.List;

public class NetVectorCalculator {

    private List<PhysicsVector> myVectors;

    public NetVectorCalculator(List<PhysicsVector> vectors) {
        myVectors = vectors;
    }

    public PhysicsVector getNetVector() {

        while(myVectors.size() > 1) {
            PhysicsVector vector1 = myVectors.get(0);
            PhysicsVector vector2 = myVectors.get(1);
            double newX = getX(vector1) + getX(vector2);
            double newY = getY(vector1) + getY(vector2);
            double newMagnitude = Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2));
            double newDirection = Math.atan(newY/newX);
            myVectors.add(new PhysicsVector(newMagnitude, newDirection));
            myVectors.remove(vector1);
            myVectors.remove(vector2);
        }
        if (myVectors.size() > 0) {
            return myVectors.get(0);
        }
        return new PhysicsVector(0,0);
    }

    private double getX(PhysicsVector v) {
        return v.getMagnitude() * Math.cos(v.getDirection());
    }

    private double getY(PhysicsVector v) {
        return v.getMagnitude() * Math.sin(v.getDirection());
    }
}
