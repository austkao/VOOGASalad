package combatSystem.internal;

public class MoveSuccessfulEvent extends CombatActionEvent{
    public MoveSuccessfulEvent(int initiatorID) {
        super(initiatorID, PlayerState.MOVING);
    }
}
