package combatSystem.internal;

public class JumpSuccessfulEvent extends CombatActionEvent {

    public JumpSuccessfulEvent(int initiatorID) {
        super(initiatorID, PlayerState.SINGLE_JUMP);
    }

    @Override
    public String getName() {
        return String.format("Player with id%d jumps successfully", initiatorID);
    }
}
