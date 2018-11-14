package physics.external;

public class XForce extends Force {

    double magnitude;

    XForce() {
        super(magnitude); // NEG: LEFT, POS: RIGHT
    }

    double getMagnitude() {
        return magnitude;
    }
}
