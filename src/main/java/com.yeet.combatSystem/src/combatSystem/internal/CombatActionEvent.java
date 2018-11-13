package combatSystem.internal;

import messenger.external.Event;

public abstract class CombatActionEvent extends Event {

    protected int initiatorID;
    protected PlayerState nextPlayerState;

    protected CombatActionEvent(int initiatorID, PlayerState playerState){
        this.initiatorID = initiatorID;
        this.nextPlayerState = playerState;
    }

    public int getInitiatorID(){
        return initiatorID;
    }

    public PlayerState getNextPlayerState(){
        return nextPlayerState;
    }

    abstract void onSuccess();
    abstract void onFailure();

    @Override
    public String getName() {
        return null;
    }
}
