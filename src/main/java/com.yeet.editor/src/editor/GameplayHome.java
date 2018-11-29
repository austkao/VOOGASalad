package editor;

import javafx.scene.Group;

public class GameplayHome extends EditorHome{

    public GameplayHome(Group root, EditorManager em){
        super(root,em);
        setEditor();
    }

    @Override
    public void setEditor() {
        myEditor = new GameplayEditor(new Group(),em);
        myEditor.createBack(this);
    }

    public String toString(){
        return "Game Home";
    }
}