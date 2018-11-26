package physics.external;

public class PhysicsBody extends PhysicsObject {

    public PhysicsBody(double mass, Coordinate start, Dimensions dims){
        super(mass, start, dims);
    }

    @Override
    public boolean isPhysicsBody(){
        return true;
    }

}