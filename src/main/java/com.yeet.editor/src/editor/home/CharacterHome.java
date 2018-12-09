package editor.home;

import editor.EditorManager;
import editor.interactive.CharacterEditor;
import editor.interactive.InputEditor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.paint.Color;

import java.io.File;
import java.nio.file.Paths;

public class CharacterHome extends EditorHome {
    private static final String PORTRAITS = "portraits/";
    private InputEditor inputEditor;

    public CharacterHome(EditorManager em){
        super(new Group(), em);
        setInputEditor();
        setEditor();
        Button input = getRender().makeStringButton("Edit Inputs",Color.BLACK,true,Color.WHITE,20.0,0.0,0.0,200.0,50.0);
        getMyBox().getChildren().add(input);
        input.setOnMouseClicked(e-> em.changeScene(inputEditor));
        myScroll = initializeScroll("characters");
        buttonDelete.setOnMouseClicked(e -> deleteCharacter(myScroll.getSelectedItem()));
    }

    private void setInputEditor(){
        inputEditor = new InputEditor(em);
        inputEditor.createBack(this);
    }

    public void setEditor(){
        myEditor = new CharacterEditor(em, new InputEditor(em));
        myEditor.createBack(this);
    }

    @Override
    public void createNewObject(String name) {
        em.changeScene(myEditor);
    }

    public void initializeEditor(ButtonBase bb, File directory) {
        //System.out.println(directory.getPath());
        if(bb != null) {
            String characterName = bb.getText();
            File characterDirectory = Paths.get(em.getGameDirectoryString(), "characters", characterName).toFile();
            //setEditor(characterDirectory, true);
            return;
        } else if(directory != null) {
            //setEditor(directory, false);
            return;
        }
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("No Map Selected");
        errorAlert.setContentText("Please select a map to edit first");
        errorAlert.showAndWait();
    }

    private void deleteCharacter(ButtonBase bb) {
        if(bb != null) {
            myScroll.removeItem();
            String characterName = bb.getText();
            File stageDirectory = Paths.get(em.getGameDirectoryString(), "characters", characterName).toFile();
            deleteDirectory(stageDirectory);
        }
    }

    public String toString(){
        return "Character Home";
    }

    public String getDir(){
        return PORTRAITS;
    }
}
