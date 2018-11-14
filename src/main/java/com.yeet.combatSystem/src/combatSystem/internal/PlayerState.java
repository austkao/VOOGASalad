package combatSystem.internal;

public enum PlayerState {

    INITIAL{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            // when player is in initial state, all action he takes will be successful
            event.onSuccess();
            return event.getInputPlayerState();
        }

    },

    MOVING{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            // when player is moving, all action he takes will be successful
            event.onSuccess();
            return event.getInputPlayerState();
        }
    },

    ATTACKING{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            return INITIAL;
        }
    },

    // the state when the player presses jump button once and still in the air
    SINGLE_JUMP{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            switch (event.getInputPlayerState()){
                case SINGLE_JUMP:
                    event.onSuccess();
                    return DOUBLE_JUMP;
            }
            return SINGLE_JUMP;
        }
    },

    /* the state when the player presses jump button two times consecutively to
    complete a double jump and still in the air*/
    DOUBLE_JUMP{
        @Override
        PlayerState changeStatesOnEvent(CombatActionEvent event){
            switch (event.getInputPlayerState()){
                // player fails to jump if he already completes a double jump
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
            /* all combat actions (jump, move, attack) the player takes while he is
                being attacked is invalid except when the event resets player's state
                to INITIAL */
            switch (event.getInputPlayerState()){
                case INITIAL: return INITIAL;
            }
            return BEING_ATTACKED;
        }
    };

    abstract PlayerState changeStatesOnEvent(CombatActionEvent event);

}
