package physics.external;

public class PhysicsBody {

    //private CoordinateBody body;

    double mass;
    PhysicsVector acceleration;
    PhysicsVector velocity;

    PhysicsBody(double mass){
        this.mass = mass;
        //this.body = body;
        this.acceleration = new PhysicsVector(0, 0);
        this.velocity = new PhysicsVector(0, 0);
    }

    public void applyForce(PhysicsVector force){
        AccelerationCalculator ACalc = new AccelerationCalculator(force, acceleration, velocity, mass);
        this.acceleration = ACalc.updateAcceleration();
        this.velocity = ACalc.updateVelocity();
    }

}