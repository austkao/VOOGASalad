package physics.external;

import java.util.ArrayList;
import java.util.List;

public class AccelerationCalculator {

    private PhysicsVector newForce;
    private List<PhysicsVector> myAccelerations = new ArrayList<>();
    private List<PhysicsVector> myVelocities = new ArrayList<>();
    private double myMass;

    AccelerationCalculator(PhysicsVector f, PhysicsVector a, PhysicsVector v, double mass) {
        this.newForce = f;
        this.myAccelerations.add(a);
        this.myVelocities.add(v);
        this.myMass = mass;
    }

    PhysicsVector updateAcceleration(){
        PhysicsVector newAcceleration = new PhysicsVector(newForce.getMagnitude() / myMass, newForce.getDirection()); // a = F/m
        myAccelerations.add(newAcceleration);
        return condenseVector(myAccelerations);
    }

    PhysicsVector updateVelocity(){
        //PhysicsVector newVelocity = new PhysicsVector()
        return new PhysicsVector(20,0);
    }

    PhysicsVector condenseVector(List<PhysicsVector> vectors) {
        NetVectorCalculator calc = new NetVectorCalculator(vectors);
        return calc.getNetVector();
    }
}
