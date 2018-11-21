package physics.external;

import java.util.List;

public class PositionCalculator {

    private List<PhysicsObject> myObjects;

    PositionCalculator(List<PhysicsObject> objects) {
        this.myObjects = objects;
    }

    void updatePositions() {
        for (PhysicsObject o : myObjects) {

        }
    }
}
