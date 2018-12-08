package physics.external.combatSystem;

import com.google.common.eventbus.Subscribe;
import messenger.external.CombatActionEvent;
import messenger.external.PlayerState;
import physics.external.PhysicsSystem;

import java.awt.geom.Point2D;
import java.util.*;

/*
    What can this bot do? More intelligent than the dummy bot:
//        when health is high:
    1. get closer to the nearest player (how to determine which one)
    2.
                Move Jump Attack Crouch
        Move  { 0.8  0.1   0.01   0.09 }
        Jump  {                        }
        Attack{                        }
        Crouch{                        }
 */

public class NormalBot extends Bot {
    Queue<CombatActionEvent> queue = new LinkedList<>();
    Queue<Integer> opponents = new PriorityQueue<>();
    PhysicsSystem physicsSystem;

    Double[][] matrix = {{},
                         {},
                         {},
                         {}};

    public NormalBot(){
        super();
        setTransitionMatrix(matrix);
        setPlayerState(PlayerState.MOVING);
    }

    public NormalBot(HashMap<Integer, Point2D> playerMap, PhysicsSystem physicsSystem){
        this.physicsSystem = physicsSystem;

    }

    private void updatePositions(){
        Map<Integer, Point2D> positions = physicsSystem.getPositionsMap();

        for(int id: positions.keySet()){
            if(id==this.id){

            }
            else{

            }
        }
    }

    @Override
    public void step() {

    }

    @Override
    public void transition() {

    }

    @Override
    protected PlayerState getNextState(Double[] distribution) {
        return null;
    }

    @Subscribe
    public void onCombatActionEvent(CombatActionEvent combatActionEvent){
        switch (combatActionEvent.getInputPlayerState()){
            case MOVING: case CROUCH: case SINGLE_JUMP: case ATTACKING:
                queue.offer(combatActionEvent);
        }
    }

    private void recordPlayerMovement(CombatActionEvent combatActionEvent){

    }
}
