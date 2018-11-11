package input.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.Internal.Parser;
import messenger.external.EventBusFactory;
import messenger.external.TestSuccesfulEvent;

public class InputSystem {

    EventBus myMessageBus;
    Parser myParser;

    public InputSystem(){
        myMessageBus = EventBusFactory.getEventBus();
        myParser = new Parser();
    }

    /*
     My method for sending out information regarding inputs. I'll have to create
     a class that represents an attack input
     */
    public void doSomething(){
        ActionEvent actionEvent = new ActionEvent("Input System Linked");
        myMessageBus.post(actionEvent);
    }


    /* My subscribe method. It is used to receive messages regarding conditional inputs
    / I will only receive messages from the Combat System (messages about fighters being at a certain threshold)
     */
    @Subscribe
    public  void printMessage(TestSuccesfulEvent testSuccessfulEvent){
        // Simulate sending reciept
        System.out.println("Player: Test successful!");
    }

}
