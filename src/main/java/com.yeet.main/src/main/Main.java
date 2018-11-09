package main;


import console.external.Console;
import messenger.external.MessageBusFactory;
import player.external.Player;

public class Main {

    static Console myConsole;

    public static void main(String[] args){
        Player player = new Player();
        myConsole = new Console();
        MessageBusFactory.getEventBus().register(player);
        MessageBusFactory.getEventBus().register(myConsole);
        player.doSomething();

    }
}
