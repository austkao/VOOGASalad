package editor;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import renderer.external.Structures.ScrollablePaneNew;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapHome extends EditorHome{
    private static final String ALL_MAPS = "allmaps/";

    public MapHome(Group root, EditorManager em){
        super(root,em);
        setEditor();
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

}
