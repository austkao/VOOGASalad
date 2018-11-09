package console.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.Event;
import messenger.external.EventBusFactory;
import messenger.external.TestSuccesfulEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

public class Console {

    EventBus myEventBus;

    public Console(){
        myEventBus = EventBusFactory.getEventBus();
        startConsole();
    }

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread T = new Thread(runner);
        T.start();
    }

    private void createEvent(String event){
        if (event.equalsIgnoreCase("test")) {
            myEventBus.post(new TestSuccesfulEvent());
        }
    }


    @Subscribe
    public  void printEvent(Event event){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("["+timestamp+"]"+" Console: "+event.getName());
    }
}
