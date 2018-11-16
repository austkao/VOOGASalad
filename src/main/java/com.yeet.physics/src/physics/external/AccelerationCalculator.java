package physics.external;

public class AccelerationCalculator {

    private XForce myXForce;
    private YForce myYForce;

    AccelerationCalculator(XForce x, YForce y) {
        this.myXForce = x;
        this.myYForce = y;
    }
}
