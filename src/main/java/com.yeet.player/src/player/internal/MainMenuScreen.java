package player.internal;

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

    public MainMenuScreen(Group root, Renderer renderer, SceneSwitch sceneSwitch) {
        super(root, renderer);
        selectSE = new MediaPlayer(new Media(new File("src/main/java/com.yeet.player/resources/select.mp3").toURI().toString()));
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("mainmenu_bg.png")));
        background.setFitHeight(800.0);
        background.setFitWidth(1280.0);
        ImageView smashButton = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("smash_button.png")));
        smashButton.setFitWidth(640.0);
        smashButton.setFitHeight(400.0);
        smashButton.setX(163.0);
        smashButton.setY(185.0);
        smashButton.setOnMouseEntered(event -> {
            smashButton.setScaleY(1.2);
            smashButton.setScaleX(1.2);
        });
        smashButton.setOnMouseExited(event -> {
            smashButton.setScaleY(1.0);
            smashButton.setScaleX(1.0);
        });
        smashButton.setOnMousePressed(event -> {
            selectSE.play();
            selectSE.setOnEndOfMedia(() -> {
                sceneSwitch.switchScene();
                selectSE.stop();
            });
        });
        super.getMyRoot().getChildren().addAll(background,smashButton);
    }
}
