package editor;

import javafx.scene.Group;

public class MapHome extends EditorHome{

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

}
