package physics.external;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSystem {

    public static final double defaultMass = 50;

    List<PhysicsBody> currentBodies = new ArrayList<>();

    PhysicsSystem() {

    }

    void createPhysicsBodies(int num) {
        int count = 0;
        while (count < num) {
            currentBodies.add(new PhysicsBody(defaultMass));
            count ++;
        }
    }
}
