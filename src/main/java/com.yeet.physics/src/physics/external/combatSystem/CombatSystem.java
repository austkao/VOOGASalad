package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;
import xml.XMLParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.PI;

public class CombatSystem {

    EventBus eventBus;
    PlayerManager playerManager;
    PhysicsSystem physicsSystem;

    public CombatSystem(int numOfPlayers){
        eventBus = EventBusFactory.getEventBus();
        playerManager = new PlayerManager(numOfPlayers);
        physicsSystem = new PhysicsSystem();
//        physicsSystem.addPhysicsBodies(numOfPlayers);
        for(int i = 0; i < numOfPlayers; i++){
            physicsSystem.addPhysicsObject(0, PhysicsSystem.defaultMass, 0, 0, 50, 50);
        }

//        XMLParser parser = new XMLParser();
//        HashMap<String, ArrayList<String>> map = parser.parseFileForElement("character");
        //physicsSystem.addPhysicsBodies(numOfPlayers);
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
