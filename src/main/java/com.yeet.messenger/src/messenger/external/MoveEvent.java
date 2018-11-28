package messenger.external;

public class MoveEvent extends CombatActionEvent{

    // indicate the direction in which the player move
    boolean isGoingLeft;

    public MoveEvent(int initiatorID, boolean isGoingLeft) {
        super(initiatorID, PlayerState.MOVING);
        this.isGoingLeft = isGoingLeft;
    }

    @Override
    public void onSuccess() {
        eventBus.post(new MoveSuccessfulEvent(initiatorID, isGoingLeft));
    }

    @Override
    public void onFailure() {

    }
}
