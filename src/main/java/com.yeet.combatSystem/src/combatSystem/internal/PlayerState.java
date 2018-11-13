package combatSystem.internal;

public enum PlayerState {

    INITIAL{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            switch (event.getNextPlayerState()){
                case SINGLE_JUMP:
                    event.onSuccess();
                    return SINGLE_JUMP;
            }
            return event.getNextPlayerState();
        }

    },

    MOVING{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            return INITIAL;
        }
    },

    ATTACKING{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            return INITIAL;
        }
    },

    SINGLE_JUMP{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            switch (event.getNextPlayerState()){
                case SINGLE_JUMP:
                    event.onSuccess();
                    return DOUBLE_JUMP;
            }
            return SINGLE_JUMP;
        }
    },

    DOUBLE_JUMP{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            switch (event.getNextPlayerState()){
                case SINGLE_JUMP:
                    event.onFailure();
                    return DOUBLE_JUMP;
            }
            return INITIAL;
        }
    },

    BEING_ATTACKED{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            switch (event.getNextPlayerState()){
                case INITIAL: return INITIAL;
            }
            return BEING_ATTACKED;
        }
    };

    abstract PlayerState changeStatesOnEvent(CombatActionEvent event);

}
