package input.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.Internal.Parser;
import messenger.external.*;

import java.io.File;
import java.util.*;

public class InputSystem {

    private EventBus myMessageBus;
    private Parser myParser;
    private Queue<KeyInputEvent> commandHolder;
    private Map<String, ArrayList<String>> inputKeys;
    private Timer timer;
    private File GameDir;


    public InputSystem(File GameDirectory) {
        try {
            myMessageBus = EventBusFactory.getEventBus();
            commandHolder = new LinkedList<>();
            timer = new Timer();
            GameDir = GameDirectory;
            myParser = new Parser(GameDir);
            setUpTimer();
        } catch (Exception e) {
            System.out.println("An error has occurred in the input system");
        }
    }

    private void setUpTimer() throws Exception{
        //Set the schedule function and rate
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //Called each time when 1000 milliseconds (1 second) (the period parameter)
                if(commandHolder.size() >0){
                    try {
                        postEvent(myParser.parse(commandHolder));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    commandHolder.clear();
                }
            }
            },
                //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                100);
    }


    /**
     My method for sending out information regarding inputs. I'll have to create
     a class that represents an attack input
     */
    //TODO: Use reflection on this!
    public void postEvent(List<String> s){
        //TRUE if left, FALSE if right
        for(String action:s){
            System.out.println(action);
            CombatActionEvent keyEvent;
            if(action.equals("LEFT")){
                keyEvent = new MoveEvent(1, true);
            }
            else if(action.equals("RIGHT")){
                keyEvent = new MoveEvent(1, false);
            }
            else if (action.equals("UP'")){
                keyEvent =  new JumpEvent(1);
            }
            else if(!action.equals("JAB")){
                keyEvent = new AttackEvent(1);
            }
            else{
                keyEvent = new JumpEvent(1);
            }
            myMessageBus.post(keyEvent);

            //TESTING: Also post an action event
            ActionEvent ae = new ActionEvent(action, "Attack");
            myMessageBus.post(ae);
        }

    }
    /**
     / The other subcribe event that listens for Key Inputs.
     / DISCLAIMER: Listen for KeyInputEvent for now ONLY
     */
    @Subscribe
    public void getKey(KeyInputEvent inputEvent){
        System.out.println(inputEvent.getName());
        commandHolder.add(inputEvent);
    }

    ///**
    // / Listens for the start of a match. Tells the system to start listening for inpits
    // */
    //@Subscribe
    //public void startListening(){
    //    setUpTimer();
    //}

    /**
     / Listens for game over. Tells this system to stop listening for inputs
     */
    @Subscribe
    public void stopListening(GameOverEvent gameOver){
        timer.cancel();
        timer.purge();
    }

}
