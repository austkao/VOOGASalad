package console.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.Event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    EventBus myEventBus;

    public Console(){
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

                System.out.print(line+"\n");
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

    @Subscribe
    public  void printEvent(Event event){
        // Simulate sending reciept
        System.out.println(event.getName());
    }
}
