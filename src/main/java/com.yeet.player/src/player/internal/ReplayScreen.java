package player.internal;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import renderer.external.Renderer;

import java.io.File;

public class ReplayScreen extends Screen {

    public ReplayScreen(Group root, Renderer renderer, Image background, File gameDirectory) {
        super(root, renderer);
        ImageView bg = new ImageView(background);
        bg.setFitHeight(800.0);
        bg.setFitWidth(1280.0);

        super.getMyRoot().getChildren().addAll(bg);
    }

}
