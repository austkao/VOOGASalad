package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditorManager {
    private Scene myScene;
    private Group root;
    private Stage myStage;
    private Scene homeScene;

    public EditorManager(Stage stage, Scene scene){
        root = new Group();
        myScene = new EditorStart(root,this);
        myStage = stage;
        homeScene = scene;
    }

    public void changeScene(){
        myStage.setScene(myScene);
    }

    public void goBack(){
        myStage.setScene(homeScene);
    }


}
