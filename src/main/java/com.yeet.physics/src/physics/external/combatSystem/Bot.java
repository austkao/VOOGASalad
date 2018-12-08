package physics.external.combatSystem;
import messenger.external.PlayerState;

public abstract class Bot extends Player{

    // how many possible states to transition to
    // moving, move, jump, attack, crouch
    protected PlayerState[] states = {PlayerState.MOVING, PlayerState.SINGLE_JUMP, PlayerState.ATTACKING, PlayerState.CROUCH};
    protected final int NUM_OF_STATES = states.length;
    double[][] transitionMatrix;

    protected abstract void transition();
    protected abstract void rand();

    public void test(){

    }

}
