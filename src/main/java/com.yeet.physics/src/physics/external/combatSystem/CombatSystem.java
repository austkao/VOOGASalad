package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;

public class CombatSystem {

    EventBus eventBus;
    PlayerManager playerManager;
    PhysicsSystem physicsSystem;

    public CombatSystem(int numOfPlayers){
        eventBus = EventBusFactory.getEventBus();
        playerManager = new PlayerManager(numOfPlayers);
        physicsSystem = new PhysicsSystem();
        physicsSystem.addPhysicsBodies(numOfPlayers);
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

    }

    @Subscribe
    public void onJumpSuccessfulEvent(JumpSuccessfulEvent event){

    }

}
