package physics.external;

public class PhysicsBody extends PhysicsObject {

    //private CoordinateBody body;

    double mass;
    PhysicsVector acceleration;
    PhysicsVector velocity;
    CoordinateBody myCoordinateBody;

    PhysicsBody(double mass, Coordinate start, Dimensions dims){
        super(start, dims);
        this.mass = mass;
        //this.body = body;
        this.acceleration = new PhysicsVector(0, 0);
        this.velocity = new PhysicsVector(0, 0);
        this.myCoordinateBody = new CoordinateBody(start, dims);
    }

    @Override
    boolean isPhysicsBody(){
        return true;
    }

    void applyForce(PhysicsVector force){ // ONLY CALL ONCE PER FRAME
        AccelerationCalculator ACalc = new AccelerationCalculator(force, acceleration, velocity, mass);
        this.acceleration = ACalc.updateAcceleration();
        this.velocity = ACalc.updateVelocity();
    }

    PhysicsVector getAcceleration(){
        return this.acceleration;
    }

    PhysicsVector getVelocity(){
        return this.velocity;
    }

    double getMass() {
        return this.mass;
    }

    public CoordinateBody getMyCoordinateBody(){
        return this.myCoordinateBody;
    }
}