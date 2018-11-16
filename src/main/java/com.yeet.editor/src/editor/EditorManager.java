package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller class for different Editor Scenes
 * @author ob29
 */

public class EditorManager {
    private Scene myScene;
    private List<EditorSuper> myEditors;
    private List<EditorHome> myEditorHomes;
    private Group root;
    private Stage myStage;
    private Scene homeScene;

    public EditorManager(Stage stage, Scene scene){
        myStage = stage;
        homeScene = scene;
        root = new Group();
        myEditors = makeEditors();
        myEditorHomes = makeEditorHomes();
        myScene = new EditorStart(root,this, myEditors,myEditorHomes);
    }

    public void setEditorHomeScene(){
        myStage.setScene(myScene);
    }

    public void goHome(){
        myStage.setScene(homeScene);
    }

    public void changeScene(Scene scene){
        myStage.setScene(scene);
    }

    private List<EditorSuper> makeEditors(){
        List<EditorSuper> editors = new ArrayList<EditorSuper>();
        Collections.addAll(editors,new MapEditor(new Group(), this),new CharacterEditor(new Group(),this),new GameplayEditor(new Group(),this));
        return editors;
    }
    private List<EditorHome> makeEditorHomes(){
        List<EditorHome> editors = new ArrayList<EditorHome>();
        Collections.addAll(editors,new MapHome(new Group(),this), new CharacterHome(new Group(),this));
        return editors;
    }


}
