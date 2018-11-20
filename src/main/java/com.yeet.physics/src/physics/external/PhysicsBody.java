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

    void applyForce(PhysicsVector force){ // ONLY CALL ONCE PER FRAME
        AccelerationCalculator ACalc = new AccelerationCalculator(force, acceleration, velocity, mass);
        this.acceleration = ACalc.updateAcceleration();
        this.velocity = ACalc.updateVelocity();
    }

    PhysicsVector getAcceleration(){
        return acceleration;
    }

    PhysicsVector getVelocity(){
        return velocity;
    }
}