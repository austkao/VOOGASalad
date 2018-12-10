package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import player.internal.Elements.MessageBar;
import renderer.external.Renderer;

import static player.internal.MainMenuScreen.*;


/** Screen for accessing the various options for changing game settings
 *  @author bpx
 */
public class SettingsScreen extends Screen {

    public static final double SCREEN_HEIGHT = 800.0;
    public static final double SCREEN_WIDTH = 1280.0;
    public static final double BUTTON_HEIGHT = 217.0;
    public static final double BUTTON_WIDTH = 462.0;
    public static final double BUTTON_Y = 270.0;

    private MessageBar myMessageBar;

    public SettingsScreen(Group root, Renderer renderer, SceneSwitch mainMenuSwitch, SceneSwitch soundsSwitch, SceneSwitch controlsSwitch) {
        super(root, renderer);
        myMessageBar = new MessageBar(this.getMyRenderer().makeText(SETTINGS_TITLE,true,MESSAGEBAR_TITLE_FONTSIZE, Color.WHITE,0.0,0.0),
                this.getMyRenderer().makeText(SETTINGS_MSG,false,MESSAGEBAR_MSG_FONTSIZE,Color.BLACK,0.0,0.0),
                MESSAGEBAR_X,MESSAGEBAR_Y);
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("mainmenu_bg.png")));
        background.setFitHeight(SCREEN_HEIGHT);
        background.setFitWidth(SCREEN_WIDTH);
        StackPane topBar = new StackPane();
        topBar.setMinSize(SCREEN_WIDTH,80.0);
        topBar.setPrefSize(SCREEN_WIDTH,80.0);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: linear-gradient(rgba(255,255,255,0.8), rgba(255,255,255,0.2));");
        topBar.setLayoutX(0.0);
        topBar.setLayoutY(0.0);
        HBox backButtonContainer = new HBox();
        backButtonContainer.setPrefSize(SCREEN_WIDTH,80.0);
        backButtonContainer.setAlignment(Pos.CENTER_LEFT);
        Rectangle buttonSpacer = new Rectangle(30.0,80.0,Color.TRANSPARENT);
        ImageView backButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))),"Back","Return to the main menu!",53.74,60.72,0.0,0.0,myMessageBar,event->mainMenuSwitch.switchScene());
        backButtonContainer.getChildren().addAll(buttonSpacer,backButton);
        Text titleText = super.getMyRenderer().makeText("Settings",true,55,Color.BLACK,0.0,0.0);
        titleText.setFill(Color.rgb(0,0,0,0.71));
        topBar.getChildren().addAll(titleText,backButtonContainer);
        ImageView soundsButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("sound_button.png"))),"Sounds","Customize audio volumes!",BUTTON_WIDTH,BUTTON_HEIGHT,20.0, BUTTON_Y,myMessageBar,event -> soundsSwitch.switchScene());
        ImageView qualityButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("quality_button.png"))),"Quality","This feature is not yet available :(",475.0,BUTTON_HEIGHT,403.0, BUTTON_Y,myMessageBar,event -> doNothing());
        ImageView controlsButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("controls_button.png"))),"Controls","Reassign key bindings!",BUTTON_WIDTH,BUTTON_HEIGHT,789.0, BUTTON_Y,myMessageBar,event -> controlsSwitch.switchScene());
        super.getMyRoot().getChildren().addAll(background,topBar,myMessageBar,soundsButton,qualityButton,controlsButton);

    }
    private void doNothing(){
        //LOL, there's no quality settings
    }

}
