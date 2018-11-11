package input.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.Internal.Parser;
import messenger.external.EventBusFactory;
import messenger.external.TestSuccesfulEvent;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

public class InputSystem {

    EventBus myMessageBus;
    Parser myParser;
    Queue<KeyEvent> CommandHolder;

    public InputSystem(){
        myMessageBus = EventBusFactory.getEventBus();
        myParser = new Parser();
        CommandHolder = new LinkedList<>();
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

    /*
     / The other subcribe event that listens for Key Inputs.
     */
    @Subscribe
    public void getKey(InputEvent inputEvent){

    }

}
