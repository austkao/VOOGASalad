package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import renderer.external.RenderSystem;

public class EditorSuper {
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;

    private Scene myScene;
    private EditorManager em;
    private Font myEmphasisFont;
    private Font myPlainFont;

    public EditorSuper(EditorManager em){
        this.em = em;

        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT),DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
    }

    public void setRoot(Group root){
        myScene = new Scene(root);
        RenderSystem renderSystem = new RenderSystem(myPlainFont,myEmphasisFont);
        Button back = renderSystem.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
        back.setOnMouseClicked(e -> process());
        root.getChildren().add(back);
    }

    private void process(){
        em.setEditorHomeScene();
    }

    public Scene getScene() {
        return myScene;
    }
}
