package physics.external;

import java.util.ArrayList;
import java.util.List;

public class Collision extends Interaction {

    private List<PhysicsBody> colliders;

    public Collision(List<PhysicsBody> colliders){
        super(colliders);
        this.colliders = colliders;
    }

    public PhysicsBody getCollider1(){
        if(this.colliders.size()>= 1){
            return this.colliders.get(0);
        }
        return null;
    }

    public PhysicsBody getCollider2() {
        if (this.colliders.size() >= 2) {
            return this.colliders.get(1);
        }
    }

    public List<PhysicsBody> getColliders(){
        return this.colliders;
    }
}
