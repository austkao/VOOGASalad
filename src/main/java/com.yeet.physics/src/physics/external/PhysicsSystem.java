package physics.external;

import com.google.common.eventbus.EventBus;
import messenger.external.AttackIntersectEvent;
import messenger.external.EventBusFactory;
import messenger.external.GroundIntersectEvent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;


public class PhysicsSystem {

    public static final double defaultMass = 50;
    public static final double defaultStrength = 20;
    public static final double defaultJumpHeight = 100;
    public static final double defaultMovementSpeed = 3000;
    public static final double defaultAttackSpace = 10;

    private int playerId;
    private int groundId;
    private int attackId;


    List<PlayerCharacteristics> playerCharacteristics = new ArrayList<>();

    Map<Integer, PhysicsObject> gameObjects = new HashMap<>();

    private EventBus myMessageBus;

    public PhysicsSystem() {
        this.myMessageBus = EventBusFactory.getEventBus();
        this.playerId = 0;
        this.attackId = 100;
        this.groundId = 1000;
    }

    public void update() {
        PassiveForceHandler passHandler = new PassiveForceHandler(gameObjects);
        passHandler.update();
        CollisionDetector detector = new CollisionDetector(gameObjects);
        List<Collision> collisions = new ArrayList<>(detector.detectCollisions(gameObjects));
        //MovementHandler movHandler = new MovementHandler(gameObjects);
        //movHandler.update(); //How does this work with subscribe?
        CollisionHandler collHandler = new CollisionHandler(collisions);
        collHandler.update();
        List<Integer> groundCollisions = collHandler.getGroundCollisions();
        List<List<Integer>> attackCollisions = collHandler.getAttackCollisions();
        applyForces();
        updatePositions();
        //PositionsUpdateEvent newPos = new PositionsUpdateEvent(getPositionsMap(), getDirectionsMap()); //Parameter is hashmap with integer as key and Point2D as value
        //myMessageBus.post(newPos);
        GroundIntersectEvent groundedPlayers = new GroundIntersectEvent(groundCollisions);
        if (groundedPlayers.getGroundedPlayers().size() > 0) {
            myMessageBus.post(groundCollisions);
        }
        AttackIntersectEvent attackPlayers = new AttackIntersectEvent(attackCollisions);
        if (attackPlayers.getAttackPlayers().size() > 0) {
            myMessageBus.post(attackCollisions);
        }

    }

    public void addPhysicsObject(int type, double mass, double XCoordinate, double YCoordinate, double XDimension, double YDimension) { // type 0: player, type 1: attack, type 2: ground
        int id;
        if (type == 0) {
            id = playerId;
            gameObjects.put(id, new PhysicsBody(id, mass, new Coordinate(XCoordinate,YCoordinate), new Dimensions(XDimension,YDimension)));
            playerId++;
        } else if (type == 1) {
            id = attackId;
            gameObjects.put(id, new PhysicsAttack(id, mass, new Coordinate(XCoordinate,YCoordinate), new Dimensions(XDimension,YDimension)));
            attackId++;
        } else {
            id = groundId;
            gameObjects.put(id, new PhysicsGround(id, mass, new Coordinate(XCoordinate,YCoordinate), new Dimensions(XDimension,YDimension)));
            groundId++;
        }
        playerCharacteristics.add(new PlayerCharacteristics(id, defaultStrength, defaultJumpHeight, defaultMovementSpeed));
    }

    public void applyForces() {
        for (PhysicsObject o : gameObjects.values()) {
            if(o.getId() == 1){
            System.out.println("ApplyForces: (before force applied) " + o.getVelocity().getMagnitude());
            System.out.println("ApplyForces: (before force applied) " + o.getVelocity().getDirection());}
            if (!o.isPhysicsGround()) {
                NetVectorCalculator calc = new NetVectorCalculator(o.getCurrentForces());
                o.applyForce(calc.getNetVector());

                if (o.getId() == 1) {
                    System.out.println("ID 1 Net Vector: " + calc.getNetVector().getMagnitude() + ", " + calc.getNetVector().getDirection());
                }

                o.clearCurrentForces();
            }
            if(o.getId() == 1){
            System.out.println("ApplyForces: " + o.getVelocity().getMagnitude());
            System.out.println("ApplyForces: " + o.getVelocity().getDirection());}
        }

    }

    public void updatePositions() {
        PositionCalculator calc = new PositionCalculator(gameObjects);
        calc.updatePositions();
    }

    public Map<Integer, Point2D> getPositionsMap() {
        Map<Integer, Point2D> out = new HashMap<>();
        for(PhysicsObject obj: gameObjects.values()){
            if (!obj.isPhysicsGround()) {
                Point2D.Double point = new Point2D.Double(obj.getMyCoordinateBody().getPos().getX(), obj.getMyCoordinateBody().getPos().getY());
                out.put(obj.getId(), point);
            }
        }
        return out;
    }

    public Map<Integer, Double> getDirectionsMap() {
        Map<Integer, Double> out = new HashMap<>();
        double direction;
        for(PhysicsObject obj: gameObjects.values()){
            if (obj.isPhysicsBody()) {
                direction = obj.getDirection();
                out.put(obj.getId(), direction);
            }
        }
        return out;
    }

    public Map<Integer, PhysicsObject> getGameObjects() {
        return this.gameObjects;
    }

    public void jump(int id) {
        PhysicsObject currentBody = gameObjects.get(id);
        currentBody.setGrounded(false);
        currentBody.addCurrentForce(new PhysicsVector(currentBody.getMass() * defaultJumpHeight, -PI/2));
        System.out.println("JUMP INITIATED");
    }

    public void move(int id, double direction) {
        PhysicsObject currentBody = gameObjects.get(id);
        currentBody.setDirection(direction);
        System.out.println(direction);
        currentBody.addCurrentForce(new PhysicsVector(currentBody.getMass() * defaultMovementSpeed, direction));
    }

    public void attack(int id) {
        int direction;
        if (gameObjects.get(id).getDirection() == 0) {
            direction = 1;
        } else {
            direction = -1;
        }
        Coordinate playerLocation = gameObjects.get(id).getMyCoordinateBody().getPos();
        Coordinate attackLocation = new Coordinate(playerLocation.getX() + direction * defaultAttackSpace,playerLocation.getY() + defaultAttackSpace);
        PhysicsAttack attack = new PhysicsAttack(id,gameObjects.get(id).getMass(), attackLocation,new Dimensions(20, 10));
        gameObjects.put(id, attack);
    }
}
