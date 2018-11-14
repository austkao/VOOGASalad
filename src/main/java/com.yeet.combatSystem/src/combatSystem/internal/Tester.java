package combatSystem.internal;

import combatSystem.external.CombatSystem;
import messenger.external.Event;
import messenger.external.EventBusFactory;

public class Tester {

    public static void main(String[] args){
        CombatSystem combatSystem = new CombatSystem();
        EventBusFactory.getEventBus().register(combatSystem);
        JumpEvent event = new JumpEvent(1);
        EventBusFactory.getEventBus().post(event);
        EventBusFactory.getEventBus().post(event);
        EventBusFactory.getEventBus().post(event);
    }
}
