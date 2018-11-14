package combatSystem.internal;

public class AttackSuccessfulEvent extends SuccessfulEvent{

    public AttackSuccessfulEvent(int initiatorID){
        super(initiatorID);
    }

    @Override
    public String getName() {
        return null;
    }
}
