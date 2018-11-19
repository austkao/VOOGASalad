package messenger.external;

public class MoveSuccessfulEvent extends SuccessfulEvent{

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
