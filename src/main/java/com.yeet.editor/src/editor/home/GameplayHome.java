package editor.home;

import editor.EditorManager;
import editor.interactive.GameplayEditor;
import javafx.scene.Group;

public class GameplayHome extends EditorHome {

    public GameplayHome(Group root, EditorManager em){
        super(root,em);
        setEditor();
    }

    @Override
    public void setEditor() {
        myEditor = new GameplayEditor(new Group(),em);
        myEditor.createBack(this);
    }

    @Override
    public void createNewObject(String name) {

    }

    public String toString(){
        return "Game Home";
    }

    public String getDir(){
        return "portraits/";
    }
}
