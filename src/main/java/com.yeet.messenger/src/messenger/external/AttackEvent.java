package messenger.external;

import java.util.HashSet;
import java.util.Set;

public class AttackEvent extends CombatActionEvent{

    private static final String STRONG_TYPE = "STRONG";
    private static final String WEAK_TYPE = "WEAK";
    private static Set<String> types = new HashSet<>();
    private String type;

    static{
        types.add(STRONG_TYPE);
        types.add(WEAK_TYPE);
    }

    public AttackEvent(int initiatorID){
        super(initiatorID, PlayerState.ATTACKING);
    }

    public AttackEvent(int initiatorID, String type){
        super(initiatorID, PlayerState.ATTACKING);
        if(!types.contains(type)){
            throw new RuntimeException(String.format("Invalid string property for attack event: %s", type));
        }
        this.type = type;
    }

    @Override
    public void onSuccess() {
        eventBus.post(new AttackSuccessfulEvent(initiatorID));
    }

    @Override
    public void onFailure() {

    }
}
