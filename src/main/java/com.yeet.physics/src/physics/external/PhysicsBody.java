package physics.external;

import java.util.List;

public abstract class PhysicsBody {

    private CoordinateBody body;

    double mass;

    double velocityX;
    double velocityY;

    public PhysicsBody(CoordinateBody body, double mass){

    }

    public void applyForce(Force f){

    }

}
