package physics.external.combatSystem;


import com.google.common.eventbus.EventBus;
import messenger.external.Event;
import messenger.external.EventBusFactory;

public class Tester {

    public static void main(String[] args){

        Bot bot = new DummyBot();
        CombatSystem system = new CombatSystem(bot);

        EventBus eventBus = EventBusFactory.getEventBus();
        EventBusFactory.getEventBus().register(system);

        for(int i = 0; i < 10; i++){
            bot.transition();
//            System.out.println(bot.getPlayerState());
        }

    }

}
