package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import renderer.external.Structures.ScrollablePaneNew;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterHome extends EditorHome {
    private static final String PORTRAITS = "portraits/";

    public CharacterHome(Group root, EditorManager em){
        super(root,em);
        setEditor();
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
