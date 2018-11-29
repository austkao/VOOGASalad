package editor;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.util.function.Consumer;

public class MapSettingEditor extends EditorSuper{
    private static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    private static final int DEFAULT_PLAIN_FONTSIZE = 25;
    private static final String DEFAULT_BGM = "Theme.m4a";

    private Consumer consumer;
    private MapEditor myParentEditor;
    private String myBGMFileName;
    private RenderSystem myRenderSystem;
    private TextBox myBGM;
    private EditorManager myEM;

    public MapSettingEditor(Group root, EditorManager em, MapEditor parentMapEditor) {
        super(root, em);
        myEM = em;
        myParentEditor = parentMapEditor;
        myRenderSystem = getRenderSystem();
        Text t = myRenderSystem.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        Font myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        myBGMFileName = DEFAULT_BGM;
        myBGM = myRenderSystem.makeTextField(consumer, myBGMFileName, 100.0, 100.0, 800.0, 50.0, myPlainFont);
        TextBox myBgImage = myRenderSystem.makeTextField(consumer, "Background Image", 100.0, 200.0,800.0, 50.0, myPlainFont);
        Button myBGMButton = myRenderSystem.makeStringButton("Set Background Music", Color.BLACK,true,Color.WHITE,
                30.0,950.0,100.0,200.0,50.0);
        myBGMButton.setOnMouseClicked(e -> setBackgroundMusic());
        Button myImageButton = myRenderSystem.makeStringButton("Set Background Image", Color.BLACK,true,Color.WHITE,
                30.0,950.0,200.0,200.0,50.0);
        root.getChildren().addAll(myBGM, myBGMButton);
        Button back = myRenderSystem.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
        back.setOnMouseClicked(e -> em.changeScene(myParentEditor));
        root.getChildren().add(back);
    }

    public String toString(){
        return "Map Settings Editor";
    }

    public void setBackgroundMusic() {
        FileChooser myBGMDirectory = myRenderSystem.makeFileChooser("all");
        myBGMDirectory.setInitialDirectory(myEM.getGameDirectory());
        File bgmFile = myBGMDirectory.showOpenDialog(new Stage());
        myBGM.setText(bgmFile.getName());
    }
}
