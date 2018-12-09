package player.internal;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import player.internal.Elements.MessageBar;
import renderer.external.Renderer;

import java.io.File;

/** Central hub for access to all game functionality
 *  @author bpx
 */
public class MainMenuScreen extends Screen {

    public static final String SMASH_TITLE = "SMASH";
    public static final String SMASH_MSG = "Jump right into a brawl with up to 4 players!";
    public static final String SETTINGS_TITLE = "SETTINGS";
    public static final String SETTINGS_MSG = "Adjust the game settings to your liking!";
    public static final String QUIT_TITLE = "QUIT";
    public static final String QUIT_MSG = "Aw, leaving so soon?";
    public static final String DEFAULT_TITLE = "Message";
    public static final String DEFAULT_MSG = "Welcome to Yeet Bros. Brawl!";
    private MediaPlayer selectSE;
    private MessageBar myMessageBar;

    public MainMenuScreen(Group root, Renderer renderer, SceneSwitch smashSceneSwitch, SceneSwitch quitSceneSwitch, SceneSwitch settingsSceneSwitch) {
        super(root, renderer);
        selectSE = new MediaPlayer(new Media(new File("src/main/java/com.yeet.player/resources/select.mp3").toURI().toString()));
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("mainmenu_bg.png")));
        background.setFitHeight(800.0);
        background.setFitWidth(1280.0);
        ImageView smashButton = makeButton("smash_button.png", SMASH_TITLE, SMASH_MSG,880.0,523.0,74.0,85.0, event -> {
            selectSE.setOnEndOfMedia(() -> {
                smashSceneSwitch.switchScene();
                selectSE.stop();
            });
            selectSE.play();
        });
        ImageView settingsButton = makeButton("settings_button.png", SETTINGS_TITLE, SETTINGS_MSG,610.0,146.0,649.0,254.0, event -> {
            selectSE.setOnEndOfMedia(() -> {
                settingsSceneSwitch.switchScene();
                selectSE.stop();
            });
            selectSE.play();
        });
        ImageView quitButton = makeButton("quit_button.png",QUIT_TITLE, QUIT_MSG,610.0,132.0,649.0,436.0, event -> quitSceneSwitch.switchScene());
        myMessageBar = new MessageBar(this.getMyRenderer().makeText(DEFAULT_TITLE,true,50, Color.WHITE,0.0,0.0),
                this.getMyRenderer().makeText(DEFAULT_MSG,false,30, Color.BLACK,0.0,0.0),
                174.0,676.0);
        super.getMyRoot().getChildren().addAll(background,myMessageBar,smashButton,settingsButton,quitButton);
    }

    private ImageView makeButton(String fileName, String messageTitle, String messageContent, double width, double height, double x, double y, EventHandler clickHandler) {
        ImageView button = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(fileName)));
        button.setFitWidth(width);
        button.setFitHeight(height);
        button.setX(x);
        button.setY(y);
        button.setOnMouseEntered(event -> {
            button.setScaleY(1.1);
            button.setScaleX(1.1);
            myMessageBar.setTitle(messageTitle);
            myMessageBar.setMessage(messageContent);
            myMessageBar.show();

        });
        button.setOnMouseExited(event -> {
            button.setScaleY(1.0);
            button.setScaleX(1.0);
            myMessageBar.hide();
        });
        button.setOnMousePressed(clickHandler);
        return button;
    }
}
