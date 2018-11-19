package combatSystem.internal;

public class MoveSuccessfulEvent extends SuccessfulEvent{

    // true if player is going left, false if player is going right
    boolean isGoingLeft;

    public MoveSuccessfulEvent(int initiatorID, boolean isGoingLeft) {
        super(initiatorID);
        this.isGoingLeft = isGoingLeft;
    }

    public boolean getDirection(){
        return isGoingLeft;
    }

    @Override
    public String getName() {
        return null;
    }
}
