package editor.home;

import editor.EditorManager;
import editor.interactive.CharacterEditor;
import editor.interactive.InputEditor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.paint.Color;
import messenger.external.CreateStageEvent;

import java.io.File;
import java.nio.file.Paths;

public class CharacterHome extends EditorHome {
    private static final String PORTRAITS = "portraits/";
    private InputEditor inputEditor;

    public CharacterHome(EditorManager em){
        super(new Group(), em);
        //setInputEditor();
        //setEditor();
        Button input = getRender().makeStringButton("Edit Inputs",Color.BLACK,true,Color.WHITE,20.0,0.0,0.0,200.0,50.0);
        getMyBox().getChildren().add(input);
        input.setOnMouseClicked(e-> em.changeScene(inputEditor));
        myScroll = initializeScroll("characters");
        buttonNew.setOnMouseClicked(e -> nameNewObject("Create Character", "Character Name:"));
        buttonDelete.setOnMouseClicked(e -> deleteCharacter(myScroll.getSelectedItem()));
        buttonEdit.setOnMouseClicked(e -> initializeEditor(myScroll.getSelectedItem(), null));
    }

    private void setInputEditor(){

    }

    public void setEditor(File directory, boolean isEdit){
        inputEditor = new InputEditor(em);
        inputEditor.createBack(this);
        myEditor = new CharacterEditor(em, inputEditor, directory, isEdit);
        myEditor.createBack(this);
        em.changeScene(myEditor);
    }

    @Override
    public void createNewObject(String name) {
        File characterDirectory = Paths.get(em.getGameDirectoryString(), "characters", name).toFile();
        setEditor(characterDirectory, false);
    }

    public void initializeEditor(ButtonBase bb, File directory) {
        //System.out.println(directory.getPath());
        if(bb != null) {
            String characterName = bb.getText();
            File characterDirectory = Paths.get(em.getGameDirectoryString(), "characters", characterName).toFile();
            System.out.println(characterDirectory.getPath());
            setEditor(characterDirectory, true);
            return;
        } else if(directory != null) {
            setEditor(directory, false);
            return;
        }
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("No Character Selected");
        errorAlert.setContentText("Please select a character to edit first");
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

    @Override
    public void setEditor() {

    }
}
