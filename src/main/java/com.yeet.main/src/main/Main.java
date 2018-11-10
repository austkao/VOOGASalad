package main;

import console.external.Console;
import javafx.application.Application;
import javafx.stage.Stage;
import messenger.external.EventBusFactory;
import player.external.Player;

public class Main extends Application {

    static Console myConsole;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Player player = new Player();
        myConsole = new Console();
        EventBusFactory.getEventBus().register(player);
        EventBusFactory.getEventBus().register(myConsole);
        player.doSomething();

    }
}
