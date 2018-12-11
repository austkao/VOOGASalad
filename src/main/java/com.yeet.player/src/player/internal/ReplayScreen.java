package player.internal;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import player.internal.Elements.MessageBar;
import renderer.external.Renderer;

import java.io.File;

import static player.internal.Elements.CharacterChooseDisplay.FORMAT_RECT;
import static player.internal.MainMenuScreen.*;
import static player.internal.SettingsScreen.*;

public class ReplayScreen extends Screen {

    public static final double REPLAY_MAIN_WIDTH = 1200.0;
    public static final double REPLAY_MAIN_HEIGHT = 600.0;
    public static final double REPLAY_MAIN_SPACING = 100.0;
    private MessageBar myMessageBar;

    private String[] fileNames;

    public ReplayScreen(Group root, Renderer renderer, Image background, File gameDirectory, SceneSwitch mainMenuSwitch) {
        super(root, renderer);
        ImageView bg = new ImageView(background);
        bg.setFitHeight(800.0);
        bg.setFitWidth(1280.0);
        myMessageBar = new MessageBar(this.getMyRenderer().makeText("Replays",true,MESSAGEBAR_TITLE_FONTSIZE, Color.WHITE,0.0,0.0),
                this.getMyRenderer().makeText("Relive your past matches!",false,MESSAGEBAR_MSG_FONTSIZE,Color.BLACK,0.0,0.0),
                MESSAGEBAR_X,MESSAGEBAR_Y);
        StackPane topBar = makeTopBar(super.getMyRenderer().makeText("Replays",true,55, Color.BLACK,0.0,0.0),
                makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))),
                        "Back","Return to the main menu!",53.74,60.72,0.0,0.0,myMessageBar,
                        event->mainMenuSwitch.switchScene()));
        StackPane mainContainer = new StackPane();
        mainContainer.setPrefSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        mainContainer.setAlignment(Pos.CENTER);
        HBox replayMainContainer = new HBox(REPLAY_MAIN_SPACING);
        replayMainContainer.setPrefSize(REPLAY_MAIN_WIDTH, REPLAY_MAIN_HEIGHT);
        replayMainContainer.setMaxSize(REPLAY_MAIN_WIDTH,REPLAY_MAIN_HEIGHT);
        replayMainContainer.setStyle(String.format(FORMAT_RECT,"0 77 0 77","0 77 0 77","rgba(255,255,255,0.4)")+"-fx-border-color: black; -fx-border-width: 3;");
        replayMainContainer.setAlignment(Pos.CENTER);
        VBox replayListContainer = new VBox(25.0);
        replayListContainer.setPrefSize(422.0,545.0);
        replayListContainer.setMaxSize(422.0,545.0);
        replayListContainer.setAlignment(Pos.CENTER);
        replayListContainer.setStyle("-fx-border-color: red");

        //search for replay directory and attempt to create list of replay files
        File replayDirectory = new File(gameDirectory,"replays");
        replayDirectory.mkdir();
        fileNames = new String[replayDirectory.listFiles().length];
        for(int i=0;i<replayDirectory.listFiles().length;i++){
            fileNames[i] = replayDirectory.listFiles()[i].getName();
        }
        ListView<String> replayList = new ListView<>(FXCollections.observableArrayList(fileNames));
        replayList.setPrefSize(422.0,498.0);
        replayList.setOnMousePressed(event -> handleListClick());
        
        VBox replayDisplayContainer = new VBox(25.0);
        replayDisplayContainer.setPrefSize(513.0,539.0);
        replayDisplayContainer.setMaxSize(513.0,539.0);
        replayDisplayContainer.setAlignment(Pos.CENTER);
        replayDisplayContainer.setStyle("-fx-border-color: blue");

        replayListContainer.getChildren().addAll(replayList);
        replayMainContainer.getChildren().addAll(replayListContainer,replayDisplayContainer);
        mainContainer.getChildren().addAll(replayMainContainer);
        super.getMyRoot().getChildren().addAll(bg,mainContainer,topBar);
    }

    private void handleListClick() {

    }

}
