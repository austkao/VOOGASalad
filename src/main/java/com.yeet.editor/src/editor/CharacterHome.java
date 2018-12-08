package editor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import renderer.external.Structures.ScrollablePaneNew;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CharacterHome extends EditorHome {
    private static final String PORTRAITS = "portraits/";


    public CharacterHome(Group root, EditorManager em){
        super(root,em);
        setEditor();
        Button input = getRender().makeStringButton("Edit Inputs",Color.BLACK,true,Color.WHITE,20.0,0.0,0.0,200.0,50.0);
        getMyBox().getChildren().add(input);
        input.setOnMouseClicked(e->setInputEditor());
    }

    private void setInputEditor(){
        myEditor = new InputEditor(new Group(), em);
        myEditor.createBack(this);
        em.changeScene(myEditor);
    }

    public void setEditor(){
        myEditor = new CharacterEditor(new Group(),em);
        myEditor.createBack(this);
    }



    public String toString(){
        return "Character Home";
    }

    public String getDir(){
        return PORTRAITS;
    }
}
