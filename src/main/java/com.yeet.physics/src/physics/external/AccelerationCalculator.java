package physics.external;

import java.util.ArrayList;
import java.util.List;

public class AccelerationCalculator {

    private Force newForce;
    private List<Acceleration> myAccelerations = new ArrayList<>();
    private List<Velocity> myVelocities = new ArrayList<>();
    private double myMass;

    AccelerationCalculator(Force f, Acceleration a, Velocity v, double mass) {
        this.newForce = f;
        this.myAccelerations.add(a);
        this.myVelocities.add(v);
        this.myMass = mass;
    }

    Acceleration updateAcceleration(){
        Acceleration newAcceleration = new Acceleration(newForce.getMagnitude() / myMass, newForce.getDirection());
        myAccelerations.add(newAcceleration);
        NetVectorCalculator calc = new NetVectorCalculator(myAccelerations);
    }

    Velocity updateVelocity(){
        return new Velocity(20,0);
    }
}
