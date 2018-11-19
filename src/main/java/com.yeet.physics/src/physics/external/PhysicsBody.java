package physics.external;

import java.util.List;

public class PhysicsBody {

    //private CoordinateBody body;

    double mass;
    Acceleration acceleration;
    Velocity velocity;

    PhysicsBody(double mass){
        this.mass = mass;
        //this.body = body;
        this.acceleration = new Acceleration(0, 0);
        this.velocity = new Velocity(0, 0);
    }

    public void applyForce(Force force){

        AccelerationCalculator ACalc = new AccelerationCalculator(force, acceleration, velocity, mass);
        this.acceleration = ACalc.updateAcceleration();
        this.velocity = ACalc.updateVelocity();
    }

}