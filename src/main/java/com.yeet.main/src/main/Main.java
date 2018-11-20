package main;

import console.external.Console;
import input.external.InputSystem;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import messenger.external.EventBusFactory;
import player.external.Player;
import renderer.external.RenderSystem;

public class Main extends Application {
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;
    static Console myConsole;
    private GameLoop gameLoop;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameLoop = new GameLoop();
        Player player = new Player(new Stage(), new RenderSystem());
        InputSystem IS = new InputSystem();
        myConsole = new Console();
        //register event listeners
        EventBusFactory.getEventBus().register(player);
        EventBusFactory.getEventBus().register(IS);
        EventBusFactory.getEventBus().register(myConsole);
        gameLoop.startLoop();
        //player.doSomething();
        //IS.doSomething();
    }

}
