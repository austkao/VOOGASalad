package main;

import console.external.Console;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import messenger.external.EventBusFactory;
import player.external.Player;
import renderer.external.RenderSystem;

import java.io.File;

public class Main extends Application {
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;

    private Stage myStage;
    private Stage myPopup;

    private static Console myConsole;
    private RenderSystem myRenderSystem;
    private Font myEmphasisFont;
    private Font myPlainFont;

    private DirectoryChooser myDirectoryChooser;
    private File myDirectory;

    private ImageView mySplashDisplay;


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create window
        myStage = primaryStage;
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Yeet Fighter Game Engine");
        Group root = new Group();
        Scene homeScene = new Scene(root);
        primaryStage.setScene(homeScene);
        homeScene.setFill(Color.web("#91C7E8"));
        primaryStage.show();
        //set up systems
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT),DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        myRenderSystem = new RenderSystem(myPlainFont,myEmphasisFont);
        Player player = new Player(primaryStage,myRenderSystem);
        myConsole = new Console();
        myDirectoryChooser = myRenderSystem.makeDirectoryChooser();
        //register event listeners
        EventBusFactory.getEventBus().register(player);
        EventBusFactory.getEventBus().register(myConsole);
        //display setup
        myPopup = createErrorPopup();
        mySplashDisplay = createSplashDisplay();
        root.getChildren().add(mySplashDisplay);
        Button newButton = myRenderSystem.makeStringButton("New Game",Color.web("#4E82D1"),true,Color.WHITE,30.0,891.0,183.36,307.21,94.6);
        root.getChildren().add(newButton);
        Button editButton = myRenderSystem.makeStringButton("Edit Game",Color.web("#4E82D1"),true,Color.WHITE,30.0,891.0,311.68,307.21,94.6);
        root.getChildren().add(editButton);
        Button loadButton = myRenderSystem.makeStringButton("Load Game",Color.web("#4E82D1"),true,Color.WHITE,30.0,891.0,440.4,307.21,94.6);
        loadButton.setOnMousePressed(e -> setDirectory());
        root.getChildren().add(loadButton);
        Button playButton = myRenderSystem.makeStringButton("Play",Color.RED,true,Color.WHITE,60.0,901.0,578.0,288.0,123.0);
        root.getChildren().add(playButton);
        //program start
        player.doSomething();


    }

    private void setDirectory(){
        File directory = myDirectoryChooser.showDialog(myStage);
        if(directory!=null){
            Image splash = new Image(String.format("%s%s",directory.toURI(),"splash.png"));
            if(!splash.isError()){
                System.out.println("nono");
                mySplashDisplay.setImage(splash);
                myDirectory = directory;
            }
            else{
                System.out.println("haha");
                myPopup.show();
            }
        }

    }

    private Stage createErrorPopup(){
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Warning");
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(myRenderSystem.makeText("Whoops!",true,40,Color.BLACK,146.75,69.16));
        Text message = myRenderSystem.makeText("We couldn't load the splash image for the loaded game.",false,20,Color.BLACK,41.26,129.46);
        message.setWrappingWidth(417.76);
        root.getChildren().add(message);
        Button button = myRenderSystem.makeStringButton("OK",Color.BLACK,false,Color.WHITE,20.9,175.0,200.0,150.0,30.0);
        button.setOnMousePressed(event -> myPopup.close());
        root.getChildren().add(button);
        stage.setScene(scene);
        return stage;
    }

    private ImageView createSplashDisplay(){
        ImageView display = new ImageView();
        display.setFitHeight(560);
        display.setFitWidth(715);
        display.setLayoutX(80);
        display.setLayoutY(76);
        return display;
    }
}
