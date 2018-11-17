package physics.external;

import java.util.List;

class NetForceCalculator {

    private List<Force> myForces;

    NetForceCalculator(List<Force> forces) {
        myForces = forces;
    }

    Force getNetForce() {
        while(myForces.size() > 1) {
            Force force1 = myForces.get(0);
            Force force2 = myForces.get(1);
            double newX = getX(force1) + getX(force2);
            System.out.println("newX: " + newX);
            double newY = getY(force1) + getY(force2);
            System.out.println("newY: " + newY);
            double newMagnitude = Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2));
            double newDirection = Math.atan(newX/newY);
            System.out.println("Current Direction: " + newDirection);
            myForces.add(new Force(newMagnitude, newDirection));
            myForces.remove(force1);
            myForces.remove(force2);
        }
        return myForces.get(0);
    }

    private double getX(Force f) {
        return f.getMagnitude() * Math.cos(f.getDirection());
    }

    private double getY(Force f) {
        return f.getMagnitude() * Math.sin(f.getDirection());
    }
}
