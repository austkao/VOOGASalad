package combatSystem.internal;

public class JumpEvent extends CombatActionEvent {

    JumpResponseEvent event;

    public JumpEvent(int initiatorID) {
        super(initiatorID, PlayerState.SINGLE_JUMP);
    }

    @Override
    void onSuccess() {
        System.out.println("Fuck");
        event = new JumpResponseEvent(initiatorID, true);
    }

    @Override
    void onFailure() {
        event = new JumpResponseEvent(initiatorID, false);
    }

    public boolean getResponse(){
        return event.getResult();
    }

    @Override
    public String getName() {
        return String.format("Player with id(%d) attempts to jump.", initiatorID);
    }
}
