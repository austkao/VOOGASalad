package physics.external;

// Decided to make Vector a concrete class that has a magnitude/direction, and removed XForce/YForce subclasses.
// This will allow any direction vector to be made, and when X and Y forces are made, they use constants for direction

public class Force extends PhysicsVector {

    Force (double magnitude, double direction) {
        super(magnitude, direction);
    }
}
