package physics.external;

public abstract class PhysicsVector {
    protected double magnitude;
    protected double direction; // IN RADIANS

    PhysicsVector (double magnitude, double direction) {
        this.magnitude = magnitude;
        this.direction = direction;
    }
    double getMagnitude() {
        return magnitude;
    }
    double getDirection() {
        return direction;
    }
}