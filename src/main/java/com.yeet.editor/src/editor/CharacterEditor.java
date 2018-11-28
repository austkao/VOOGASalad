package editor;

import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import renderer.external.Structures.SliderBox;
import renderer.external.Structures.Sprite;
import renderer.external.Structures.SpriteAnimation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author ob29
 */

public class CharacterEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "lucinaglasses.png";

    private ImageView portrait;
    private ImageView spriteSheet;


    private Group root;
    private VBox mySliders;
    private SliderBox healthSlider;
    private SliderBox attackSlider;
    private SliderBox defenseSlider;
    private Consumer consumer;



    public CharacterEditor(Group root, EditorManager em){
        super(root,em);
        this.root = root;
        portrait = initializeImageView(200, 300, 275, 25);
        spriteSheet = initializeImageView(600, 400, 25, 350);

        this.setImageView(portrait, DEFAULT_BACKGROUND_IMAGE);

        Button addBG = getRenderSystem().makeStringButton("set portrait", Color.BLACK,true,Color.WHITE,
                30.0,25.0,250.0,200.0,50.0);
        root.getChildren().add(addBG);
        addBG.setOnMouseClicked(e -> choosePortrait());


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

        Button getSpriteSheet = getRenderSystem().makeStringButton("Import Sprite Sheet", Color.FORESTGREEN, true,
                Color.WHITE, 20.0, 600.0, 25.0, 200.0, 50.0);
        root.getChildren().add(getSpriteSheet);
        getSpriteSheet.setOnMouseClicked(e -> chooseSpriteSheet());

        Button setAnimation = getRenderSystem().makeStringButton("Set Sprite Animation", Color.ORCHID, true,
                Color.WHITE, 20.0, 600.0, 100.0, 200.0, 50.0);
        root.getChildren().add(setAnimation);
        setAnimation.setOnMouseClicked(e -> makeSprite());
    }


    private void makeSprite(){
        Sprite mySprite = getRenderSystem().makeSprite(spriteSheet.getImage(), 0.0, 0.0, 110.0, 55.5);
        SpriteAnimation myAnimation = getRenderSystem().makeSpriteAnimation(mySprite, Duration.seconds(2.0), 22,
                11, 0.0, 0.0, 111.818181, 56.0);
        root.getChildren().add(mySprite);
        mySprite.setLayoutX(650);
        mySprite.setLayoutY(175);
        mySprite.setScaleX(2);
        mySprite.setScaleY(2);
        myAnimation.setCycleCount(Animation.INDEFINITE);
        myAnimation.play();


    }


    private void makeSliders(){
        mySliders = new VBox(10);
        healthSlider = getRenderSystem().makeSlider("health",consumer,0.0,0.0,200.0);
        attackSlider = getRenderSystem().makeSlider("attack",consumer,0.0,0.0,200.0);
        defenseSlider = getRenderSystem().makeSlider("defense",consumer,0.0,0.0,200.0);

        mySliders.getChildren().addAll(healthSlider,attackSlider,defenseSlider);
        mySliders.setLayoutX(900.0);
        mySliders.setLayoutY(200.0);
        root.getChildren().add(mySliders);

    }

    private ImageView initializeImageView(int width, int height, int x, int y){
        ImageView picture = new ImageView();
        picture.setFitWidth(width);
        picture.setFitHeight(height);
        picture.setLayoutX(x);
        picture.setLayoutY(y);
        root.getChildren().add(picture);
        return picture;
    }



    private void setImageView(ImageView img, String portraitURL){
        img.setImage(new Image(portraitURL));
    }

    private void chooseSpriteSheet(){
        File sprites = chooseImage("Choose Sprite Sheet");
            if (sprites !=  null){
                setImageView(spriteSheet,sprites.toURI().toString());
            }
    }
    /**
     * User selects background, and it is applied to level.
     */
    private void choosePortrait(){
        File portraitFile = chooseImage("Choose Portrait File");
        if (portraitFile != null)
            setImageView(portrait, portraitFile.toURI().toString());
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
