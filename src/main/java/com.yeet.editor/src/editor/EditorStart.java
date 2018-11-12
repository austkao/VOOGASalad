package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.List;

/**
 * @author ob29
 */

public class EditorStart extends Scene {
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;


    private Font myEmphasisFont;
    private Font myPlainFont;
    private Group root;
    private EditorManager em;
    private List<EditorSuper> editors;
    private RenderSystem renderSystem;

    public EditorStart(Group root, EditorManager em, List<EditorSuper> editors){
        super(root);
        this.root = root;
        this.em = em;
        this.editors = editors;
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT),DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        renderSystem = new RenderSystem(myPlainFont,myEmphasisFont);
        Button back = renderSystem.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
        back.setOnMouseClicked(e -> process());
        root.getChildren().add(back);
        arrangeButtons();
    }

    private void process(){
        root.getChildren().remove(this);
        em.goBack();
    }

    private void arrangeButtons(){
        for(int i = 0; i < editors.size(); i++){
            String name = editors.get(i).toString();
            System.out.println(name);
            final int pos = i;
            Button nextEditor = renderSystem.makeStringButton(name, Color.BLACK,true,Color.WHITE,30.0,800.0,100.0*i,350.0,50.0);
            nextEditor.setOnMouseClicked(e -> go(e,editors.get(pos)));
            root.getChildren().add(nextEditor);
        }
    }

    private void go(MouseEvent e, EditorSuper editor){
        em.deactivateOthers(editor, root);
        editor.setRoot(new Group());
        em.changeScene(editor.getScene());


    }





}
