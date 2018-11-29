package physics.external;

import com.google.common.eventbus.EventBus;
import messenger.external.EventBusFactory;
import messenger.external.PositionsUpdateEvent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;


public class PhysicsSystem {


    public static final double defaultMass = 50;
    public static final double defaultStrength = 20;
    public static final double defaultJumpHeight = 20;
    public static final double defaultMovementSpeed = 20;



    //List<PhysicsObject> gameObjects = new ArrayList<>();
    List<PlayerCharacteristics> playerCharacteristics = new ArrayList<>();

    Map<Integer, PhysicsObject> gameObjects = new HashMap<>();

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
        //MovementHandler movHandler = new MovementHandler(gameObjects);
        //movHandler.update(); //How does this work with subscribe?
        CollisionHandler collHandler = new CollisionHandler(collisions);
        collHandler.update();
        PassiveForceHandler passHandler = new PassiveForceHandler(gameObjects);
        passHandler.update();
        applyForces();
        updatePositions();
        Map<Integer, Point2D> myMap;
        PositionsUpdateEvent newPos = new PositionsUpdateEvent(getPositionsMap(), getDirectionsMap()); //Parameter is hashmap with integer as key and Point2D as value
        myMessageBus.post(newPos);
    }

    public void addPhysicsBodies(int num) {
        int id = gameObjects.size();
        while (id < num) {
            gameObjects.put(id, new PhysicsBody(id, defaultMass, new Coordinate(0,0), new Dimensions(1,1)));
            playerCharacteristics.add(new PlayerCharacteristics(id, defaultStrength, defaultJumpHeight, defaultMovementSpeed));
            id ++;
        }
    }

    public void applyForces() {
        for (PhysicsObject b : gameObjects.values()) {
            NetVectorCalculator calc = new NetVectorCalculator(b.getCurrentForces());
            b.applyForce(calc.getNetVector());
            b.clearCurrentForces();
        }

    }

    public void updatePositions() {
        PositionCalculator calc = new PositionCalculator(gameObjects);
        calc.updatePositions();
    }

    private Map<Integer, Point2D> getPositionsMap() {
        Map<Integer, Point2D> out = new HashMap<>();
        for(PhysicsObject obj: gameObjects.values()){
            Point2D.Double point = new Point2D.Double(obj.getMyCoordinateBody().getPos().getX(), obj.getMyCoordinateBody().getPos().getY());
            out.put(obj.getId(), point);
        }
        return out;
    }

    private Map<Integer, Double> getDirectionsMap() {
        Map<Integer, Double> out = new HashMap<>();
        double direction;
        for(PhysicsObject obj: gameObjects.values()){
            direction = obj.getDirection();
            out.put(obj.getId(), direction);
        }
        return out;
    }

    public Map<Integer, PhysicsObject> getGameObjects() {
        return this.gameObjects;
    }

    public void jump(int id) {
        PhysicsObject currentBody = gameObjects.get(id);
        currentBody.addCurrentForce(new PhysicsVector(currentBody.getMass() * defaultJumpHeight, -PI/2));
    }

    public void move(int id, double direction) {
        PhysicsObject currentBody = gameObjects.get(id);
        currentBody.setDirection(direction);
        currentBody.addCurrentForce(new PhysicsVector(currentBody.getMass() * defaultMovementSpeed, direction));
    }
}
