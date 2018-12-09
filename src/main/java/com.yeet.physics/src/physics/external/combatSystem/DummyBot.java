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
    public void step() {
        PlayerState currentState = this.getPlayerState();
        int row = map.get(currentState);
        PlayerState nextState = getNextState(matrix[row]);
        takeActionBasedOnNextState(nextState);
    }

    @Override
    public void transition() {

    }





}
