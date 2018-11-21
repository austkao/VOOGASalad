package physics.external;

public abstract class PhysicsObject {

    double myMass;
    CoordinateBody myCoordinateBody;
    int myDirection; //+1: right, -1: left
    PhysicsVector myAcceleration;
    PhysicsVector myVelocity;

    PhysicsObject(double mass, Coordinate start, Dimensions dims) {
        this.myMass = mass;
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myMass = mass;
        this.myAcceleration = new PhysicsVector(0, 0);
        this.myVelocity = new PhysicsVector(0, 0);
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myDirection = 1; // start facing right
    }

    void applyForce(PhysicsVector force){ // ONLY CALL ONCE PER FRAME
        AccelerationCalculator ACalc = new AccelerationCalculator(force, myAcceleration, myVelocity, myMass);
        this.myAcceleration = ACalc.updateAcceleration();
        this.myVelocity = ACalc.updateVelocity();
    }

    public double getMass() {
        return myMass;
    }

    PhysicsVector getAcceleration(){
        return this.myAcceleration;
    }

    PhysicsVector getVelocity(){
        return this.myVelocity;
    }

    public CoordinateBody getMyCoordinateBody() {
        return myCoordinateBody;
    }
    boolean isPhysicsAttack() {
        return false;
    }

    boolean isPhysicsBody() {
        return false;
    }

    boolean isPhysicsGround(){
        return false;
    }

    void setDirection(int dir) {
        this.myDirection = dir;
    }

    int getDirection() {
        return myDirection;
    }
}
