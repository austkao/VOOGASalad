package player.internal;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;
import renderer.external.Renderer;

/** Central hub for access to all game functionality
 *  @author bpx
 */
public class MainMenuScreen extends Screen {

    public MainMenuScreen(Group root, Renderer renderer) {
        super(root, renderer);
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
        smashButton.setOnMousePressed(event -> System.out.println("hello :)"));
        super.getMyRoot().getChildren().addAll(background,smashButton);
    }
}
