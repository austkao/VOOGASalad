package editor.home;

import editor.EditorManager;
import editor.interactive.GameplayEditor;
import javafx.scene.Group;

public class GameplayHome extends EditorHome {

    public GameplayHome(EditorManager em){
        super(new Group(), em);
        setEditor();
        //buttonNew.setOnMouseClicked(e ->);
    }

    @Override
    public void setEditor() {
        myEditor = new GameplayEditor(new Group(),em);
        myEditor.createBack(this);
    }

    @Override
    public void createNewObject(String name) {
        //nameNewObject();
    }

    public String toString(){
        return "Game Mode Home";
    }

    public String getDir(){
        return "portraits/";
    }
}
