package player.internal;

import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import renderer.external.Renderer;
import renderer.external.Structures.OrbitAnimation;

/** Placeholder {@code Screen} while other elements are being initialized
 *  @author bpx
 */
public class LoadingScreen extends Screen {

    private Group myRoot;
    private Renderer myRenderer;

    private Animation animation;

    /** Creates a preset loading screen */
    public LoadingScreen(Group root, Renderer renderer){
        super(root,renderer);
        myRoot = root;
        myRenderer = renderer;
        HBox textBox = new HBox(myRenderer.makeText("Loading",false,50, Color.BLACK,0.0,0.0));
        textBox.setLayoutX(548.0);
        textBox.setLayoutY(366.0);
        root.getChildren().add(textBox);
        Circle staticCircle = new Circle(640.0,400.0,200.0);
        staticCircle.setFill(Color.TRANSPARENT);
        staticCircle.setStroke(Color.BLACK);
        myRoot.getChildren().add(staticCircle);
        Circle dynamicCircle = new Circle(640.0,200.0,13.0);
        animation = new OrbitAnimation(dynamicCircle,640.0,400.0,200.0,Duration.seconds(3));
        animation.setCycleCount(Animation.INDEFINITE);
        myRoot.getChildren().add(dynamicCircle);
        System.out.println(dynamicCircle.getLayoutX());
        System.out.println(dynamicCircle.getLayoutY());
        animation.play();
    }

}
