package physics.external;

import java.util.*;

import static java.lang.Math.PI;
import static physics.external.PassiveForceHandler.DEFAULT_GRAVITY_ACCELERATION;

public class CollisionHandler {

    public static double defaultAttackMagnitude = 2000000;
    public static final double timeOfFrame = 0.016666666; // Assume each frame is 1/8 of a sec
    public static final double frictionCoefficient = .5;

    private List<Collision> myCollisions;
    private List<Integer> groundCollisions = new ArrayList<>();
    private List<List<Integer>> attackCollisions = new ArrayList<>();

    public CollisionHandler(List<Collision> collisions){
        filterCollisions(collisions);
    }

    private void filterCollisions(List<Collision> collisions) {
        Iterator<Collision> collisionIterator = collisions.iterator();
        Set<PhysicsObject> groundCols = new HashSet<>();
        this.myCollisions = new ArrayList<>();
        //Filter collisions so that only one ground collides with one body
        while(collisionIterator.hasNext()){
            Collision col = collisionIterator.next();
            PhysicsObject one = col.getCollider1();
            PhysicsObject two = col.getCollider2();
            if (one.isPhysicsBody() && two.isPhysicsGround()) {
                if (one.getId() == 1)
                    this.printSides(col.getIntersection().getSides());
                    System.out.println("DIRECTION: " + col.getIntersection().getCollisionDirection());
                filterGroundCollisions(groundCols, one, col);
            } else if (one.isPhysicsBody() && two.isPhysicsAttack()) {
                filterAttackCollisions(one, two, col);
            }
        }
    }

    private void printSides(List<Side> sides){
        for(Side s: sides){
            System.out.println(s.getMySide());
        }
    }

    private void filterGroundCollisions(Set<PhysicsObject> groundCols, PhysicsObject one, Collision col) {
        if (!groundCols.contains(one)) {
            groundCols.add(one);
            this.myCollisions.add(col);
        }
    }

    private void filterAttackCollisions(PhysicsObject one, PhysicsObject two, Collision col) {
        if (!(one.getId() == two.getParentID())) {
            this.myCollisions.add(col);
        }
    }

    public void update() {
        for (Collision c : myCollisions) {
            PhysicsObject one, two;
            one = c.getCollider1();
            two = c.getCollider2();
            // body+attack
            if(one.isPhysicsBody() && two.isPhysicsAttack()){
                System.out.println("Successful Hit Attack");
                PhysicsVector force = new PhysicsVector(defaultAttackMagnitude, two.getDirection());
                one.addCurrentForce(force);
                System.out.println("CH Current Forces:");
                for (PhysicsVector f : one.getCurrentForces()) {
                    System.out.println(f.getMagnitude() + ", " + f.getDirection());
                }
                List<Integer> collisions = new ArrayList<>();
                collisions.add(one.getId());
                collisions.add(two.getId());
                attackCollisions.add(collisions);
            }
            // body+ground
            if(one.isPhysicsBody() && two.isPhysicsGround()){

                if (one.getId() == 1) {
                    for (Side s : c.getIntersection().getSides()) {
                        System.out.print(s.getMySide() + ", ");
                    }
                    System.out.println();
                }

                double bodyMass = one.getMass();
                double intersectionDirection = c.getIntersection().getCollisionDirection();
                double bodyVelocity;
                System.out.println("sides in one: ");
                for(Side s: c.getIntersection().getSides()){
                    System.out.print(s.getMySide());

                }
                System.out.println();
                if (c.getIntersection().containsSide(new Side("BOTTOM"))) {
                    System.out.println("ADDING BOTTOM FORCE");
                    PhysicsVector gravityOpposition = new PhysicsVector(Math.round(one.getMass() * DEFAULT_GRAVITY_ACCELERATION), -PI / 2);
                    PhysicsVector yForceCancel = new PhysicsVector(bodyMass*one.getYVelocity().getMagnitude()/timeOfFrame, -PI/2);
                    one.addCurrentForce(yForceCancel);
                    one.addCurrentForce(gravityOpposition);
                }

                if (c.getIntersection().containsSide(new Side("TOP"))){
                    PhysicsVector yForceCancel = new PhysicsVector(-bodyMass*one.getYVelocity().getMagnitude()/timeOfFrame, PI/2);
                    one.addCurrentForce(yForceCancel);
                }


                if(Math.abs(one.getXVelocity().getMagnitude()) > 10) { //Should we apply kinetic friction?
                    PhysicsVector friction;
                    if(one.getXVelocity().getMagnitude() > 0) {
                        friction = new PhysicsVector((int) -one.getMass() * DEFAULT_GRAVITY_ACCELERATION * frictionCoefficient, 0);
                    }else{
                        friction = new PhysicsVector((int) one.getMass() * DEFAULT_GRAVITY_ACCELERATION * frictionCoefficient, 0);
                    }
                    one.addCurrentForce(friction);
                }else{
                    PhysicsVector staticFriction;
                    bodyVelocity = one.getXVelocity().getMagnitude();
                    staticFriction = new PhysicsVector(-bodyMass*bodyVelocity/timeOfFrame, 0);
                    one.addCurrentForce(staticFriction);
                }
                groundCollisions.add(one.getId());
            }
        }
    }

    public void applyFriction(){

    }

    public List<Integer> getGroundCollisions() {
        return groundCollisions;
    }

    public List<List<Integer>> getAttackCollisions() {
        return attackCollisions;
    }



}
