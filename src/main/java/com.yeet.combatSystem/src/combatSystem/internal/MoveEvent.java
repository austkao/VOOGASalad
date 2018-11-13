package combatSystem.internal;

public class MoveEvent extends CombatActionEvent{
    public MoveEvent(int initiatorID, PlayerState playerState) {
        super(initiatorID, playerState);
    }

    @Override
    void onSuccess() {

    }

    @Override
    void onFailure() {

    }
}
