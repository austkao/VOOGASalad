package input.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.Internal.Parser;
import messenger.external.ActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.KeyInputEvent;

import java.util.*;

public class InputSystem {

    private EventBus myMessageBus;
    private Parser myParser;
    private Queue<KeyInputEvent> commandHolder;
    private Timer timer;


    public InputSystem(){
        myMessageBus = EventBusFactory.getEventBus();
        myParser = new Parser();
        commandHolder = new LinkedList<>();
        timer = new Timer();
        setUpTimer();

    }

    private void setUpTimer(){
        //Set the schedule function and rate
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //Called each time when 1000 milliseconds (1 second) (the period parameter)
                if(commandHolder.size() >0){
                    postEvent(myParser.parse(commandHolder));
                    commandHolder.clear();
                }
            }
            },
                //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                1000);
    }

    /**
     My method for sending out information regarding inputs. I'll have to create
     a class that represents an attack input
     */
    public void postEvent(List<String> s){
        for(String action:s){
            System.out.println(action);
            ActionEvent keyEvent = new ActionEvent(action, "attacks");
            myMessageBus.post(keyEvent);
        }

    }
    /**
     / The other subcribe event that listens for Key Inputs.
     / DISCLAIMER: Listen for KeyInputEvent for now ONLY
     */
    @Subscribe
    public void getKey(KeyInputEvent inputEvent){
        commandHolder.add(inputEvent);


    }

}
