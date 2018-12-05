package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import renderer.external.Renderer;

import javax.swing.text.html.ImageView;

/** Displays the winner of the combat as well as other related statistics and information
 *  @author bpx
 */
public class CombatResultsScreen extends Screen {

    public CombatResultsScreen(Group root, Renderer renderer) {
        super(root, renderer);
        VBox mainContainer = new VBox();
        Rectangle topSpacer = new Rectangle(1280,20, Color.TRANSPARENT);
        StackPane winnerBanner = new StackPane();
        winnerBanner.setPrefSize(1280.0,200.0);
        winnerBanner.setAlignment(Pos.CENTER);
        ImageView winnerBannerImage;

        mainContainer.getChildren().addAll(topSpacer);
        this.getMyRoot().getChildren().addAll(mainContainer);
    }
}
