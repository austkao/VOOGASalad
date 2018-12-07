package editor;

import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import renderer.external.Structures.*;

import javax.swing.*;
import java.io.File;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author ob29
 * @author rr202
 */

public class CharacterEditor extends EditorSuper{
    private static final String DEFAULT_PORTRAIT = "lucinaglasses.png";

    private ImageView portrait;
    private ImageView spriteSheet;

    private Sprite currentSprite;
    private SpriteAnimation currentAnimation;
    private currentFrame frame;
    private StackPane mySpritePane;

    private Group root;
    private VBox mySliders;
    private SliderBox healthSlider;
    private SliderBox attackSlider;
    private SliderBox defenseSlider;
    private Consumer consumer;

    private ScrollablePane animationList;
    private Map<ScrollableItem, SpriteAnimation> scrollToAnimation;

    private Text frameText;


    private boolean first = false;

    static class currentFrame{
        int currentFrame;
        int totalFrames;
        Map<Integer, Rectangle> frameBox;
        currentFrame(){
            frameBox = new HashMap<>();
            currentFrame = -1;
            totalFrames = -1;
        }

        void setFrameBox(Rectangle r){
            frameBox.put(currentFrame, r);
        }
        Rectangle getFrameBox(){
            return frameBox.get(currentFrame);
        }
        void setTotalFrames(int t){
            totalFrames = t;
            for (int i = 1; i <= totalFrames; i++){
                frameBox.putIfAbsent(i, new Rectangle());
            }
        }
        void advance(int add){
            currentFrame = Math.floorMod((currentFrame + add),totalFrames);
        }
        public String toString(){
            if (currentFrame == -1 || totalFrames == -1){
                return "unintialized";
            }
            if (currentFrame == 0){
                return "Frame "+totalFrames+"/"+totalFrames;
            }
            return "Frame "+currentFrame+"/"+totalFrames;
        }


    }


    public CharacterEditor(Group root, EditorManager em){
        super(root,em);
        this.root = root;
        portrait = initializeImageView(200, 300, 275, 25);
        //spriteSheet = initializeImageView(600, 400, 25, 350);
        spriteSheet = new ImageView();
        spriteSheet.setLayoutX(25.0);
        spriteSheet.setLayoutY(350.0);

        scrollToAnimation = new HashMap<>();
        initializeScrollPane();

        this.setImageView(portrait, DEFAULT_PORTRAIT);


        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        frame = new currentFrame();
        makeSliders();
        makeButtons();
        initializeStackPane();
    }

