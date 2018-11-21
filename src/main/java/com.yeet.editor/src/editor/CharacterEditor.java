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
import xml.XMLParser;
import xml.XMLSaveBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author ob29
 */

public class CharacterEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "lucinaglasses.png";

    private Pane mapPane;
    private Group root;
    private String backgroundURL;
    private VBox mySliders;
    private HBox healthSlider;
    private HBox attackSlider;
    private HBox defenseSlider;



    public CharacterEditor(Group root, EditorManager em){
        super(root,em);
        this.root = root;
        initializeMap(400, 400);
        this.setBackground(DEFAULT_BACKGROUND_IMAGE);
        Button addBG = getRenderSystem().makeStringButton("set Background", Color.BLACK,true,Color.WHITE,
                30.0,50.0,200.0,200.0,50.0);
        root.getChildren().add(addBG);
        addBG.setOnMouseClicked(e -> chooseBackground());
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
        healthSlider = getRenderSystem().makeSlider("health",consumer,0.0,0.0,200.0);
        attackSlider = getRenderSystem().makeSlider("attack",consumer,0.0,0.0,200.0);
        defenseSlider = getRenderSystem().makeSlider("defense",consumer,0.0,0.0,200.0);

        mySliders.getChildren().addAll(healthSlider,attackSlider,defenseSlider);
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
    private void chooseBackground(){
        File backgroundFile = chooseImage("Choose Background File");
        if (backgroundFile != null)
            this.setBackground(backgroundFile.toURI().toString());
    }

    public void setBackground(String backgroundURL){
        this.backgroundURL = backgroundURL;
        String formatted = String.format("-fx-background-image: url('%s');", backgroundURL);
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

    private void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> characterAttributes = new ArrayList<>();
        characterAttributes.add("health");
        characterAttributes.add("attack");
        characterAttributes.add("defense");
        structure.put("character", characterAttributes);
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        try {
            generateSave(structure, data);
        } catch (Exception ex) {
            System.out.println("Invalid save");
            //ex.printStackTrace();
        }
    }

    private void loadXMLFile() {
        try {
            //XMLParser parser = new XMLParser();
            //HashMap<String, ArrayList<String>> data = parser.parseFileForElement("character");
            //ArrayList<String> health = data.get("health");
            //ArrayList<String> attack = data.get("attack");
            //ArrayList<String> defense = data.get("defense");
        } catch (Exception ex) {
            System.out.println("Cannot load file");
        }
    }



}
