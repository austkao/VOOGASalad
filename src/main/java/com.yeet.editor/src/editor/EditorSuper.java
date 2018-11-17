package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;

/**
 * Editor super class
 * @author ob29
 */

public class EditorSuper extends Scene{

    private Group root;
    private Scene myScene;
    private EditorManager em;
    private RenderSystem rs;

    public EditorSuper(Group root, EditorManager em){
        super(root);
        this.root = root;
        this.em = em;
        rs = new RenderSystem();
        Text t = rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        root.getChildren().add(t);

    }

    /**
     * Creates back button to the editor landing page
     */
    public void createBack(Scene scene){
        Button back = rs.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,1000.0,0.0,150.0,50.0);
        back.setOnMouseClicked(e -> em.changeScene(scene));
        root.getChildren().add(back);
    }

    /**
     * Creates save button to the editor landing page
     */
    public void createSave(){
        Button save = rs.makeStringButton("Save", Color.BLACK,true,Color.WHITE,30.0,800.0,0.0,150.0,50.0);
        //TODO figure out save functionality
        //save.setOnMouseClicked(e -> em.setEditorHomeScene());
        root.getChildren().add(save);
    }

    public RenderSystem getRenderSystem(){
        return rs;
    }

}
