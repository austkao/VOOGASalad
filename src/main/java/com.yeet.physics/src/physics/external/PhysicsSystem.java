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

    private static final double defaultMass = 50;
    private List<PhysicsObject> gameObjects;
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
    public PhysicsSystem(List<PhysicsObject> objects) {
        this.myMessageBus = EventBusFactory.getEventBus();
        gameObjects = objects;
    }

    public void update() {
        CollisionDetector detector = new CollisionDetector(gameObjects);
        List<Collision> collisions = new ArrayList<>(detector.detectCollisions(gameObjects));
        CollisionHandler collHandler = new CollisionHandler(collisions);
        collHandler.update();
        PassiveForceHandler passHandler = new PassiveForceHandler(gameObjects);
        passHandler.update();
        applyForces();
        updatePositions();
        Map<Integer, Point2D> myMap;
        myMap = convertToMap(gameObjects);
        PositionsUpdateEvent newPos = new PositionsUpdateEvent(myMap); //Parameter is hashmap with integer as key and Point2D as value
        myMessageBus.post(newPos);
    }

    public void addPhysicsBodies(int num) {
        int count = 0;
        while (count < num) {
            gameObjects.add(new PhysicsBody(defaultMass, new Coordinate(0,0), new Dimensions(0,0)));
            count ++;
        }
    }

    private void applyForces() {
        for (PhysicsObject b : gameObjects) {
            NetVectorCalculator calc = new NetVectorCalculator(b.getCurrentForces());
            b.applyForce(calc.getNetVector());
            b.clearCurrentForces();
        }

    }

    private void updatePositions() {
        PositionCalculator calc = new PositionCalculator(gameObjects);
        calc.updatePositions();
    }

    private Map<Integer, Point2D> convertToMap(List<PhysicsObject> objectList) {
        Map<Integer, Point2D> out = new HashMap<>();
        for(PhysicsObject obj: objectList){
            //Convert to map
            Point2D.Double point = new Point2D.Double(obj.getMyCoordinateBody().getPos().getX(), obj.getMyCoordinateBody().getPos().getY());
            out.put(objectList.indexOf(obj), point);
        }
        return out;
    }

    List<PhysicsObject> getGameObjects() {
        return this.gameObjects;
    }
}
