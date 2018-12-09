package player.internal;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import renderer.external.Renderer;


/** Screen for accessing the various options for changing game settings
 *  @author bpx
 */
public class SettingsScreen extends Screen {

    public static final double SCREEN_HEIGHT = 800.0;
    public static final double SCREEN_WIDTH = 1280.0;
    public static final double BUTTON_HEIGHT = 217.0;
    public static final double BUTTON_WIDTH = 462.0;
    public static final double BUTTON_Y = 270.0;

    public SettingsScreen(Group root, Renderer renderer, SceneSwitch mainMenuSwitch, SceneSwitch soundsSwitch, SceneSwitch controlsSwitch) {
        super(root, renderer);
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("mainmenu_bg.png")));
        background.setFitHeight(SCREEN_HEIGHT);
        background.setFitWidth(SCREEN_WIDTH);
        ImageView soundsButton = makeButton("sound_button.png",20.0, BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        ImageView qualityButton = makeButton("quality_button.png",403.0,BUTTON_Y,475.0,BUTTON_HEIGHT);
        ImageView controlsButton = makeButton("controls_button.png",789.0,BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
        super.getMyRoot().getChildren().addAll(background,soundsButton,qualityButton,controlsButton);

    }

    private ImageView makeButton(String fileName, double x, double y, double w, double h){
        ImageView button = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(fileName)));
        button.setFitWidth(w);
        button.setFitHeight(h);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }
}
