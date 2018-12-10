package physics.external;

public abstract class PhysicsAttack extends PhysicsObject {

    private int myParentID;

    PhysicsAttack(int id, int parentID, double direction, double mass, Coordinate start, Dimensions dims) {
        super(id, mass, start, dims);
        this.myParentID = parentID;
        this.myDirection = direction;
    }

    @Override
    public int getParentID() { return myParentID; }

    @Override
    public boolean isPhysicsAttack() {
        return true;
    }
}
