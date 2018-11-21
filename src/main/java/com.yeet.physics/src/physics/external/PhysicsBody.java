package physics.external;

public class PhysicsBody extends PhysicsObject {

    //private CoordinateBody body;

    double myMass;
    int myDirection; //+1: right, -1: left
    PhysicsVector myAcceleration;
    PhysicsVector myVelocity;
    CoordinateBody myCoordinateBody;

    PhysicsBody(double mass, Coordinate start, Dimensions dims){
        super(start, dims);
        this.myMass = mass;
        //this.body = body;
        this.myAcceleration = new PhysicsVector(0, 0);
        this.myVelocity = new PhysicsVector(0, 0);
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myDirection = 1; // start facing right
    }

    @Override
    boolean isPhysicsBody(){
        return true;
    }

    void applyForce(PhysicsVector force){ // ONLY CALL ONCE PER FRAME
        AccelerationCalculator ACalc = new AccelerationCalculator(force, myAcceleration, myVelocity, myMass);
        this.myAcceleration = ACalc.updateAcceleration();
        this.myVelocity = ACalc.updateVelocity();
    }

    PhysicsVector getAcceleration(){
        return this.myAcceleration;
    }

    PhysicsVector getVelocity(){
        return this.myVelocity;
    }

    double getMass() {
        return this.myMass;
    }

    public CoordinateBody getMyCoordinateBody(){
        return this.myCoordinateBody;
    }

    void setDirection(int dir) {
        this.myDirection = dir;
    }

    int getDirection() {
        return myDirection;
    }
}