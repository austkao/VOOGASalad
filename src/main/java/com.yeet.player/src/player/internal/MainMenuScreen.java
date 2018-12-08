package player.internal;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import renderer.external.Renderer;

import java.io.File;

/** Central hub for access to all game functionality
 *  @author bpx
 */
public class MainMenuScreen extends Screen {

    private MediaPlayer selectSE;

    public MainMenuScreen(Group root, Renderer renderer, SceneSwitch smashSceneSwitch, SceneSwitch quitSceneSwitch) {
        super(root, renderer);
        selectSE = new MediaPlayer(new Media(new File("src/main/java/com.yeet.player/resources/select.mp3").toURI().toString()));
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("mainmenu_bg.png")));
        background.setFitHeight(800.0);
        background.setFitWidth(1280.0);
        ImageView smashButton = makeButton("smash_button.png",880.0,523.0,74.0,85.0,event -> {
            selectSE.play();
            selectSE.setOnEndOfMedia(() -> {
                smashSceneSwitch.switchScene();
                selectSE.stop();
            });
        });
        ImageView settingsButton = makeButton("settings_button.png",610.0,146.0,649.0,254.0,event -> {
            //TODO: implement settings scene
            selectSE.play();
        });
        ImageView quitButton = makeButton("quit_button.png",610.0,132.0,649.0,436.0,event -> quitSceneSwitch.switchScene());
        super.getMyRoot().getChildren().addAll(background,smashButton,settingsButton,quitButton);
    }

    private ImageView makeButton(String fileName, double width, double height, double x, double y, EventHandler clickHandler) {
        ImageView smashButton = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(fileName)));
        smashButton.setFitWidth(width);
        smashButton.setFitHeight(height);
        smashButton.setX(x);
        smashButton.setY(y);
        smashButton.setOnMouseEntered(event -> {
            smashButton.setScaleY(1.1);
            smashButton.setScaleX(1.1);
        });
        smashButton.setOnMouseExited(event -> {
            smashButton.setScaleY(1.0);
            smashButton.setScaleX(1.0);
        });
        smashButton.setOnMousePressed(clickHandler);
        return smashButton;
    }
}
