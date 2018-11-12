package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ob29
 * Controller class for different Editor Scenes
 */

public class EditorManager {
    private Scene myScene;
    private List<EditorSuper> myEditors;
    private Group root;
    private Stage myStage;
    private Scene homeScene;

    public EditorManager(Stage stage, Scene scene){
        myStage = stage;
        homeScene = scene;
        root = new Group();
        myEditors = makeEditors();

        myScene = new EditorStart(root,this, myEditors);

    }

    public void setEditorHomeScene(){
        myStage.setScene(myScene);
    }

    public void goBack(){
        myStage.setScene(homeScene);
    }

    public void changeScene(Scene scene){
        myStage.setScene(scene);
    }

    private List<EditorSuper> makeEditors(){
        List<EditorSuper> editors = new ArrayList<EditorSuper>();
        Collections.addAll(editors,new MapEditor(this),new CharacterEditor(this),new GameplayEditor(this));
        return editors;
    }

    public void deactivateOthers(EditorSuper editor, Group root){
        for(int i = 0; i < myEditors.size(); i++){
            if(!editor.toString().equals(myEditors.get(i).toString())){
                root.getChildren().remove(myEditors.get(i).getScene());
                System.out.println("not equal");
            }
        }
        this.root.getChildren().remove(myScene);
    }


}
