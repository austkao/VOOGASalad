package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import renderer.external.Renderer;

/** Creates a dynamic display for all stages available to the user, allows the user to choose
 *  a stage to do battle on
 *  @author bpx
 */
public class StageSelectScreen extends Screen {

    public StageSelectScreen(Group root, Renderer renderer, SceneSwitch prevScene, SceneSwitch nextScene) {
        super(root, renderer);
        VBox mainContainer = new VBox();
        mainContainer.setPrefSize(1280.0,800.0);
        mainContainer.setAlignment(Pos.CENTER);
        HBox topBar = new HBox();
        topBar.setPrefSize(1280.0,55.0);
        topBar.setAlignment(Pos.CENTER_LEFT);
        //set up back button
        Rectangle topBarSpacer = new Rectangle(15.0,55.0, Color.TRANSPARENT);
        ImageView backButton = new ImageView((new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))));
        backButton.setFitHeight(50.0);
        backButton.setFitWidth(50.0);
        Rectangle buttonHolderSpacer = new Rectangle(50.0,15.0, Color.TRANSPARENT);
        VBox buttonHolder = new VBox(buttonHolderSpacer,backButton);
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.setOnMouseEntered(event -> {
            buttonHolder.setScaleX(1.1);
            buttonHolder.setScaleY(1.1);
        });
        buttonHolder.setOnMouseExited(event -> {
            buttonHolder.setScaleX(1.0);
            buttonHolder.setScaleY(1.0);
        });
        buttonHolder.setOnMousePressed(event -> prevScene.switchScene());
        HBox stageContainer = new HBox(10.0);
        stageContainer.setPrefSize(1280.0,745.0);
        stageContainer.setAlignment(Pos.CENTER);
        VBox stagePreview = new VBox();
        stagePreview.setPrefSize(415.0,360.0);
        stagePreview.setAlignment(Pos.CENTER);
        ImageView thumbnailPreview = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("random_stage.png")));
        thumbnailPreview.setFitWidth(415.0);
        thumbnailPreview.setFitHeight(260.0);
        StackPane stageNameHolder = new StackPane();
        stageNameHolder.setPrefSize(415.0,100.0);
        stageNameHolder.setAlignment(Pos.CENTER);
        stageNameHolder.setStyle("-fx-background-color: black");
        Text stageName = this.getMyRenderer().makeText("Random",true,60, Color.WHITE,0.0,0.0);
        VBox stageGrid = new VBox();
        stageGrid.setPrefSize(835.0,600.0);
        stageGrid.setStyle("-fx-background-color: black");
        topBar.getChildren().addAll(topBarSpacer,buttonHolder);
        stageNameHolder.getChildren().addAll(stageName);
        stagePreview.getChildren().addAll(thumbnailPreview,stageNameHolder);
        stageContainer.getChildren().addAll(stagePreview,stageGrid);
        mainContainer.getChildren().addAll(topBar,stageContainer);
        super.getMyRoot().getChildren().addAll(mainContainer);
        super.setOnMousePressed(event -> nextScene.switchScene());
    }
}
