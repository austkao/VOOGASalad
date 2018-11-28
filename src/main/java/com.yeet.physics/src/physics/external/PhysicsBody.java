package physics.external;

public class PhysicsBody extends PhysicsObject {


    public PhysicsBody(int id, double mass, Coordinate start, Dimensions dims){
        super(id, mass, start, dims);
    }

    @Override
    public boolean isPhysicsBody(){
        return true;
    }

}