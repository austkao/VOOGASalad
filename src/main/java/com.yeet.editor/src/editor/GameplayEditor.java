package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

public class GameplayEditor extends EditorSuper{
    private Group root;
    private Scene myScene;
    private EditorManager em;

    public GameplayEditor(EditorManager em){
        super(em);
    }
    public String toString(){
        return "GameplayEditor";
    }


}
