package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
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
    private File gameDirectory;

    public EditorManager(Stage stage, Scene scene, File directory){
        myStage = stage;
        homeScene = scene;
        root = new Group();
        gameDirectory = directory;
        myEditors = makeEditors();
        myEditorHomes = makeEditorHomes();
        myScene = new EditorStart(root,this, myEditors,myEditorHomes);
    }

    public void setEditorHomeScene(){
        myStage.setScene(myScene);
    }

    public void setGameDirectory(File gameDirectory){
        this.gameDirectory = gameDirectory;

    }

    public File getGameDirectory(){
      return gameDirectory;
    }

    public String getGameDirectoryString(){

       return gameDirectory.toString();
    }

//    public void loadEditorHomeScene() {
//        setEditorHomeScene();
//    }

    public void goHome(){
        myStage.setScene(homeScene);
    }

    public void changeScene(Scene scene){
        myStage.setScene(scene);
    }

    private List<EditorSuper> makeEditors(){
        List<EditorSuper> editors = new ArrayList<EditorSuper>();
        Collections.addAll(editors,new MapEditor(new Group(), this),new CharacterEditor(new Group(),this,new InputEditor(new Group(),this)),new GameplayEditor(new Group(),this));
        return editors;
    }
    private List<EditorHome> makeEditorHomes(){
        List<EditorHome> editors = new ArrayList<>();
        Collections.addAll(editors,new MapHome(new Group(),this), new CharacterHome(new Group(),this), new GameplayHome(new Group(), this));
        return editors;
    }


}
