package editor.interactive;


import editor.EditorManager;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * @author ob29
 */
public class GameSettingsEditor extends EditorSuper {
    private static String[] DEFAULT_GAME_MODES = new String[]{"SSBB","Street Fighter"};
    private static final String DEFAULT_SPLASH = "/Users/orgil/cs308/voogasalad_yeet/src/main/java/com.yeet.main/resources/lucinaglasses.png";
    //private Double Gravity
    private Image splashScreen;
    private File splashFile;

    public GameSettingsEditor(Group root, EditorManager em) {
        super(root,em);

        //ImageView splash = new Image(new File())



        VBox vb1 = makeVBox1();
        vb1.setLayoutX(100.0);
        vb1.setLayoutY(100.0);

        root.getChildren().addAll(vb1);
    }

    public VBox makeVBox1(){
        VBox vbox = new VBox(20.0);

        List<String> options = Arrays.asList(DEFAULT_GAME_MODES);
        SwitchButton sb = myRS.makeSwitchButtons(options,false, Color.WHITE, Color.BLACK,
                8.0,200.0,200.0,400.0,50.0);

        vbox.getChildren().addAll(sb);
        return vbox;
    }



    public String toString(){
        return "Game Settings Editor";
    }



}
