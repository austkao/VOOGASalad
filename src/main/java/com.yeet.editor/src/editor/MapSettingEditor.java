package editor;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import renderer.external.Structures.TextBox;

import java.util.function.Consumer;

public class MapSettingEditor extends EditorSuper{
    private static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    private static final int DEFAULT_PLAIN_FONTSIZE = 25;

    private Consumer consumer;
    private MapEditor myParentEditor;

    public MapSettingEditor(Group root, EditorManager em, MapEditor parentMapEditor) {
        super(root, em);
        myParentEditor = parentMapEditor;
        RenderSystem myRenderSystem = getRenderSystem();
        Text t = myRenderSystem.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        Font myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        TextBox myBGM = myRenderSystem.makeTextField(consumer, "Background Music", 100.0, 100.0, 800.0, 50.0, myPlainFont);
        TextBox myBgImage = myRenderSystem.makeTextField(consumer, "Background Image", 100.0, 200.0,800.0, 50.0, myPlainFont);
        Button myBGMButton = myRenderSystem.makeStringButton("Set Background Music", Color.BLACK,true,Color.WHITE,
                30.0,950.0,100.0,200.0,50.0);
        Button myImageButton = myRenderSystem.makeStringButton("Set Background Image", Color.BLACK,true,Color.WHITE,
                30.0,950.0,200.0,200.0,50.0);
        root.getChildren().addAll(myBGM, myBGMButton, myBgImage, myImageButton);
        Button back = myRenderSystem.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
        back.setOnMouseClicked(e -> em.changeScene(myParentEditor));
        root.getChildren().add(back);
    }

    public String toString(){
        return "Map Settings Editor";
    }
}