    private void makeButtons(){
        Button addPortrait = myRS.makeStringButton("set portrait", Color.BLACK,true,Color.WHITE,
                30.0,25.0,250.0,200.0,50.0);
        addPortrait.setOnMouseClicked(e -> choosePortrait());
        Button saveFile = myRS.makeStringButton("Save File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 150.0, 200.0, 50.0);
        saveFile.setOnMouseClicked(e -> createSaveFile());

        Button loadFile = myRS.makeStringButton("Load File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 75.0, 200.0, 50.0);
        loadFile.setOnMouseClicked(e ->loadCharacterData());

        Button getSpriteSheet = myRS.makeStringButton("Import Sprite Sheet", Color.FORESTGREEN, true,
                Color.WHITE, 20.0, 600.0, 25.0, 200.0, 50.0);
        getSpriteSheet.setOnMouseClicked(e -> chooseSpriteSheet());

        Button setAnimation = myRS.makeStringButton("Set Sprite Animation", Color.ORCHID, true,
                Color.WHITE, 20.0, 600.0, 100.0, 200.0, 50.0);
        setAnimation.setOnMouseClicked(e -> makeSpriteAnimation());

        Button playAnimation = myRS.makeStringButton("Play Animation", Color.DARKVIOLET, true,
                Color.WHITE, 20.0, 500.0, 175.0, 200.0, 50.0);
        playAnimation.setOnMouseClicked(e -> playAnimation());

        Button stepForward = myRS.makeStringButton("Forward", Color.DARKGREEN, true,
                Color.WHITE, 20.0, 830.0, 175.0, 95.0, 50.0);
        stepForward.setOnMouseClicked(e -> stepForwardAnimation());

        Button stepBackward = myRS.makeStringButton("Backward", Color.DARKGREEN, true,
                Color.WHITE, 20.0, 725.0, 175.0, 95.0, 50.0);
        stepBackward.setOnMouseClicked(e -> stepBackAnimation());

        frameText = myRS.makeText(frame.toString(), true, 30,
                Color.AQUA, 500.0, 250.0);

        root.getChildren().addAll(saveFile, loadFile, getSpriteSheet, setAnimation, playAnimation, stepForward,
                stepBackward, frameText, addPortrait);
    }

    private void makeSliders(){
        mySliders = new VBox(10);
        healthSlider = myRS.makeSlider("health",consumer,0.0,0.0,200.0);
        attackSlider = myRS.makeSlider("attack",consumer,0.0,0.0,200.0);
        defenseSlider = myRS.makeSlider("defense",consumer,0.0,0.0,200.0);

        mySliders.getChildren().addAll(healthSlider,attackSlider,defenseSlider);
        mySliders.setLayoutX(900.0);
        mySliders.setLayoutY(200.0);
        root.getChildren().add(mySliders);
    }

    private void playAnimation(){
        currentAnimation.setRate(Math.abs(currentAnimation.getRate()));
        if (currentAnimation.getStatus().equals(Animation.Status.RUNNING)){
            currentAnimation.jumpTo(new Duration(0));
            currentAnimation.stop();
            frame.currentFrame = 1;
            frameText.setText(frame.toString());
        }
        else{
            currentAnimation.play();
        }
    }

    private void initializeStackPane(){
        mySpritePane = new StackPane();
        mySpritePane.setLayoutX(700);
        mySpritePane.setLayoutY(500);
        root.getChildren().add(mySpritePane);
    }

    private void stepAnimation(int adjust){

        currentAnimation.playFrom(currentAnimation.getCurrentTime().add(
                new Duration(currentAnimation.getCycleDuration().toMillis()*adjust / currentAnimation.getCount())));
        currentAnimation.pause();

    }

    private void stepForwardAnimation(){
        currentAnimation.setRate(Math.abs(currentAnimation.getRate()));
        stepAnimation(1);
        frame.advance(1);
        frameText.setText(frame.toString());
    }

    private void stepBackAnimation(){
        if (currentAnimation.getCurrentTime().subtract(Duration.ZERO).toMillis() < 10.0){
            return;
        }
        currentAnimation.setRate(-1*Math.abs(currentAnimation.getRate()));
        stepAnimation(-1);
        frame.advance(-1);
        frameText.setText(frame.toString());
    }
    
    private void makeSpriteAnimation(){
        File image = chooseImage("Choose thumbnail for animation");
        if (image == null){
            return;
        }
        ScrollableItem animPic = animationList.addItem(new Image(image.toURI().toString()));

        //SpriteAnimation myAnimation = myRS.makeSpriteAnimation(currentSprite, Duration.seconds(2.0), 22,
        //        11, 0.0, 0.0, 111.818181, 56.0);
        SpriteAnimation myAnimation;
        if (!first){
            myAnimation = myRS.makeSpriteAnimation(currentSprite, Duration.seconds(2.0), 11,
                    11, 6.0, 640.0, 61.0, 60.0);
            first = true;
        }
        else{
            myAnimation = myRS.makeSpriteAnimation(currentSprite, Duration.seconds(1.5), 8,
                    8, 6.0, 89.0, 61.0, 60.0);
        }
        myAnimation.setCycleCount(Animation.INDEFINITE);
        scrollToAnimation.put(animPic, myAnimation);
        animPic.getButton().setOnMouseClicked(e -> selectAnimationFromScroll(animPic));
    }

    private void initializeScrollPane(){
        animationList = new ScrollablePane(50.0,300.0);
        root.getChildren().add(animationList.getScrollPane());
    }
    
    private void selectAnimationFromScroll(ScrollableItem b){
        if (currentAnimation != null){
            currentAnimation.stop();
        }

        currentAnimation = scrollToAnimation.get(b);
        frame.currentFrame = 1;
        frame.setTotalFrames(currentAnimation.getCount());

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
        //Sprite mySprite = myRS.makeSpriteAnimation(spriteSheet.getImage(), 0.0, 0.0, 110.0, 55.0);
        Sprite mySprite = myRS.makeSprite(spriteSheet.getImage(), 6.0, 14.0, 60.0, 60.0);
        mySprite.setOnMouseClicked(e -> setRectangle(e));
        mySprite.setScaleX(5);
        mySprite.setScaleY(5);
        currentSprite = mySprite;
        mySpritePane.getChildren().add(currentSprite);
    }

    private void setRectangle(MouseEvent e){
        int x = (int)e.getX();
        int y = (int)e.getY();

        int width = Integer.parseInt(JOptionPane.showInputDialog("give the width"));
        int height = Integer.parseInt(JOptionPane.showInputDialog("give the width"));

        frame.setFrameBox(new Rectangle(x, y, width, height));
        mySpritePane.getChildren().add(frame.getFrameBox());
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
        FileChooser fileChooser = myRS.makeFileChooser("image");
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
