package player.external;

import com.google.common.eventbus.EventBus;
import messenger.external.MessageBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.MessageBusFactory;
import messenger.external.TestSuccesfulEvent;

public class Player {
    EventBus myMessageBus;

    public Player(){
        myMessageBus = MessageBusFactory.getEventBus();
    }

    public void doSomething(){
        TestSuccesfulEvent testSuccesfulEvent = new TestSuccesfulEvent();
        myMessageBus.post(testSuccesfulEvent);
    }

    @Subscribe
    public  void printMessage(TestSuccesfulEvent testSuccessfulEvent){
        // Simulate sending reciept
        System.out.println("Message bus is a go!");
    }

}
