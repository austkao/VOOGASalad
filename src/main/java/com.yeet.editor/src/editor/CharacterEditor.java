package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import renderer.external.RenderSystem;

public class CharacterEditor extends EditorSuper{




    private Group root;
    private Scene myScene;
    private EditorManager em;

    public CharacterEditor(EditorManager em){
        super(em);

    }

    public String toString(){
        return "CharacterEditor";
    }





}
