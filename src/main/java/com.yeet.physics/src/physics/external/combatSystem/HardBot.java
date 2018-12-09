package physics.external.combatSystem;

import com.google.common.eventbus.Subscribe;
import messenger.external.AttackEvent;
import messenger.external.CombatActionEvent;
import messenger.external.MoveEvent;
import messenger.external.PlayerState;
import physics.external.PhysicsSystem;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class HardBot extends NormalBot {

    Queue<CombatActionEvent> pattern;
    Player target;
    boolean inRange = false;

    Double[][] defensive = {{0.8, 0.19, 0.01, 0.0},
                            {0.49, 0.5, 0.01, 0.0},
                            {},
                            {}};

    Double[][] players = {{0.8, 0.19, 0.01, 0.0},
            {0.49, 0.5, 0.01, 0.0},
            {},
            {}};

    public HardBot(PhysicsSystem physicsSystem){
        super(physicsSystem);
        pattern = new LinkedList<>();
        eventBus.register(this);
    }

    @Override
    public void step() {
        Map<Integer, Point2D> positions = physicsSystem.getPositionsMap();
        playerGraph.updatePositionMap(positions);

        double healthDiff = target.getHealth()-this.getHealth();
        /* if the bot is low on health compared to its target,
            switch to defensive mode */
        if(healthDiff>=50.0){
            setTransitionMatrix(defensive);
        }
        else{
            setTransitionMatrix(matrix);
        }

        Point2D targetPos = playerGraph.getNearestNeighbor(this.id);
        target = playerGraph.findPlayerByPosition(targetPos);
        if(!playerGraph.hasEnemyNearBy(this.id, DETECTION_RADIUS)){
            inRange = false;
            moveCloserToTarget(target, positions);
        }
        else{
            inRange = true;
            this.setPlayerState(PlayerState.ATTACKING);
            transition();
        }
    }

    @Subscribe
    public void onPlayerMovement(CombatActionEvent combatActionEvent){
        if(combatActionEvent.getInitiatorID()==target.id){
            switch (combatActionEvent.getInputPlayerState()){
                case MOVING:
                    if(inRange) eventBus.post(new AttackEvent(this.id, AttackEvent.WEAK_TYPE));
                    break;
                case ATTACKING:
                    if(inRange){
                        // determine the dodge direction
                        boolean direction = playerGraph.getFacingDirection(this.id, target.id);
                        // dodge the opponent's attack
                        eventBus.post(new MoveEvent(this.id, direction));
                    }
                case SINGLE_JUMP:
            }
        }
    }

    private void recordPlayerMoves(CombatActionEvent combatActionEvent){
        pattern.offer(combatActionEvent);
        if(pattern.size()>=10){

        }
    }

}
