package physics.external;

public class PhysicsBody extends PhysicsObject {

    PhysicsBody(double mass, Coordinate start, Dimensions dims){
        super(mass, start, dims);
    }

    @Override
    boolean isPhysicsBody(){
        return true;
    }

}