package player.internal.Elements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import player.internal.SceneSwitch;

import static player.internal.Elements.CharacterChooseDisplay.FORMAT_RECT;
import static renderer.external.RenderUtils.toRGBCode;

/** Menu bar at top of character grid for accessing back button and changing game rules
 *  @author bpx
 */
public class MenuTopper extends HBox {

    /** Create a {@code MenuTopper} with a certain amount of spacing
     *  @param spacing Amount of spacing between elements horizontally
     */
    public MenuTopper(double spacing, SceneSwitch sceneSwitch){
        super(spacing);
        this.setPrefSize(1280,75);
        this.setAlignment(Pos.CENTER_LEFT);
        Rectangle menuSpacer = new Rectangle(15,75, Color.TRANSPARENT);
        //set up back button
        ImageView backButton = new ImageView((new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))));
        backButton.setFitHeight(60.0);
        backButton.setFitWidth(60.0);
        VBox buttonHolder = new VBox(backButton);
        buttonHolder.setOnMouseEntered(event -> {
            buttonHolder.setScaleX(1.1);
            buttonHolder.setScaleY(1.1);
        });
        buttonHolder.setOnMouseExited(event -> {
            buttonHolder.setScaleX(1.0);
            buttonHolder.setScaleY(1.0);
        });
        buttonHolder.setOnMousePressed(event -> sceneSwitch.switchScene());
        //set up actual menu topper
        HBox menuTop = new HBox(5.0);
        menuTop.setAlignment(Pos.CENTER);
        menuTop.setPrefSize(914,75);
        menuTop.setStyle(String.format(FORMAT_RECT,"35 35 0 0", "35 35 0 0", toRGBCode(Color.web("#201D20"))));
        HBox smashBox = new HBox(24.0);
        smashBox.setAlignment(Pos.CENTER);
        smashBox.setPrefSize(280,62);
        smashBox.setMaxSize(280,62);
        smashBox.setStyle(String.format(FORMAT_RECT,"35 0 0 0","35 0 0 0",toRGBCode(Color.web("#FF1D20"))));
        ImageView smashIcon = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("smash_icon.png")));
        smashIcon.setFitWidth(50);
        smashIcon.setFitHeight(50);
        ImageView smashText = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("smash_text.png")));
        HBox ruleBox = new HBox();
        ruleBox.setAlignment(Pos.CENTER);
        ruleBox.setPrefSize(370,62);
        ruleBox.setMaxSize(370,62);
        ruleBox.setStyle(String.format(FORMAT_RECT,"0 0 0 0","0 0 0 0",toRGBCode(Color.WHITE)));

        HBox carouselBox = new HBox();
        carouselBox.setAlignment(Pos.CENTER);
        carouselBox.setPrefSize(217,62);
        carouselBox.setMaxSize(217,62);
        carouselBox.setStyle(String.format(FORMAT_RECT,"0 35 0 0","0 35 0 0",toRGBCode(Color.WHITE)));
        //TODO: FINISH MENU TOPPER
        smashBox.getChildren().addAll(smashIcon,smashText);
        menuTop.getChildren().addAll(smashBox,ruleBox,carouselBox);
        this.getChildren().addAll(menuSpacer,buttonHolder,menuTop);
    }

}
