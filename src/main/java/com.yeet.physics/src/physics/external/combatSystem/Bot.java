package physics.external.combatSystem;
import com.google.common.eventbus.EventBus;
import messenger.external.EventBusFactory;
import messenger.external.PlayerState;

import java.util.HashMap;
import java.util.Map;

public abstract class Bot extends Player{

    // how many possible states to step to
    // moving, move, jump, attack, crouch
    protected PlayerState[] states = {PlayerState.MOVING, PlayerState.SINGLE_JUMP, PlayerState.ATTACKING, PlayerState.CROUCH};
    protected static Map<PlayerState, Integer> map;
    protected static EventBus eventBus = EventBusFactory.getEventBus();
    protected final int NUM_OF_STATES = states.length;
    Double[][] transitionMatrix;
    PlayerGraph playerGraph;

    protected Bot(){
        setIsBot(true);
    }

    static{
        map = new HashMap<>();
        map.put(PlayerState.MOVING, 0);
        map.put(PlayerState.SINGLE_JUMP, 1);
        map.put(PlayerState.ATTACKING, 2);
        map.put(PlayerState.CROUCH, 3);
    }

    protected void setTransitionMatrix(Double[][] transitionMatrix){
        if(transitionMatrix.length!=transitionMatrix[0].length){
            throw new IllegalArgumentException("Not a square matrix");
        }
        else if(transitionMatrix.length!=NUM_OF_STATES){
            throw new IllegalArgumentException(String.format("Wrong matrix size. Expected: %d Actual: %d", NUM_OF_STATES, transitionMatrix.length));
        }
        this.transitionMatrix = transitionMatrix;
    }

    public void setPlayerGraph(PlayerGraph playerGraph){
        this.playerGraph = playerGraph;
    }


    /* implement this method to define the rule of transitioning
        from initial state to a new state */
    public abstract void step();
    public abstract void transition();
    /* determine what the next state is based on a probability distribution */
    protected abstract PlayerState getNextState(Double[] distribution);


}
