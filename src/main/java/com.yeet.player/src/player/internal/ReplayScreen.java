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
import javafx.scene.text.Text;
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
    private ListView replayList;
    private ImageView replayPreview;

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
        replayListContainer.setAlignment(Pos.CENTER_RIGHT);
        replayListContainer.setStyle("-fx-border-color: red");

        //search for replay directory and attempt to create list of replay files
        File replayDirectory = new File(gameDirectory,"replays");
        replayDirectory.mkdir();
        fileNames = new String[replayDirectory.listFiles().length];
        for(int i=0;i<replayDirectory.listFiles().length;i++){
            fileNames[i] = replayDirectory.listFiles()[i].getName();
        }
        replayList = new ListView<>(FXCollections.observableArrayList(fileNames));
        replayList.setPrefSize(422.0,498.0);
        replayList.setOnMousePressed(event -> handleListClick());
        Text openDirectory = super.getMyRenderer().makeText("Open Replays Folder",false,16,Color.BLACK,0.0,0.0);
        openDirectory.setOnMousePressed(event -> openReplayDirectory());
        
        VBox replayDisplayContainer = new VBox(25.0);
        replayDisplayContainer.setPrefSize(513.0,539.0);
        replayDisplayContainer.setMaxSize(513.0,539.0);
        replayDisplayContainer.setAlignment(Pos.TOP_CENTER);
        replayDisplayContainer.setStyle("-fx-border-color: blue");
        replayPreview = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("replay_placeholder.png")));
        replayPreview.setFitWidth(513.0);
        replayPreview.setFitHeight(321.0);
        HBox replayInfoContainer = new HBox(25.0);
        replayInfoContainer.setPrefSize(513.0,173.0);
        replayInfoContainer.setAlignment(Pos.TOP_LEFT);
        replayInfoContainer.setStyle("-fx-border-color: green");
        HBox characterPreview = new HBox(15.0);
        characterPreview.setPrefSize(311.0,66.0);
        characterPreview.setAlignment(Pos.CENTER);
        characterPreview.setStyle("-fx-border-color: purple");
        VBox replayInfo = new VBox(25.0);
        replayInfo.setPrefSize(175.0,173.0);
        replayInfo.setAlignment(Pos.TOP_RIGHT);
        replayInfo.setStyle("-fx-border-color: cyan");

        replayListContainer.getChildren().addAll(replayList,openDirectory);
        replayInfoContainer.getChildren().addAll(characterPreview,replayInfo);
        replayDisplayContainer.getChildren().addAll(replayPreview,replayInfoContainer);
        replayMainContainer.getChildren().addAll(replayListContainer,replayDisplayContainer);
        mainContainer.getChildren().addAll(replayMainContainer);
        super.getMyRoot().getChildren().addAll(bg,mainContainer,topBar);
    }

    private void openReplayDirectory() {

    }

    private void handleListClick() {

    }

}
