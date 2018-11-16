package combatSystem.internal;

public class AttackSuccessfulEvent extends SuccessfulEvent{

    public AttackSuccessfulEvent(int initiatorID){
        super(initiatorID);
    }

    @Override
    public String getName() {
        return String.format("Player with id(%d) attacks successfully", initiatorID);
    }
}
