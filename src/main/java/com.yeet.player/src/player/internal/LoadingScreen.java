package player.internal;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import renderer.external.Renderer;
import renderer.external.Structures.OrbitAnimation;

/** Placeholder {@code Screen} while other elements are being initialized
 *  @author bpx
 */
public class LoadingScreen implements Screen{

    private Stage myStage;
    private Group myRoot;
    private Scene myScene;
    private Renderer myRenderer;

    private Animation animation;

    /** Creates a preset loading screen */
    public LoadingScreen(Stage stage, Renderer renderer){
        myStage = stage;
        myRoot = new Group();
        myRenderer = renderer;
        myScene = new Scene(myRoot);
        HBox textBox = new HBox(myRenderer.makeText("Loading",false,50, Color.BLACK,0.0,0.0));
        textBox.setLayoutX(548.0);
        textBox.setLayoutY(366.0);
        myRoot.getChildren().add(textBox);
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
    }

    @Override
    public void setActive() {
        myStage.setScene(myScene);
        animation.play();
    }
}
