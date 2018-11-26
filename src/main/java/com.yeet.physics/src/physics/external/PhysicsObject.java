package physics.external;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicsObject {

    double myMass;
    List<PhysicsVector> currentForces = new ArrayList<>();
    CoordinateBody myCoordinateBody;
    double myDirection; //0: right, PI: left
    PhysicsVector myAcceleration;
    PhysicsVector myVelocity;
    int id;

    PhysicsObject(int id, double mass, Coordinate start, Dimensions dims) {
        this.myMass = mass;
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myMass = mass;
        this.myAcceleration = new PhysicsVector(0, 0);
        this.myVelocity = new PhysicsVector(0, 0);
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myDirection = 0; // start facing right
        this.id = id;
    }

    void applyForce(PhysicsVector force){ // ONLY CALL ONCE PER FRAME
        AccelerationCalculator ACalc = new AccelerationCalculator(force, myAcceleration, myVelocity, myMass);
        this.myAcceleration = ACalc.updateAcceleration();
        this.myVelocity = ACalc.updateVelocity();
    }

    double getMass() {
        return myMass;
    }

    PhysicsVector getAcceleration(){
        return this.myAcceleration;
    }

    PhysicsVector getVelocity(){
        return this.myVelocity;
    }

    CoordinateBody getMyCoordinateBody() {
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

    void addCurrentForce(PhysicsVector force) {
        currentForces.add(force);
    }

    List<PhysicsVector> getCurrentForces() {
        return currentForces;
    }

    void clearCurrentForces() {
        currentForces.clear();
    }

    void setDirection(double dir) {
        this.myDirection = dir;
    }

    double getDirection() {
        return myDirection;
    }

    int getId(){return this.id;}
}
