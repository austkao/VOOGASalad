package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.PI;

public class CombatSystem {

    EventBus eventBus;
    PlayerManager playerManager;
    PhysicsSystem physicsSystem;

    public CombatSystem(HashMap<Integer, Point2D> playerMap, HashMap<Integer, Rectangle2D> tileMap, PhysicsSystem physicsSystem){
        eventBus = EventBusFactory.getEventBus();
        playerManager = new PlayerManager(playerMap.keySet().size());
        this.physicsSystem = physicsSystem;
        // register players to physics engine
        for(int i = 0; i < playerMap.keySet().size(); i++){
            physicsSystem.addPhysicsObject(0, PhysicsSystem.defaultMass, playerMap.get(i).getX(), playerMap.get(i).getY(), 30, 70);
        }
        // register tiles to physics engine
        for(int i=0;i < tileMap.keySet().size(); i++){
            System.out.println("TILE WIDTH: " + tileMap.get(i).getWidth());
            System.out.println("TILE HEIGHT: " + tileMap.get(i).getHeight());
            System.out.println("TILE POSX: " + tileMap.get(i).getX());
            System.out.println("TILE POSY: " + tileMap.get(i).getY());
            physicsSystem.addPhysicsObject(2,PhysicsSystem.defaultMass, tileMap.get(i).getX(),tileMap.get(i).getY(),tileMap.get(i).getWidth(),tileMap.get(i).getHeight());
        }

    }

    @Subscribe
    public void onCombatEvent(CombatActionEvent event){
        int id = event.getInitiatorID();
        playerManager.changePlayerStateByIDOnEvent(id, event);
    }

    @Subscribe
    public void onAttackSuccessfulEvent(AttackSuccessfulEvent event){
        physicsSystem.attack(event.getInitiatorID());
    }

    @Subscribe
    public void onMoveSuccessfulEvent(MoveSuccessfulEvent event){
        boolean direction = event.getDirection();
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
        for(List<Integer> list: event.getAttackPlayers()){
            Player playerBeingAttacked = playerManager.getPlayerByID(list.get(0));
            Player playerAttacking = playerManager.getPlayerByID(list.get(1));
            playerAttacking.addAttackingTargets(playerBeingAttacked);
        }
    }

    @Subscribe
    public void onJumpSuccessfulEvent(JumpSuccessfulEvent event){
        physicsSystem.jump(event.getInitiatorID());
    }

}
