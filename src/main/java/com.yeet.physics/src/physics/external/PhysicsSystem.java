package physics.external;

import java.util.ArrayList;
import java.util.List;


public class PhysicsSystem {

    public static final double defaultMass = 50;
    List<PhysicsObject> gameObjects;

    PhysicsSystem(List<PhysicsObject> objects) {
        this.gameObjects = objects;
    }

    void update() {
        CollisionDetector detector = new CollisionDetector(gameObjects);
        List<Collision> collisions = new ArrayList<>(detector.detectCollisions(gameObjects));
        CollisionHandler collHandler = new CollisionHandler(collisions);
        collHandler.update();
        PassiveForceHandler passHandler = new PassiveForceHandler(gameObjects);
        passHandler.update();
        applyForces(gameObjects);
    }

    public void addPhysicsBodies(int num) {
        int count = 0;
        while (count < num) {
            gameObjects.add(new PhysicsBody(defaultMass, new Coordinate(0,0), new Dimensions(0,0)));
            count ++;
        }
    }

    private void applyForces(List<PhysicsObject> objects) {
        for (PhysicsObject b : objects) {
            NetVectorCalculator calc = new NetVectorCalculator(b.getCurrentForces());
            b.applyForce(calc.getNetVector());
        }
    }

    List<PhysicsObject> getGameObjects() {
        return this.gameObjects;
    }
}
