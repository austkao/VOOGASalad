package combatSystem.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import combatSystem.internal.JumpEvent;
import combatSystem.internal.Player;
import combatSystem.internal.PlayerManager;
import messenger.external.EventBusFactory;

public class CombatSystem {

    EventBus eventBus;
    PlayerManager playerManager;
    Player player = new Player();

    public CombatSystem(){
        eventBus = EventBusFactory.getEventBus();
    }

    // called when a JumpEvent is posted through the event bus
    @Subscribe
    public void onJumpEvent(JumpEvent event){
        int id = event.getInitiatorID();
        player.changePlayerStateOnEvent(event);
    }

}
