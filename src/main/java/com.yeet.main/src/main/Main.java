package main;


import messenger.external.MessageBusFactory;
import player.external.Player;

public class Main {

    public static void main(String[] args){
        Player player = new Player();
        MessageBusFactory.getEventBus().register(player);
        player.doSomething();

    }
}
