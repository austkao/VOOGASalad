package physics.external;

import java.util.ArrayList;
import java.util.List;

public class Collision extends Interaction {

    private List<PhysicsObject> colliders;

    public Collision(List<PhysicsObject> colliders, Side s){
        super(colliders);
        this.colliders = colliders;
    }

    public PhysicsObject getCollider1(){
        if(this.colliders.size()>= 1){
            return this.colliders.get(0);
        }
        return null;
    }

    public PhysicsObject getCollider2() {
        if (this.colliders.size() >= 2) {
            return this.colliders.get(1);
        }
        return null;
    }

    public List<PhysicsObject> getColliders(){
        return this.colliders;
    }
}
