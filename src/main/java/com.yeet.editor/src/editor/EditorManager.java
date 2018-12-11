package editor;

import editor.home.CharacterHome;
import editor.home.EditorHome;
import editor.home.MapHome;
import editor.interactive.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

public class EditorManager extends Scene {
    private List<EditorSuper> myEditors;
    private List<EditorHome> myEditorHomes;
    private Group myRoot;
    private Stage myStage;
    private Scene homeScene;
    private File gameDirectory;
    private RenderSystem rs;
    private EditorSuper mySettingsEditor;

    public EditorManager(Stage stage, Scene scene, Group root, File directory){
        super(root);
        myStage = stage;
        homeScene = scene;
        myRoot = root;
        gameDirectory = directory;
        myEditors = makeEditors();
        myEditorHomes = makeEditorHomes();
        rs = new RenderSystem();
        Button back = createBack();
        back.setOnMouseClicked(e -> goBack());
        arrangeButtons();
        Text title = createTitle();
        myRoot.getChildren().addAll(back, title);
    }

    private void arrangeButtons() {
        for (int i = 0; i < myEditorHomes.size(); i++) {
            String name = myEditorHomes.get(i).toString();
            final int pos = i;
            Button nextEditor = rs.makeStringButton(name, Color.BLACK, true, Color.WHITE, 30.0, 800.0, 100.0 * i, 350.0, 50.0);
            nextEditor.setOnMouseClicked(e -> changeScene(myEditorHomes.get(pos)));
            myRoot.getChildren().add(nextEditor);
        }
        Button settings = rs.makeStringButton("Game Settings", Color.BLACK, true, Color.WHITE, 30.0, 800.0, 100.0 * 2, 350.0, 50.0);
        mySettingsEditor = myEditors.get(myEditors.size()-1);
        settings.setOnMouseClicked(e -> changeScene(mySettingsEditor));
        myRoot.getChildren().add(settings);
    }

    public void setEditorHomeScene(){
        myStage.setScene(this);
    }

    public void setGameDirectory(File gameDirectory){
        this.gameDirectory = gameDirectory;

    }

    public Text createTitle() {
        return rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
    }

    public void goBack() {
        myStage.setScene(homeScene);
    }

    public Button createBack() {
        return rs.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
    }

    public String getGameDirectoryString(){

       return gameDirectory.toString();
    }

    public void changeScene(Scene scene){
        myStage.setScene(scene);
    }

    private List<EditorSuper> makeEditors(){
        List<EditorSuper> editors = new ArrayList<>();
        Collections.addAll(editors,new MapEditor(this, this),new CharacterEditor(this, new InputEditor(this, this), this),new InputEditor(this, this),new GameModeEditor(this,this));
        return editors;
    }
    private List<EditorHome> makeEditorHomes(){
        List<EditorHome> editors = new ArrayList<>();
        Collections.addAll(editors,new MapHome(this), new CharacterHome(this));//;, new GameModeHome(this));
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
        InputEditor input = new InputEditor(this, this);
        changeScene(input);
    }

    @Override
    public String toString() {
        return "Game Editor";
    }
}
