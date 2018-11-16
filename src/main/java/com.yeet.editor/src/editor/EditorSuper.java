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
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;

    private Group root;
    private Scene myScene;
    private EditorManager em;
    private RenderSystem rs;
    private Font myEmphasisFont;
    private Font myPlainFont;

    public EditorSuper(Group root, EditorManager em){
        super(root);
        this.root = root;
        this.em = em;
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT),DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        rs = new RenderSystem(myPlainFont,myEmphasisFont);
        Text t = rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        root.getChildren().add(t);

    }

    /**
     * Creates back button to the editor landing page
     */
    public void createBack(){
        Button back = rs.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,1000.0,0.0,150.0,50.0);
        back.setOnMouseClicked(e -> em.setEditorHomeScene());
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
