package combatSystem.internal;

public class JumpEvent extends CombatActionEvent {

    public JumpEvent(int initiatorID) {
        super(initiatorID, PlayerState.SINGLE_JUMP);
    }

    @Override
    void onSuccess() {
        eventBus.post(new JumpSuccessfulEvent(initiatorID));
    }

    @Override
    void onFailure() {
        System.out.println(String.format("Player with id(%d) fails to jump.", initiatorID));
//        event = new JumpSuccessfulEvent(initiatorID, false);
    }

    @Override
    public String getName() {
        return String.format("Player with id(%d) attempts to jump.", initiatorID);
    }
}
