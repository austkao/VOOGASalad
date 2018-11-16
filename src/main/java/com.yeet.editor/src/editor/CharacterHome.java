package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

public class CharacterHome extends EditorHome {

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
}
