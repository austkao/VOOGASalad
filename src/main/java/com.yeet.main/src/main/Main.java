package main;

import console.external.Console;
import editor.EditorManager;
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

    private static Console myConsole;
    private Font myEmphasisFont;
    private Font myPlainFont;
    private EditorManager em;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create window
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Yeet Fighter Game Engine");
        Group root = new Group();
        Scene homeScene = new Scene(root);
        primaryStage.setScene(homeScene);
        primaryStage.show();
        //set up systems
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT),DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        RenderSystem renderSystem = new RenderSystem(myPlainFont,myEmphasisFont);
        Player player = new Player(primaryStage,renderSystem);
        myConsole = new Console();
        //register event listeners
        EventBusFactory.getEventBus().register(player);
        EventBusFactory.getEventBus().register(myConsole);
        //program start
        player.doSomething();
        root.getChildren().add(renderSystem.makeStringButton("Hello World", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0));

        em = new EditorManager(primaryStage,homeScene);
        Button editorButton = renderSystem.makeStringButton("Editor", Color.BLACK,true, Color.WHITE, 30.0, 800.0, 500.0, 350.0, 50.0);
        editorButton.setOnMouseClicked(event -> processClick(event));
        root.getChildren().add(editorButton);
    }

    private void processClick(MouseEvent e){
        em.setEditorHomeScene();
    }
}
