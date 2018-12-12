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
import messenger.external.EventBusFactory;
import player.internal.Elements.MessageBar;
import renderer.external.Renderer;
import replay.external.InvalidReplayFileException;
import replay.external.ReplayPlayer;
import xml.XMLParser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static player.internal.Elements.CharacterChooseDisplay.FORMAT_RECT;
import static player.internal.MainMenuScreen.*;
import static player.internal.SettingsScreen.*;

public class ReplayScreen extends Screen {

    public static final double REPLAY_MAIN_WIDTH = 1200.0;
    public static final double REPLAY_MAIN_HEIGHT = 600.0;
    public static final double REPLAY_MAIN_SPACING = 100.0;

    private MessageBar myMessageBar;

    private File gameDirectory;
    private File replayDirectory;

    private ReplayPlayer replayPlayer;

    private String[] fileNames;
    private ListView replayList;
    private ImageView replayPreview;
    private ImageView playButton;
    private HBox characterPreview;
    private Text stageName;
    private Text date;
    private Text gameMode;

    public ReplayScreen(Group root, Renderer renderer, Image background, File gameDirectory, SceneSwitch mainMenuSwitch) {
        super(root, renderer);
        this.gameDirectory = gameDirectory;
        ImageView bg = new ImageView(background);
        bg.setFitHeight(800.0);
        bg.setFitWidth(1280.0);
        replayPlayer = new ReplayPlayer(EventBusFactory.getEventBus());
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
        replayDirectory = new File(gameDirectory,"replays");
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
        StackPane replayPreviewContainer = new StackPane();
        replayPreviewContainer.setPrefSize(513.0,321.0);
        replayPreviewContainer.setAlignment(Pos.CENTER);
        replayPreviewContainer.setStyle("-fx-border-color: black;-fx-border-width: 2");
        replayPreview = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("replay_placeholder.png")));
        replayPreview.setFitWidth(513.0);
        replayPreview.setFitHeight(321.0);
        playButton = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("play_button.png")));
        playButton.setFitWidth(92.0);
        playButton.setFitHeight(92.0);
        playButton.setOpacity(0.0);
        HBox replayInfoContainer = new HBox(25.0);
        replayInfoContainer.setPrefSize(513.0,173.0);
        replayInfoContainer.setAlignment(Pos.TOP_LEFT);
        replayInfoContainer.setStyle("-fx-border-color: green");
        characterPreview = new HBox(15.0);
        characterPreview.setPrefSize(311.0,66.0);
        characterPreview.setAlignment(Pos.CENTER);
        characterPreview.setStyle("-fx-border-color: purple");
        VBox replayInfo = new VBox(25.0);
        replayInfo.setPrefSize(175.0,173.0);
        replayInfo.setAlignment(Pos.TOP_RIGHT);
        replayInfo.setStyle("-fx-border-color: cyan");
        stageName = super.getMyRenderer().makeText("Stage Name",false,30,Color.BLACK,0.0,0.0);
        date = super.getMyRenderer().makeText("Date",false,30,Color.BLACK,0.0,0.0);
        gameMode = super.getMyRenderer().makeText("Game Mode",false,30,Color.BLACK,0.0,0.0);
        replayInfo.getChildren().addAll(stageName,date,gameMode);
        replayListContainer.getChildren().addAll(replayList,openDirectory);
        replayInfoContainer.getChildren().addAll(characterPreview,replayInfo);
        replayPreviewContainer.getChildren().addAll(replayPreview,playButton);
        replayDisplayContainer.getChildren().addAll(replayPreviewContainer,replayInfoContainer);
        replayMainContainer.getChildren().addAll(replayListContainer,replayDisplayContainer);
        mainContainer.getChildren().addAll(replayMainContainer);
        super.getMyRoot().getChildren().addAll(bg,mainContainer,topBar);
    }

    private void openReplayDirectory() {
        try {
            Desktop.getDesktop().open(replayDirectory);
        } catch (IOException e) {
            //TODO some kind of error handling?
        }

    }

    private void handleListClick() {
        try{
            String file = (String)replayList.getSelectionModel().getSelectedItem();
            File replayFile = new File(replayDirectory,file);
            replayPlayer.load(replayFile);
            stageName.setText(replayPlayer.getStageName());
            date.setText(replayPlayer.getDate());
            gameMode.setText(replayPlayer.getGameMode());
            //get map image
            File parse = new File(String.format("%s/%s",new File(new File(gameDirectory,"stages"),replayPlayer.getStageName()).getPath(),"stageproperties.xml"));
            XMLParser parser = new XMLParser(parse);
            HashMap<String, ArrayList<String>> bgmap = parser.parseFileForElement("background");
            replayPreview.setImage(new Image(gameDirectory.toURI()+"data/background/"+bgmap.get("bgFile").get(0)));
            //make mini portraits
            characterPreview.getChildren().clear();
            for(Integer i : replayPlayer.getCharacterMap().keySet()){
                StackPane portraitContainer = new StackPane();
                portraitContainer.setPrefSize(66.0,66.0);
                portraitContainer.setMaxSize(66.0,66.0);
                portraitContainer.setStyle("-fx-background-color: "+replayPlayer.getColorMap().get(i));
                XMLParser characterPropertiesParser = new XMLParser(new File(gameDirectory.getPath()+"/characters/"+replayPlayer.getCharacterMap().get(i)+"/characterproperties.xml"));
                double x = Double.parseDouble(characterPropertiesParser.parseFileForAttribute("portrait","x").get(0));
                double y = Double.parseDouble(characterPropertiesParser.parseFileForAttribute("portrait","y").get(0));
                double size = Double.parseDouble(characterPropertiesParser.parseFileForAttribute("portrait","size").get(0));
                ImageView healthPortrait = new ImageView(new Image(gameDirectory.toURI()+"/characters/"+replayPlayer.getCharacterMap().get(i)+"/"+replayPlayer.getCharacterMap().get(i)+".png"));
                healthPortrait.setViewport(new javafx.geometry.Rectangle2D(x,y,size,size));
                ImageView portraitView = healthPortrait;
                portraitView.setFitWidth(66.0);
                portraitView.setFitHeight(66.0);
                portraitContainer.getChildren().addAll(portraitView);
                characterPreview.getChildren().add(portraitContainer);
            }
        } catch (InvalidReplayFileException e) {
            //TODO bad replay file
            e.printStackTrace();
        } catch (NullPointerException e){
            //TODO no file selected in bar, no biggie
        }
    }

}
