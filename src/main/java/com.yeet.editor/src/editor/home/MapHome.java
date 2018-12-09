package editor.home;

import com.google.common.eventbus.EventBus;
import editor.EditorConstant;
import editor.EditorManager;
import editor.interactive.MapEditor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import messenger.external.CreateStageEvent;
import messenger.external.EventBusFactory;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class MapHome extends EditorHome {
    private static final String ALL_MAPS = "allmaps/";

    private EventBus myEB;
    private Consumer consumer;
    private Stage popupStage;

    public MapHome(Group root, EditorManager em){
        super(root,em);
        setEditor();
        myEB = EventBusFactory.getEventBus();
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        buttonNew.setOnMouseClicked(e -> nameNewStage());
        buttonEdit.setOnMouseClicked(e -> initializeEditor(myScroll.getSelectedItem()));
        buttonDelete.setOnMouseClicked(e -> deleteStage());
    }

    public String toString(){
        return "Map Home";
    }

    public void setEditor(){
        myEditor = new MapEditor(new Group(),em);
        myEditor.createBack(this);
    }
    public String getDir(){
        return ALL_MAPS;
    }


    public void nameNewStage() {
        popupStage = new Stage();
        popupStage.setTitle("Create New Stage");
        TextBox stageName = rs.makeTextField(consumer, "", 100.0,20.0,200.0,30.0, rs.getPlainFont());
        Text stageLabel = rs.makeText("Stage Name:", false, 12, Color.BLACK, 20.0, 50.0);
        Button create = rs.makeStringButton("Create", Color.BLACK, false, Color.GRAY, 12.0,50.0, 100.0, 100.0, 30.0);
        Button cancel = rs.makeStringButton("Cancel", Color.BLACK, false, Color.GRAY, 12.0,200.0, 100.0, 100.0, 30.0);
        create.setOnMouseClicked(e -> createNewStage(stageName.getText()));
        cancel.setOnMouseClicked(e -> popupStage.close());
        Scene creationScene = new Scene(new Group(stageName, stageLabel, create, cancel), 400, 200);
        popupStage.setScene(creationScene);
        popupStage.show();
    }

    public void initializeEditor(ButtonBase bb) {
        if(bb != null) {
            String stageName = bb.getText();
            Path stagePropertiesPath = Paths.get(em.getGameDirectoryString(), "stages", stageName, "stageProperties.xml");
            File stagePropertiesXML = stagePropertiesPath.toFile();
            myEditor = new MapEditor(new Group(), em, stagePropertiesXML);
            myEditor.createBack(this);
        }
        em.changeScene(myEditor);
    }

    public void createNewStage(String name) {
        Path stagePath = Paths.get(em.getGameDirectoryString(),"stages", name);
        System.out.println(stagePath.toString());
        File stageDirectory = stagePath.toFile();
        myEB.post(new CreateStageEvent("Create Stage", stageDirectory));
        popupStage.close();
        initializeEditor(null);
    }

    private void deleteStage() {

    }
}
