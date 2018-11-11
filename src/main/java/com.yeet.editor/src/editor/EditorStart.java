package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import javafx.scene.control.Button;



public class EditorStart extends Scene {
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;


    private Font myEmphasisFont;
    private Font myPlainFont;
    private Group root;
    private EditorManager em;
    public EditorStart(Group root, EditorManager em){
        super(root);
        this.root = root;
        this.em = em;
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT),DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        RenderSystem renderSystem = new RenderSystem(myPlainFont,myEmphasisFont);
        Button back = renderSystem.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
        back.setOnMouseClicked(e -> process());
        root.getChildren().add(back);
    }

    private void process(){
        root.getChildren().remove(this);
        em.goBack();

    }



}
