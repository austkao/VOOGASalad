package combatSystem.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import combatSystem.internal.JumpSuccessfulEvent;
import combatSystem.internal.PlayerManager;
import messenger.external.EventBusFactory;

public class CombatSystem {

    EventBus eventBus;
    PlayerManager playerManager;

    public CombatSystem(){
        eventBus = EventBusFactory.getEventBus();
    }

    @Subscribe
    public void sendMessage(JumpSuccessfulEvent event){

//        eventBus.post();
    }

}
