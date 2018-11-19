package combatSystem.internal;

import combatSystem.external.CombatSystem;
import messenger.external.CombatActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.JumpEvent;

public class Tester {

    public static void main(String[] args){
        CombatSystem combatSystem = new CombatSystem(4);
        EventBusFactory.getEventBus().register(combatSystem);
        CombatActionEvent event = new JumpEvent(1);
        EventBusFactory.getEventBus().post(event);
        EventBusFactory.getEventBus().post(event);
        EventBusFactory.getEventBus().post(event);
    }
}
