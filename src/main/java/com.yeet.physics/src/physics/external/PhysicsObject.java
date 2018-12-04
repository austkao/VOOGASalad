package physics.external;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicsObject {

    protected double myMass;
    protected List<PhysicsVector> currentForces = new ArrayList<>();
    protected CoordinateBody myCoordinateBody;
    protected double myDirection; //0: right, PI: left
    protected PhysicsVector myAcceleration;
    protected PhysicsVector myVelocity;
    protected boolean isGrounded;
    protected int id;

    public PhysicsObject(int id, double mass, Coordinate start, Dimensions dims) {
        this.myMass = mass;
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myMass = mass;
        this.myAcceleration = new PhysicsVector(0, 0);
        this.myVelocity = new PhysicsVector(0, 0);
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myDirection = 0; // start facing right
        this.id = id;
    }

    public void applyForce(PhysicsVector force){ // ONLY CALL ONCE PER FRAME
        AccelerationCalculator ACalc = new AccelerationCalculator(force, myVelocity, myMass);
        this.myAcceleration = ACalc.updateAcceleration();
        this.myVelocity = ACalc.updateVelocity();
        if (id == 1) {
            System.out.println("CURRENT VELOCITY: ");
            System.out.println(myVelocity.getMagnitude());
            System.out.println(myVelocity.getDirection());
        }
    }

    public double getMass() {
        return myMass;
    }

    public PhysicsVector getAcceleration(){
        return this.myAcceleration;
    }

    public PhysicsVector getVelocity(){
        return this.myVelocity;
    }

    public void setVelocity(PhysicsVector velocity){
        this.myVelocity = velocity;
    }

    public CoordinateBody getMyCoordinateBody() {
        return myCoordinateBody;
    }
    public boolean isPhysicsAttack() {
        return false;
    }

    public boolean isPhysicsBody() {
        return false;
    }

    public boolean isPhysicsGround(){
        return false;
    }

    public boolean isGrounded() { return isGrounded; }

    public void setGrounded(boolean bool) { isGrounded = bool; }

    public void addCurrentForce(PhysicsVector force) {
        currentForces.add(force);
    }

    public List<PhysicsVector> getCurrentForces() {
        return currentForces;
    }

    public void clearCurrentForces() {
        currentForces.clear();
    }

    public void setDirection(double dir) {
        this.myDirection = dir;
    }

    public double getDirection() {
        return myDirection;
    }

    int getId(){return this.id;}

    public abstract PhysicsVector getXVelocity();
}
