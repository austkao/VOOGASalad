package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;
import xml.XMLParser;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.PI;

public class CombatSystem {

    EventBus eventBus;
    PlayerManager playerManager;
    PhysicsSystem physicsSystem;

    public CombatSystem(int numOfPlayers){
        eventBus = EventBusFactory.getEventBus();
        playerManager = new PlayerManager(numOfPlayers);
        physicsSystem = new PhysicsSystem();
        physicsSystem.addPhysicsBodies(numOfPlayers);
//        XMLParser parser = new XMLParser();
//        HashMap<String, ArrayList<String>> map = parser.parseFileForElement("character");
    }

    @Subscribe
    public void onCombatEvent(CombatActionEvent event){
        int id = event.getInitiatorID();
        playerManager.changePlayerStateByIDOnEvent(id, event);
    }

    @Subscribe
    public void onAttackSuccessfulEvent(AttackSuccessfulEvent event){

    }

    @Subscribe
    public void onMoveSuccessfulEvent(MoveSuccessfulEvent event){
        boolean direction = event.getDirection();
        // move left
        if(direction){
            physicsSystem.move(event.getInitiatorID(), PI);
        }
        else{
            physicsSystem.move(event.getInitiatorID(), 0);
        }

    }

    @Subscribe
    public void onJumpSuccessfulEvent(JumpSuccessfulEvent event){
        physicsSystem.jump(event.getInitiatorID());
    }

}
