package physics.external;

import java.util.List;

public abstract class Interaction {

    private List<PhysicsBody> bodies;

    public Interaction(List<PhysicsBody> bodies){
        this.bodies = bodies;
    }


}
