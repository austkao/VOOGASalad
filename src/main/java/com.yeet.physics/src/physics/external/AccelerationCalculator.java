package physics.external;

import java.util.ArrayList;
import java.util.List;

public class AccelerationCalculator {

    private PhysicsVector newForce;
    private List<PhysicsVector> myAccelerations = new ArrayList<>();
    private List<PhysicsVector> myVelocities = new ArrayList<>();
    private double myMass;
    public static final double timeOfFrame = 0.125; // Assume each frame is 1/8 of a sec

    public AccelerationCalculator(PhysicsVector f, PhysicsVector a, PhysicsVector v, double mass) {
        this.newForce = f;
        this.myAccelerations.add(a);
        this.myVelocities.add(v);
        this.myMass = mass;
    }

    public PhysicsVector updateAcceleration(){
        PhysicsVector newAcceleration = new PhysicsVector(newForce.getMagnitude() / myMass, newForce.getDirection()); // a = F/m
        myAccelerations.add(newAcceleration);
        return condenseVector(myAccelerations);
    }

    public PhysicsVector updateVelocity(){
        PhysicsVector currentAcceleration = condenseVector(myAccelerations);
        PhysicsVector newVelocity = new PhysicsVector(currentAcceleration.getMagnitude() * timeOfFrame, currentAcceleration.getDirection()); // Vf = Vo + at
        myVelocities.add(newVelocity);
        return condenseVector(myVelocities);
    }

    private PhysicsVector condenseVector(List<PhysicsVector> vectors) {
        NetVectorCalculator calc = new NetVectorCalculator(vectors);
        return calc.getNetVector();
    }
}
