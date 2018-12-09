package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;

public class CombatSystem {

    EventBus eventBus;
    private PlayerManager playerManager;
    private PhysicsSystem physicsSystem;
    private List<Integer> botList;
    private HashMap<Integer, Point2D> playerMap;

    public CombatSystem(Player bot){
        eventBus = EventBusFactory.getEventBus();
        bot.id = 1;
        playerManager = new PlayerManager(1);
    }

    public CombatSystem(HashMap<Integer, Point2D> playerMap, HashMap<Integer, Rectangle2D> tileMap, PhysicsSystem physicsSystem){
        eventBus = EventBusFactory.getEventBus();
        this.playerMap = playerMap;
//        playerManager = new PlayerManager(playerMap.keySet().size());
        this.physicsSystem = physicsSystem;
        // register players to physics engine
        for(int i = 0; i < playerMap.keySet().size(); i++){
            physicsSystem.addPhysicsObject(0, PhysicsSystem.DEFAULT_MASS, playerMap.get(i).getX(), playerMap.get(i).getY(),40,60);
        }
        // register tiles to physics engine
        for(int i=0;i < tileMap.keySet().size(); i++){
            physicsSystem.addPhysicsObject(2,0, tileMap.get(i).getX(),tileMap.get(i).getY(),tileMap.get(i).getWidth(),tileMap.get(i).getHeight());
        }
    }

    /** Returns the {@code PlayerState} of the player specified
     *  @param id The player to retrieve the state for
     */
    public PlayerState getPlayerState(int id){
        return playerManager.getPlayerByID(id).getPlayerState();
    }

    @Subscribe
    public void onCombatEvent(CombatActionEvent event){
        int id = event.getInitiatorID();
        if(!botList.contains(id)){
            playerManager.changePlayerStateByIDOnEvent(id, event);
        }
    }

    @Subscribe
    public void onIdleEvent(IdleEvent idleEvent){
        int id = idleEvent.getId();
        if(playerManager.getPlayerByID(id).getPlayerState()!=PlayerState.SINGLE_JUMP
                && playerManager.getPlayerByID(id).getPlayerState()!=PlayerState.DOUBLE_JUMP){
            playerManager.setToInitialStateByID(id);
        }
    }

    @Subscribe
    public void onAttackSuccessfulEvent(AttackSuccessfulEvent event){
//        System.out.println("Attack!!!");
        physicsSystem.attack(event.getInitiatorID());
    }

    @Subscribe
    public void onMoveSuccessfulEvent(MoveSuccessfulEvent event){
        boolean direction = event.getDirection();
//        System.out.println("Move" + direction);
        // move left
        if(direction){
            physicsSystem.move(event.getInitiatorID(), PI);
        }
        // move right
        else{
            physicsSystem.move(event.getInitiatorID(), 0);
        }
    }

    @Subscribe
    public void onGroundIntersectEvent(GroundIntersectEvent event){
        List<Integer> playersOnGround = event.getGroundedPlayers();
        for(int id: playersOnGround){
            playerManager.setToInitialStateByID(id);
        }
    }

    @Subscribe
    public void onAttackIntersectEvent(AttackIntersectEvent event){
        Map<Integer, Double> playersBeingRekt = new HashMap<>();
        for(List<Integer> list: event.getAttackPlayers()){
            Player playerBeingAttacked = playerManager.getPlayerByID(list.get(0));
            Player playerAttacking = playerManager.getPlayerByID(list.get(1));
            playerAttacking.addAttackingTargets(playerBeingAttacked);
            double health = playerBeingAttacked.reduceHealth(playerAttacking.getAttackDamage());
            //TODO: do something when the player's health drops to below 0.0
            if(health<=0.0){

            }
            playersBeingRekt.put(playerBeingAttacked.id, health);
        }
        eventBus.post(new GetRektEvent(playersBeingRekt));
    }

    @Subscribe
    public void onJumpSuccessfulEvent(JumpSuccessfulEvent event){
        physicsSystem.jump(event.getInitiatorID());
    }

    @Subscribe
    public void onGameStart(GameStartEvent gameStartEvent){
        botList = gameStartEvent.getBots();
        playerManager = new PlayerManager(playerMap.size());
        playerManager.setBots(botList, physicsSystem);
        PlayerGraph graph = new PlayerGraph(playerManager, physicsSystem.getPositionsMap());
        for(int id: botList){
            ((Bot)playerManager.getPlayerByID(id)).setPlayerGraph(graph);
        }
        for(int id: botList){
            ((DummyBot)playerManager.getPlayerByID(id)).start();
        }
    }

//    @Subscribe


}