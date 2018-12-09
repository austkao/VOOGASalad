package editor.home;

import editor.EditorManager;
import editor.interactive.MapEditor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBase;
import messenger.external.CreateStageEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapHome extends EditorHome {
    private static final String ALL_MAPS = "allmaps/";

    public MapHome(EditorManager em){
        super(new Group(), em);
        buttonNew.setOnMouseClicked(e -> nameNewObject("Create New Stage","Stage name:"));
        buttonEdit.setOnMouseClicked(e -> initializeEditor(myScroll.getSelectedItem(), null));
        buttonDelete.setOnMouseClicked(e -> deleteMap(myScroll.getSelectedItem()));
        myScroll = initializeScroll("stages");
    }

    public String toString(){
        return "Map Home";
    }

    public void setEditor(File directory, boolean isEdit){
        myEditor = new MapEditor(em, directory, isEdit);
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
            setEditor(stageDirectory, true);
            return;
        } else if(directory != null) {
            setEditor(directory, false);
            return;
        }
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("No Map Selected");
        errorAlert.setContentText("Please select a map to edit first");
        errorAlert.showAndWait();
    }

    @Override
    protected void createNewObject(String name) {
        Path stagePath = Paths.get(em.getGameDirectoryString(),"stages", name);
        System.out.println(stagePath.toString());
        File stageDirectory = stagePath.toFile();
        myEB.post(new CreateStageEvent("Create Stage", stageDirectory));
        popupStage.close();
        initializeEditor(null, stageDirectory);
    }

    protected void deleteMap(ButtonBase bb) {
        if(bb != null) {
            myScroll.removeItem();
            String stageName = bb.getText();
            File stageDirectory = Paths.get(em.getGameDirectoryString(), "stages", stageName).toFile();
            deleteDirectory(stageDirectory);
        }
    }
}
