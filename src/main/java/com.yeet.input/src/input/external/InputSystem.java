package input.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.Internal.Parser;
import messenger.external.ActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.KeyInputEvent;

import java.util.LinkedList;
import java.util.Queue;

public class InputSystem {

    private EventBus myMessageBus;
    private Parser myParser;
    private Queue<KeyInputEvent> commandHolder;


    public InputSystem(){
        myMessageBus = EventBusFactory.getEventBus();
        myParser = new Parser();
        commandHolder = new LinkedList<>();
    }

    /**
     My method for sending out information regarding inputs. I'll have to create
     a class that represents an attack input
     */
    public void postEvent(String s){
        ActionEvent keyEvent = new ActionEvent(s, "attacks");
        myMessageBus.post(keyEvent);
    }
    /**
     / The other subcribe event that listens for Key Inputs.
     / DISCLAIMER: Listen for KeyInputEvent for now ONLY
     */
    @Subscribe
    public void getKey(KeyInputEvent inputEvent){
        commandHolder.add(inputEvent);
        postEvent(myParser.parse(inputEvent));


        //TODO: COMBOS (NOT DONE)
        //List<String> events = myParser.parse(commandHolder);
        //for(String event:events){
        //    System.out.println(event);
        //    //postEvent(event);
        //}


    }

}
