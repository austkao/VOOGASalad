package messenger.external;

public class AttackSuccessfulEvent extends SuccessfulEvent{

    public AttackSuccessfulEvent(int initiatorID){
        super(initiatorID);
    }

    @Override
    public String getName() {
        return String.format("Attack Successful Event: Player with id(%d) attacks successfully", initiatorID);
    }
}
