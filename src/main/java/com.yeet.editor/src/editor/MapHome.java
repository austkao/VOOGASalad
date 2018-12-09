package editor;

import editor.home.EditorHome;
import editor.interactive.MapEditor;
import javafx.scene.Group;

public class MapHome extends EditorHome {
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
