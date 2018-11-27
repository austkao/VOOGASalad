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
import java.util.Map;


public class PhysicsSystem {


    public static final double defaultMass = 50;
    private List<PhysicsObject> gameObjects = new ArrayList<>();
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

    public PhysicsSystem() {
        this.myMessageBus = EventBusFactory.getEventBus();
    }

    public void update() {
        CollisionDetector detector = new CollisionDetector(gameObjects);
        List<Collision> collisions = new ArrayList<>(detector.detectCollisions(gameObjects));
        MovementHandler movHandler = new MovementHandler(gameObjects);
        movHandler.update(); //How does this work with subscribe?
        CollisionHandler collHandler = new CollisionHandler(collisions);
        collHandler.update();
        PassiveForceHandler passHandler = new PassiveForceHandler(gameObjects);
        passHandler.update();
        applyForces();
        updatePositions();
        Map<Integer, Point2D> myMap;
        myMap = convertToMap();
        PositionsUpdateEvent newPos = new PositionsUpdateEvent(myMap); //Parameter is hashmap with integer as key and Point2D as value
        myMessageBus.post(newPos);
    }

    public void addPhysicsBodies(int num) {
        int count = 0;
        while (count < num) {
            gameObjects.add(new PhysicsBody(count, defaultMass, new Coordinate(0,0), new Dimensions(1,1)));
            count ++;
        }
    }

    public void applyForces() {
        for (PhysicsObject b : gameObjects) {
            NetVectorCalculator calc = new NetVectorCalculator(b.getCurrentForces());
            b.applyForce(calc.getNetVector());
            b.clearCurrentForces();
        }

    }

    public void updatePositions() {
        PositionCalculator calc = new PositionCalculator(gameObjects);
        calc.updatePositions();
    }

    private Map<Integer, Point2D> convertToMap() {
        Map<Integer, Point2D> out = new HashMap<>();
        for(PhysicsObject obj: gameObjects){
            //Convert to map
            Point2D.Double point = new Point2D.Double(obj.getMyCoordinateBody().getPos().getX(), obj.getMyCoordinateBody().getPos().getY());
            out.put(gameObjects.indexOf(obj), point);
        }
        return out;
    }

    List<PhysicsObject> getGameObjects() {
        return this.gameObjects;
    }
}
