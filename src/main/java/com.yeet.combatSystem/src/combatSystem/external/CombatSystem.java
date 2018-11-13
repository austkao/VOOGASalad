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

    public void jump(int index){
        JumpEvent event = new JumpEvent(index);
        Player player = new Player();
        player.onCombatActionEvent(event);
        eventBus.post(event);
    }

    @Subscribe
    public void sendMessage(JumpEvent event){
//        System.out.println(event.getName());
        Player player = new Player();
        boolean result = player.onCombatActionEvent(event);
//        System.out.println(event);
    }

}
