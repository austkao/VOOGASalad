package editor.interactive;


import editor.EditorManager;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * @author ob29
 */
public class GameModeEditor extends EditorSuper {
    private static String[] DEFAULT_GAME_MODES = new String[]{"SSBB","Street Fighter"};
    private static final String DEFAULT_SPLASH = "/Users/orgil/cs308/voogasalad_yeet/src/main/java/com.yeet.main/resources/lucinaglasses.png";
    private Image splashScreen;
    private File splashFile;
    private ImageView splashView;

    public GameModeEditor(Group root, EditorManager em) {
        super(root,em);
        Button pickSplash = myRS.makeStringButton("pick splash",Color.BLACK,true,Color.WHITE,20.0,60.0,100.0,150.0,50.0);
        pickSplash.setOnMouseClicked(e -> getSplashFile());

        splashView = new ImageView();

        root.getChildren().addAll(pickSplash,splashView);
    }

    private void getSplashFile(){
        FileChooser fileChooser = myRS.makeFileChooser("image");
        splashFile = fileChooser.showOpenDialog(getWindow());
        splashScreen = new Image(splashFile.toURI().toString());
        splashView.setImage(splashScreen);
        splashView.setPreserveRatio(true);
        splashView.setFitHeight(200.0);

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
