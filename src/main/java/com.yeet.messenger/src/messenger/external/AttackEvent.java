package messenger.external;

public class AttackEvent extends CombatActionEvent{

    public AttackEvent(int initiatorID){
        super(initiatorID, PlayerState.ATTACKING);
    }

    @Override
    void onSuccess() {
        eventBus.post(new AttackSuccessfulEvent(initiatorID));
    }

    @Override
    void onFailure() {

    }
}
