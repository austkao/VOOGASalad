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
 * Editor landing page that provides access to specific editor views
 * @author ob29
 */

public class EditorStart extends Scene {

    private Group root;
    private EditorManager em;
    private List<EditorSuper> editors;
    private List<EditorHome> editorHomes;
    private RenderSystem renderSystem;

    public EditorStart(Group root, EditorManager em, List<EditorSuper> editors, List<EditorHome> editorHomes){
        super(root);
        this.root = root;
        this.em = em;
        this.editors = editors;
        this.editorHomes = editorHomes;
        renderSystem = new RenderSystem();
        Button back = renderSystem.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
        back.setOnMouseClicked(e -> em.goHome());
        root.getChildren().add(back);
        arrangeButtons();
    }



    private void arrangeButtons(){
        for(int i = 0; i < editors.size(); i++){
            String name = editors.get(i).toString();
            final int pos = i;
            Button nextEditor = renderSystem.makeStringButton(name, Color.BLACK,true,Color.WHITE,30.0,800.0,100.0*i,350.0,50.0);
            nextEditor.setOnMouseClicked(e -> go(e,editorHomes.get(pos)));
            root.getChildren().add(nextEditor);
        }
    }

    /**
     *
     * @param e
     * @param editor
     */
    private void go(MouseEvent e, EditorHome editorHome){

//        editor.createBack();
//        editor.createSave();
//        editorHome.createBack();
//        editorHome.createSave();
        em.changeScene(editorHome);

    }





}
