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

    public CombatSystem(){
        eventBus = EventBusFactory.getEventBus();
    }

    @Subscribe
    public void onJumpEvent(JumpEvent event){
        Player player = new Player();
        int id = event.getInitiatorID();
        player.changePlayerStateOnEvent(event);
    }

}
