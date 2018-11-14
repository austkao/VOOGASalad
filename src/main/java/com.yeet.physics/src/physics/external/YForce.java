package physics.external;

public class YForce extends Force {

    YForce() {
        super(magnitude); // NEG: UP, POS: DOWN
    }

    double getMagnitude() {
        return magnitude;
    }
}
