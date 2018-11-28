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
import renderer.external.Structures.SliderBox;
import renderer.external.Structures.TextBox;
import xml.XMLParser;
import xml.XMLSaveBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author ob29
 */

public class CharacterEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "lucinaglasses.png";

    private Pane mapPane;
    private Group root;
    private VBox mySliders;
    private SliderBox healthSlider;
    private SliderBox attackSlider;
    private SliderBox defenseSlider;
    private Consumer consumer;



    public CharacterEditor(Group root, EditorManager em){
        super(root,em);
        this.root = root;
        initializeMap(400, 400);
        this.setPortrait(DEFAULT_BACKGROUND_IMAGE);
        Button addBG = getRenderSystem().makeStringButton("set portrait", Color.BLACK,true,Color.WHITE,
                30.0,50.0,200.0,200.0,50.0);
        root.getChildren().add(addBG);
        //addBG.setOnMouseClicked(e -> chooseBackground());
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        makeSliders();
        Button saveFile = getRenderSystem().makeStringButton("Save File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 150.0, 200.0, 50.0);
        root.getChildren().add(saveFile);
        saveFile.setOnMouseClicked(e -> createSaveFile());

        Button loadFile = getRenderSystem().makeStringButton("Load File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 75.0, 200.0, 50.0);
        root.getChildren().add(loadFile);
        loadFile.setOnMouseClicked(e ->loadCharacterData());
    }

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

    private void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> characterAttributes = new ArrayList<>();
        characterAttributes.add("health");
        characterAttributes.add("attack");
        characterAttributes.add("defense");
        structure.put("character", characterAttributes);
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        ArrayList<String> healthList = new ArrayList<>(List.of(Double.toString(healthSlider.getValue())));
        ArrayList<String> attackList = new ArrayList<>(List.of(Double.toString(attackSlider.getValue())));
        ArrayList<String> defenseList = new ArrayList<>(List.of(Double.toString(defenseSlider.getValue())));
        data.put("health", healthList);
        data.put("attack", attackList);
        data.put("defense", defenseList);
        try {
            generateSave(structure, data);
        } catch (Exception ex) {
            System.out.println("Invalid save");
            //ex.printStackTrace();
        }
    }

    private void loadCharacterData() {
        try {
            HashMap<String, ArrayList<String>> data = loadXMLFile("character");
            ArrayList<String> health = data.get("health");
            ArrayList<String> attack = data.get("attack");
            ArrayList<String> defense = data.get("defense");
            healthSlider.setNewValue(Double.parseDouble(health.get(0)));
            attackSlider.setNewValue(Double.parseDouble(attack.get(0)));
            defenseSlider.setNewValue(Double.parseDouble(defense.get(0)));
        } catch (Exception ex) {
            System.out.println("Cannot load file");
        }
    }
}
