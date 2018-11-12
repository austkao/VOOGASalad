package input.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.Internal.Parser;
import input.Internal.TimeHandler;
import javafx.scene.input.KeyCode;
import messenger.external.ActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.KeyInputEvent;

import java.sql.Timestamp;
import java.util.*;

public class InputSystem {

    private EventBus myMessageBus;
    private Parser myParser;
    private Queue<KeyInputEvent> commandHolder;
    private Map<Timestamp, KeyCode> commandMap;
    private TimeHandler timer;
    private double INPUT_THRESHOLD = 250;


    public InputSystem(){
        myMessageBus = EventBusFactory.getEventBus();
        myParser = new Parser();
        commandHolder = new LinkedList<>();
        //EXPERIMENTAL
        commandMap = new LinkedHashMap<>();
        timer = new TimeHandler();
    }

    /**
     My method for sending out information regarding inputs. I'll have to create
     a class that represents an attack input
     */
    public void postEvent(String s){
        ActionEvent keyEvent = new ActionEvent(s);
        myMessageBus.post(keyEvent);
    }
    /**
     / The other subcribe event that listens for Key Inputs.
     / DISCLAIMER: Listen for KeyInputEvent for now ONLY
     */
    @Subscribe
    public void getKey(KeyInputEvent inputEvent){
        commandHolder.add(inputEvent);
        List<String> events = timer.comboHandler(commandHolder);

        for(String event:events){
            postEvent(event);
        }


        //commandMap.put(inputEvent.getTime(), inputEvent.getKey());
        //System.out.println("I CAUGHT "+ inputEvent.getKey()+ " " +inputEvent.getTime());

    }

}
