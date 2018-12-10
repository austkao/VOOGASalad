package editor;

import editor.home.CharacterHome;
import editor.home.EditorHome;
import editor.home.GameplayHome;
import editor.home.MapHome;
import editor.interactive.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import renderer.external.RenderSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

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
    private RenderSystem rs;

    public EditorManager(Stage stage, Scene scene, File directory){
        myStage = stage;
        homeScene = scene;
        root = new Group();
        gameDirectory = directory;
        myEditors = makeEditors();
        myEditorHomes = makeEditorHomes();
        rs = new RenderSystem();

        myScene = makeMyScene();
    }

    private Scene makeMyScene(){
        Scene scene = new Scene(root);
        Button back = rs.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
        back.setOnMouseClicked(e -> goHome());
        arrangeButtons();
        root.getChildren().add(back);
        return scene;
    }

    private void arrangeButtons() {
        for (int i = 0; i < myEditorHomes.size(); i++) {
            String name = myEditorHomes.get(i).toString();
            final int pos = i;
            Button nextEditor = rs.makeStringButton(name, Color.BLACK, true, Color.WHITE, 30.0, 800.0, 100.0 * i, 350.0, 50.0);
            nextEditor.setOnMouseClicked(e -> changeScene(myEditorHomes.get(pos)));
            root.getChildren().add(nextEditor);
        }
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
        Collections.addAll(editors,new MapEditor(this),new CharacterEditor(this, new InputEditor(this)),new InputEditor(this),new GameSettingsEditor(new Group(),this));
        return editors;
    }
    private List<EditorHome> makeEditorHomes(){
        List<EditorHome> editors = new ArrayList<>();
        Collections.addAll(editors,new MapHome(this), new CharacterHome(this), new GameplayHome(this));
        return editors;
    }

    public Consumer<Scene> getInputSceneSwitcher(){
        return new Consumer<Scene>() {
            @Override
            public void accept(Scene playerScene) {
                //1. switch the stage's scene to the editor input settings
                //2. when back button pressed switch back to playerScene
                    goToInput(playerScene);
            }
        };
    }

    public void goToInput(Scene scene){
        InputEditor input = new InputEditor(this);
        input.createBack(scene);
        changeScene(input);
    }


}
