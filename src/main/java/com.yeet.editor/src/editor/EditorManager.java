package editor;

import editor.home.CharacterHome;
import editor.home.EditorHome;
import editor.home.GameplayHome;
import editor.home.MapHome;
import editor.interactive.*;
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
        List<EditorSuper> editors = new ArrayList<>();
        Collections.addAll(editors,new MapEditor(this),new CharacterEditor(this, new InputEditor(this)),new GameplayEditor(new Group(),this));
        return editors;
    }
    private List<EditorHome> makeEditorHomes(){
        List<EditorHome> editors = new ArrayList<>();
        Collections.addAll(editors,new MapHome(this), new CharacterHome(this), new GameplayHome(this));
        return editors;
    }


}
