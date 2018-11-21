package physics.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.EventBusFactory;
import messenger.external.PositionsUpdateEvent;
import messenger.external.SuccessfulEvent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PhysicsSystem {

    public static final double defaultMass = 50;
    List<PhysicsObject> gameObjects;

    private EventBus myMessageBus;

    /*
    private EventBus myMessageBus;
    private String path = "/example_character_1/";
    private MediaPlayer myPlayer;

    public AudioSystem(){
        myMessageBus = EventBusFactory.getEventBus();
        myPlayer= new MediaPlayer();
    }
     */
    PhysicsSystem(List<PhysicsObject> objects) {
        this.myMessageBus = EventBusFactory.getEventBus();
    }

    void update() {
        CollisionDetector detector = new CollisionDetector(gameObjects);
        List<Collision> collisions = new ArrayList<>(detector.detectCollisions(gameObjects));
        CollisionHandler collHandler = new CollisionHandler(collisions);
        collHandler.update();
        PassiveForceHandler passHandler = new PassiveForceHandler(gameObjects);
        passHandler.update();
        applyForces(gameObjects);
        // update position
        for(PhysicsObject obj: gameObjects){
            //Convert to map
        }
        PositionsUpdateEvent newPos = new PositionsUpdateEvent(); //Parameter is hashmap with integer as key and Point2D as value
        myMessageBus.post(newPos);
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

    private HashMap<Integer, Point2D> convertToMap(List<PhysicsObject> objects) {
        return null;
    }

    List<PhysicsObject> getGameObjects() {
        return this.gameObjects;
    }
}
