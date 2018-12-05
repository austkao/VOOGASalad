package console.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.Event;
import messenger.external.EventBusFactory;
import messenger.external.GameOverEvent;
import messenger.external.TestSuccesfulEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

public class Console {

    private EventBus myEventBus;

    public Console(){
        myEventBus = EventBusFactory.getEventBus();
        startConsole();
    }

    /** Creates a separate {@code Thread} that waits for console input, and exits upon the 'quit' command*/
    private void startConsole() {
        Runnable runner = () -> {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            while (!line.equalsIgnoreCase("quit")) {
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                createEvent(line);
            }

            try {
                in.close();
                System.out.println("Console shutting down.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread T = new Thread(runner);
        T.start();
    }

    /** Converts user console input to an actual {@code Event} which can be posted to the {@code EventBus}*/
    private void createEvent(String event){
        if (event.equalsIgnoreCase("test")) {
            myEventBus.post(new TestSuccesfulEvent());
        }
        else if(event.matches("gameover [0-3]")){
            myEventBus.post(new GameOverEvent(Integer.parseInt(event.substring(event.length()-1))));
        }
    }


    /** Prints any {@code Event} sent through the {@code EventBus} to the console for debugging purposes.*/
    @Subscribe
    public  void printEvent(Event event){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("["+timestamp+"]"+" Console: "+event.getName());
    }
}
