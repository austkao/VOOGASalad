package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;


/**
 * @author ob29
 */
public class GameplayEditor extends EditorSuper{


    public GameplayEditor(Group root, EditorManager em) {

        super(root,em);
        RenderSystem myRenderSystem = getRenderSystem();
        Text t = myRenderSystem.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        root.getChildren().add(t);
    }


    public String toString(){

        return "GameplayEditor";
    }


}
