package main;

import console.external.Console;
import input.external.InputSystem;
import javafx.application.Application;
import javafx.stage.Stage;
import messenger.external.EventBusFactory;
import player.external.Player;

public class Main extends Application {

    static Console myConsole;
    private GameLoop gameLoop;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameLoop = new GameLoop();
        Player player = new Player();
        InputSystem IS = new InputSystem();
        myConsole = new Console();
        EventBusFactory.getEventBus().register(player);
        EventBusFactory.getEventBus().register(IS);
        EventBusFactory.getEventBus().register(myConsole);
        gameLoop.startLoop();
        //player.doSomething();
        //IS.doSomething();
    }

}
