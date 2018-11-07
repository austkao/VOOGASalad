package player.external;

import messenger.external.MessageBus;

public class Player {
    MessageBus myMessageBus;

    public Player(){
        myMessageBus = new MessageBus();
    }

    public void doSomething(){
        myMessageBus.talk();
    }
}
