package console.external;

import com.google.common.eventbus.Subscribe;
import messenger.external.Event;

public class Console {
    @Subscribe
    public  void printEvent(Event event){
        // Simulate sending reciept
        System.out.println(event.getName());
    }
}
