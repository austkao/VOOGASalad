package editor.interactive;


import editor.EditorManager;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import renderer.external.RenderUtils;
import renderer.external.Structures.SwitchButton;
import renderer.external.Structures.TextBox;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    private File bgMusic;

    private int maxMinutes;
    private int maxStock;
    private TextField minutesField;
    private TextField stockField;
    private VBox myBox;


    public GameModeEditor(Group root, EditorManager em) {
        super(root,em);
        makeMyBox();

        splashView = new ImageView();





        root.getChildren().addAll(myBox,splashView);
    }

    private void getSplashFile(){
        FileChooser fileChooser = myRS.makeFileChooser("image");
        splashFile = fileChooser.showOpenDialog(getWindow());
        splashScreen = new Image(splashFile.toURI().toString());
        splashView.setImage(splashScreen);
        splashView.setPreserveRatio(true);
        splashView.setFitHeight(200.0);
        splashView.setLayoutX(400.0);
        splashView.setLayoutY(100.0);
    }


    public void makeMyBox(){
        myBox = new VBox(20.0);
        Button saveFile = myRS.makeStringButton("Save File", Color.CRIMSON, true, Color.WHITE,
                20.0,0.0, 0.0, 200.0, 50.0);
        //saveFile.setOnMouseClicked(e -> (null));

        Button pickSplash = myRS.makeStringButton("pick splash",Color.BLACK,true,Color.WHITE,20.0,60.0,100.0,200.0,50.0);
        pickSplash.setOnMouseClicked(e -> getSplashFile());

        HBox musicbox = new HBox(5);
        Button pickMusic = myRS.makeStringButton("pick main music",Color.BLACK,true,Color.WHITE,20.0,60.0,200.0,200.0,50.0);
        Text musicLabel = new Text();
        pickMusic.setOnMouseClicked(e -> {
            getMusicFile();
            musicLabel.setText(bgMusic.getName());
        });
        musicbox.getChildren().addAll(pickMusic,musicLabel);


        HBox minbox = new HBox(5);
        HBox stockbox = new HBox(5);
        Text minutes = new Text();
        Text stock = new Text();
        minutesField = new TextField();
        stockField = new TextField();
        minutesField.setOnKeyPressed(e -> displayDigit(e,minutesField,minutes,maxMinutes));
        stockField.setOnKeyPressed(e -> displayDigit(e,stockField,stock,maxStock));
        Text minLabel = new Text("Maximum Minutes");
        Text stockLabel = new Text("Maximum Lives");
        minbox.getChildren().addAll(minLabel,minutesField,minutes);
        stockbox.getChildren().addAll(stockLabel,stockField,stock);


        myBox.getChildren().addAll(saveFile,pickSplash,musicbox,minbox,stockbox);
        myBox.setLayoutX(50.0);
        myBox.setLayoutY(100.0);

    }

    private void getMusicFile(){
        FileChooser fileChooser = myRS.makeFileChooser("audio");
        bgMusic = fileChooser.showOpenDialog(getWindow());
    }


    private void displayDigit(KeyEvent e, TextField t,Text text, int val){
        if(e.getCode() == KeyCode.ENTER) {
            if (!Character.isDigit(t.getText().charAt(0))){
                System.out.println(t.getText());
                RenderUtils.throwErrorAlert("Invalid Input", "Only Numbers");
            } else {
                val = Integer.parseInt(t.getText());
                text.setText(t.getText());
            }
        }
    }


    public String toString(){
        return "Game Settings Editor";
    }



}
