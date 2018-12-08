package physics.external.combatSystem;

import messenger.external.*;

import java.util.Random;

public class DummyBot extends Bot{

    Double[][] matrix = {{0.25, 0.25, 0.25, 0.25},
                         {0.25, 0.25, 0.25, 0.25},
                         {0.25, 0.25, 0.25, 0.25},
                         {0.25, 0.25, 0.25, 0.25}};

    public DummyBot(){
        super();
        setTransitionMatrix(matrix);
        setPlayerState(PlayerState.MOVING);
    }

    @Override
    public void transition() {
        PlayerState currentState = this.getPlayerState();

        int row = map.get(currentState);
        PlayerState nextState = getNextState(matrix[row]);
        switch (nextState){
            case MOVING:
                moveRandomly();
                break;
            case SINGLE_JUMP:
                eventBus.post(new JumpEvent(id));
                break;
            case ATTACKING:
                eventBus.post(new AttackEvent(id, AttackEvent.WEAK_TYPE));
                break;
            case CROUCH:
                eventBus.post(new CrouchEvent(id));
                break;
        }
    }

    @Override
    protected PlayerState getNextState(Double[] distribution) {
        Random random = new Random();
        double prob = random.nextDouble();
        double cumulated = 0.0;
        int stateIndex = NUM_OF_STATES;
        for(int i = 0; i < distribution.length; i++){
            cumulated += distribution[i];
            if(prob<=cumulated){
                stateIndex = i;
                break;
            }
        }
        return states[stateIndex];
    }

    private void moveRandomly(){
        if(Math.random() < 0.5) {
            eventBus.post(new MoveEvent(id, true));
        }
        else{
            eventBus.post(new MoveEvent(id, false));
        }
    }

}
