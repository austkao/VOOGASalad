package physics.external;

import java.util.List;

class NetVectorCalculator {

    private List<Vector> myVectors;

    NetVectorCalculator(List<Vector> vectors) {
        myVectors = vectors;
    }

    Vector getNetForce() {
        while(myVectors.size() > 1) {
            Vector vector1 = myVectors.get(0);
            Vector vector2 = myVectors.get(1);
            double newX = getX(vector1) + getX(vector2);
            double newY = getY(vector1) + getY(vector2);
            double newMagnitude = Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2));
            double newDirection = Math.atan(newX/newY);
            myVectors.add(new Force(newMagnitude, newDirection));
            myVectors.remove(vector1);
            myVectors.remove(vector2);
        }
        return myVectors.get(0);
    }

    private double getX(Vector v) {
        return v.getMagnitude() * Math.cos(v.getDirection());
    }

    private double getY(Vector v) {
        return v.getMagnitude() * Math.sin(v.getDirection());
    }
}
