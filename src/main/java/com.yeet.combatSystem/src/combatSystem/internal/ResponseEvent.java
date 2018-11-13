package combatSystem.internal;

import messenger.external.Event;

public abstract class ResponseEvent extends Event {

    int initiatorID;
    boolean isSuccessful;

    protected ResponseEvent(int id, boolean result){
        this.initiatorID = id;
        this.isSuccessful = result;
    }

    public boolean getResult(){
        return isSuccessful;
    }
}
