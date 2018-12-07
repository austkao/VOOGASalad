package messenger.external;

public class CrouchEvent extends CombatActionEvent {
    public CrouchEvent(int initiatorID) {
        super(initiatorID, PlayerState.CROUCH);
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
