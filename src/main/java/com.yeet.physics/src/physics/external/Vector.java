package physics.external;

public abstract class Vector {
    protected double magnitude;
    protected double direction; // IN RADIANS

    Vector (double magnitude, double direction) {
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
