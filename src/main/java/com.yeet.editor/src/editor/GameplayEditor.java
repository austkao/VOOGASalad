package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import renderer.external.Structures.TextBox;

import java.util.function.Consumer;


/**
 * @author ob29
 */
public class GameplayEditor extends EditorSuper{

    private Consumer consumer;

    public GameplayEditor(Group root, EditorManager em) {

        super(root,em);
        RenderSystem myRenderSystem = getRenderSystem();
        Text t = myRenderSystem.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        root.getChildren().addAll(t);
    }


    public String toString(){

        return "GameplayEditor";
    }


}
