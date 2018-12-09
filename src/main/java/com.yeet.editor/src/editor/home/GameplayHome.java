package editor.home;

import editor.EditorManager;
import editor.interactive.GameplayEditor;
import javafx.scene.Group;
import javafx.scene.control.ButtonBase;

public class GameplayHome extends EditorHome {

    public GameplayHome(EditorManager em){
        super(new Group(), em);
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

    @Override
    protected void deleteDirectory(ButtonBase bb) {

    }

    public String toString(){
        return "Game Home";
    }

    public String getDir(){
        return "portraits/";
    }
}
