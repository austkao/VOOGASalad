package messenger.external;

public class AttackEvent extends CombatActionEvent{

    public AttackEvent(int initiatorID){
        super(initiatorID, PlayerState.ATTACKING);
    }

    @Override
    public void onSuccess() {
        eventBus.post(new AttackSuccessfulEvent(initiatorID));
    }

    @Override
    public void onFailure() {

    }
}
