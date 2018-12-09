package editor.home;

import com.google.common.eventbus.EventBus;
import editor.EditorConstant;
import editor.EditorManager;
import editor.interactive.MapEditor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBase;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import messenger.external.CreateStageEvent;
import messenger.external.DeleteDirectoryEvent;
import messenger.external.EventBusFactory;
import renderer.external.Structures.ScrollablePaneNew;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class MapHome extends EditorHome {
    private static final String ALL_MAPS = "allmaps/";

    private EventBus myEB;


    public MapHome(Group root, EditorManager em){
        super(root,em);
        myEB = EventBusFactory.getEventBus();

        buttonNew.setOnMouseClicked(e -> nameNewObject("Create New Stage","Stage name:"));
        buttonEdit.setOnMouseClicked(e -> initializeEditor(myScroll.getSelectedItem(), null));
        buttonDelete.setOnMouseClicked(e -> deleteStage(myScroll.getSelectedItem()));
    }

    public String toString(){
        return "Map Home";
    }

    public void setEditor(File directory){
        myEditor = new MapEditor(em, directory);
        myEditor.createBack(this);
        em.changeScene(myEditor);
    }
    public String getDir(){
        return ALL_MAPS;
    }

    @Override
    public void setEditor() {

    }

    public void initializeEditor(ButtonBase bb, File directory) {
        //System.out.println(directory.getPath());
        if(bb != null) {
            String stageName = bb.getText();
            File stageDirectory = Paths.get(em.getGameDirectoryString(), "stages", stageName).toFile();
            setEditor(stageDirectory);
            return;
        } else if(directory != null) {
            setEditor(directory);
            return;
        }
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("No Map Selected");
        errorAlert.setContentText("Please select a map to edit first");
        errorAlert.showAndWait();
    }

    @Override
    public void createNewObject(String name) {
        Path stagePath = Paths.get(em.getGameDirectoryString(),"stages", name);
        System.out.println(stagePath.toString());
        File stageDirectory = stagePath.toFile();
        myEB.post(new CreateStageEvent("Create Stage", stageDirectory));
        popupStage.close();
        initializeEditor(null, stageDirectory);
    }

    private void deleteStage(ButtonBase bb) {
        if(bb != null) {
            myScroll.removeItem();
            String stageName = bb.getText();
            File stageDirectory = Paths.get(em.getGameDirectoryString(), "stages", stageName).toFile();
            myEB.post(new DeleteDirectoryEvent("Delete Stage", stageDirectory));
        }
    }

    public ScrollablePaneNew getScroll() {
        return myScroll;
    }
}
