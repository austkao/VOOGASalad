package messenger.external;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

public class MessageBus {

    public MessageBus(){

    }

    public void talk(){
        System.out.println("Hello, I am the Messenger!");
    }
}
