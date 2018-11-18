package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import renderer.external.Renderer;
import renderer.external.Structures.CharacterChooseDisplay;

/** Dynamic layout for the display of all available characters, allows users to select their
 *  character for a fight
 *  @author bpx
 */
public class CharacterSelectScreen extends Screen {

    public CharacterSelectScreen(Group root, Renderer renderer) {
        super(root, renderer);
        super.setFill(Color.WHITE);
        StackPane holder = new StackPane();
        holder.setAlignment(Pos.BOTTOM_CENTER);
        ImageView bg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("characterselect_bg.jpg")));
        bg.setFitWidth(1280);
        bg.setFitHeight(800);
        bg.setOpacity(0.52);
        HBox charBox = new HBox(10);
        charBox.setMaxHeight(332.0);
        charBox.setAlignment(Pos.CENTER);
        CharacterChooseDisplay char1 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#FD1B1B"),"Player 1");
        CharacterChooseDisplay char2 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#4C7FFF"),"Player 2");
        CharacterChooseDisplay char3 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#FFF61B"),"Player 3");
        CharacterChooseDisplay char4 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#1FCB17"),"Player 4");
        charBox.getChildren().addAll(char1, char2, char3, char4);
        holder.getChildren().addAll(bg,charBox);
        super.getMyRoot().getChildren().addAll(holder);
        char1.setPortrait(new Image(this.getClass().getClassLoader().getResourceAsStream("lucina.png")));
    }

}
