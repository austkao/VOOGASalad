package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import renderer.external.RenderSystem;
import renderer.external.Structures.Level;
import renderer.external.Structures.ScrollablePane;

import java.io.File;
import java.util.function.Consumer;

/**
 * @author ob29
 */

public class CharacterEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "lucinaglasses.png";

    private Pane mapPane;
    private Group root;
    private VBox mySliders;




    public CharacterEditor(Group root, EditorManager em){
        super(root,em);
        this.root = root;
        initializeMap(400, 400);
        this.setPortrait(DEFAULT_BACKGROUND_IMAGE);
        Button addBG = getRenderSystem().makeStringButton("set portrait", Color.BLACK,true,Color.WHITE,
                30.0,50.0,200.0,200.0,50.0);
        root.getChildren().add(addBG);
        addBG.setOnMouseClicked(e -> choosePortrait());
        makeSliders();

    }
    Consumer consumer = new Consumer() {
        @Override
        public void accept(Object o) {
            o = o;
        }
    };

    private void makeSliders(){
        mySliders = new VBox(10);
        HBox slider1 = getRenderSystem().makeSlider("health",consumer,0.0,0.0,200.0);
        HBox slider2 = getRenderSystem().makeSlider("attack",consumer,0.0,0.0,200.0);
        HBox slider3 = getRenderSystem().makeSlider("defense",consumer,0.0,0.0,200.0);

        mySliders.getChildren().addAll(slider1,slider2,slider3);
        mySliders.setLayoutX(800.0);
        mySliders.setLayoutY(200.0);
        root.getChildren().add(mySliders);

    }


    private void initializeMap(int width, int height){
        mapPane = new Pane();
        mapPane.setPrefWidth(width);
        mapPane.setPrefHeight(height);
        mapPane.setLayoutX(275);
        mapPane.setLayoutY(100);
        root.getChildren().add(mapPane);
    }

    /**
     * User selects background, and it is applied to level.
     */
    private void choosePortrait(){
        File portrait = chooseImage("Choose Background File");
        if (portrait != null)
            this.setPortrait(portrait.toURI().toString());
    }

    public void setPortrait(String portraitURL){
        String formatted = String.format("-fx-background-image: url('%s');", portraitURL);
        formatted = formatted + String.format("-fx-background-size: cover;");
        mapPane.setStyle(formatted);
    }

    /**
     * general method for choosing an image
     * @returns file chosen
     */
    private File chooseImage(String message){
        FileChooser fileChooser = getRenderSystem().makeFileChooser("image");
        fileChooser.setTitle(message);
        return fileChooser.showOpenDialog(getWindow());
    }


    public String toString(){
        return "CharacterEditor";
    }





}
