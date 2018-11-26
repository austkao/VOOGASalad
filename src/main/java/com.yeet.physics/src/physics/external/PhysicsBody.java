package physics.external;

public class PhysicsBody extends PhysicsObject {

    PhysicsBody(int id, double mass, Coordinate start, Dimensions dims){
        super(id, mass, start, dims);
    }

    @Override
    boolean isPhysicsBody(){
        return true;
    }

}