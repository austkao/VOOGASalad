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
    private List<EditorScreen> myEditorHomes;
    private Group myRoot;
    private Stage myStage;
    private Scene homeScene;
    private File gameDirectory;
    private RenderSystem rs;

    public EditorManager(Stage stage, Scene scene, Group root, File directory, RenderSystem rs){
        super(root);
        myStage = stage;
        homeScene = scene;
        myRoot = root;
        gameDirectory = directory;
        myEditorHomes = makeEditorHomes();
        this.rs = rs;
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
            nextEditor.setOnMouseClicked(e -> changeSceneEditor(myEditorHomes.get(pos)));
            myRoot.getChildren().add(nextEditor);
        }
    }

    public void setEditorHomeScene(){
        myStage.setScene(this);
    }

    public void setGameDirectory(File gameDirectory){
        this.gameDirectory = gameDirectory;

    }

    private Text createTitle() {
        return rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
    }

    private void goBack() {
        myStage.setScene(homeScene);
    }

    private Button createBack() {
        return rs.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
    }

    public String getGameDirectoryString(){

       return gameDirectory.toString();
    }

    public void changeScene(Scene scene){
        myStage.setScene(scene);
    }

    public void changeSceneEditor(EditorScreen screen){
        myStage.setScene(screen.getScene());
    }

    private List<EditorScreen> makeEditorHomes(){
        List<EditorScreen> editors = new ArrayList<>();
        Collections.addAll(editors,new MapHome(this), new CharacterHome(this),new InputEditor(this,this));//;, new GameModeHome(this));
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

    private void goToInput(Scene scene){
        //InputEditor input = new InputEditor(this, this);
        changeScene(myEditorHomes.get(myEditorHomes.size()-1).getScene());
    }

    @Override
    public String toString() {
        return "Game Editor";
    }
}
