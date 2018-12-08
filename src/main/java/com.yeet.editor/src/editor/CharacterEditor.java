package editor;

import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import renderer.external.Structures.*;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author ob29
 * @author rr202
 */

public class CharacterEditor extends EditorSuper{
    private static final String DEFAULT_PORTRAIT = "lucinaglasses.png";
    private static final String HIT_TEXT = "HITBOX";
    private static final String HURT_TEXT = "HURTBOX";

    private ImageView portrait;

    private ImageView spriteSheet;

    private Sprite currentSprite;

    //Animation variables
    private SpriteAnimation currentAnimation;
    private Pane mySpritePane;
    private ScrollablePane animationList;
    private Map<ScrollableItem, SpriteAnimation> scrollToAnimation;
    private Map<SpriteAnimation, FrameInfo> animationFrame;

    private Group root;
    private VBox mySliders;
    private SliderBox healthSlider;
    private SliderBox attackSlider;
    private SliderBox defenseSlider;
    private Consumer consumer;
    private Text frameText;
    private SwitchButton hitOrHurt;


    private boolean first = false;


    static class FrameInfo {
        int currentFrame;
        int totalFrames;
        Map<Integer, Rectangle> hitBoxes;
        Map<Integer, Rectangle> hurtBoxes;
        String input;

        FrameInfo(int total, String inputString){
            input = inputString;
            currentFrame = -1;
            totalFrames = total;
            hitBoxes = new HashMap<>();
            hurtBoxes = new HashMap<>();
            for (int i = 1; i <= totalFrames; i++){
                hitBoxes.putIfAbsent(i, new Rectangle());
                hurtBoxes.putIfAbsent(i, new Rectangle());
            }
        }

        void setInput(String s){
            input = s;
        }
        String getInput(){
            return input;
        }

        void setHitBox(Rectangle r){
            hitBoxes.put(currentFrame, r);
        }
        void setHurtBox(Rectangle r){
            hurtBoxes.put(currentFrame, r);
        }
        Rectangle getHitBox(){
            return hitBoxes.get(currentFrame);
        }
        Rectangle getHurtBox(){
            return hurtBoxes.get(currentFrame);
        }
        void advance(int add){
            currentFrame = Math.floorMod((currentFrame + add),totalFrames);
            if (currentFrame == 0){
                currentFrame = totalFrames;
            }
        }
        public String toString(){
            if (currentFrame == -1 || totalFrames == -1){
                return "Animation Not Set";
            }
            return "Frame "+currentFrame+"/"+totalFrames + "\ninput: " + input;
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
        animationFrame = new HashMap<>();
        makeSliders();
        makeButtons();
        initializeSpritePane();
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

        frameText = myRS.makeText("Animation Not Set", true, 30,
                Color.AQUA, 500.0, 250.0);

        List<String> options = new ArrayList<>();
        options.add(HURT_TEXT);
        options.add(HIT_TEXT);
        hitOrHurt = myRS.makeSwitchButtons(options, true, Color.BLUE,
                Color.RED, 5.0,400.0, 400.0, 50.0, 25.0 );

        root.getChildren().addAll(addPortrait, saveFile, loadFile, getSpriteSheet, setAnimation, playAnimation,
                stepForward, stepBackward, frameText, hitOrHurt );
    }

    private void makeSliders(){
        mySliders = new VBox(10);
        healthSlider = myRS.makeSlider("health",50.0,consumer,0.0,0.0,200.0);
        attackSlider = myRS.makeSlider("attack",50.0,consumer,0.0,0.0,200.0);
        defenseSlider = myRS.makeSlider("defense",50.0,consumer,0.0,0.0,200.0);

        mySliders.getChildren().addAll(healthSlider,attackSlider,defenseSlider);
        mySliders.setLayoutX(900.0);
        mySliders.setLayoutY(200.0);
        root.getChildren().add(mySliders);
    }

    private void playAnimation(){

        if (testNull(currentAnimation, "Current Animation not set")){
            return;
        }
        FrameInfo frame = animationFrame.get(currentAnimation);
        mySpritePane.getChildren().remove(frame.getHitBox());
        mySpritePane.getChildren().remove(frame.getHurtBox());
        currentAnimation.setRate(Math.abs(currentAnimation.getRate()));
        if (currentAnimation.getStatus().equals(Animation.Status.RUNNING)){
            currentAnimation.jumpTo(new Duration(0));
            currentAnimation.stop();
            frame.currentFrame = 1;
            frameText.setText(frame.toString());
            mySpritePane.getChildren().add(frame.getHitBox());
            mySpritePane.getChildren().add(frame.getHurtBox());
        }
        else{
            currentAnimation.play();
        }
    }

    private void initializeSpritePane(){
        mySpritePane = new Pane();
        mySpritePane.setLayoutX(600);
        mySpritePane.setLayoutY(400);
        mySpritePane.setMinSize(300, 300);
        mySpritePane.setMaxSize(300, 300);
        root.getChildren().add(mySpritePane);
    }

    /*
        Alert system found at:
        https://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
     */
    private boolean testNull(Object object, String message){
        if (object == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message,
                    ButtonType.OK);
            alert.showAndWait();
            return true;
        }
        return false;
    }


    private void stepAnimation(int adjust){
        FrameInfo frame = animationFrame.get(currentAnimation);
        mySpritePane.getChildren().remove(frame.getHitBox());
        mySpritePane.getChildren().remove(frame.getHurtBox());
        //currentAnimation.setRate(adjust*Math.abs(currentAnimation.getRate()));
        currentAnimation.playFrom(currentAnimation.getCurrentTime().add(
                new Duration(currentAnimation.getCycleDuration().toMillis()*adjust / currentAnimation.getCount())));
        currentAnimation.pause();
        frame.advance(adjust);
        frameText.setText(frame.toString());
        mySpritePane.getChildren().add(frame.getHitBox());
        mySpritePane.getChildren().add(frame.getHurtBox());
    }

    private void stepForwardAnimation(){
        if (testNull(currentAnimation, "Animation not set")){
            return;
        }
        stepAnimation(1);
    }

    private void stepBackAnimation(){
        if (testNull(currentAnimation, "Animation not set")){
            return;
        }
        else if (currentAnimation.getCurrentTime().subtract(Duration.ZERO).toMillis() < 10.0){
            return;
        }
        stepAnimation(-1);
    }


    private String getCombo(){
        String combo = "";
        int inputNum = 1;
        String nextInput = showAlertInputOptions(inputNum);
        while (!"".equals(nextInput)){
            System.out.println(nextInput);
            combo = combo + "_" + nextInput;
            inputNum++;
            nextInput = showAlertInputOptions(inputNum);
        }
        System.out.println(combo);
        return combo;
    }

    private String showAlertInputOptions(int num){
        // TODO: 12/8/2018 incorporate custom list
        Set<String> options = new HashSet<String>();
        options.add("test1");
        options.add("hello");
        options.add("rahul");

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Enter input #" + num);

        boolean disabled = false;

        for (String option: options){
            ButtonType optionButton = new ButtonType(option, ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(optionButton);
            dialog.getDialogPane().lookupButton(optionButton).setDisable(disabled);
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        ButtonType input = dialog.showAndWait().orElse(ButtonType.CLOSE);

        if (input == ButtonType.CLOSE){
            return "";
        }
        return input.getText();

    }

    private void makeSpriteAnimation(){
        if (testNull(spriteSheet, "Sprite Sheet Not Set")){
            return;
        }
        File image = chooseImage("Choose thumbnail for animation");
        if (image == null){
            return;
        }
        ScrollableItem animPic = animationList.addItem(new Image(image.toURI().toString()));

        String inputString = getCombo();

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
        animationFrame.put(myAnimation, new FrameInfo(myAnimation.getCount(), inputString));
    }

    private void initializeScrollPane(){
        animationList = new ScrollablePane(50.0,300.0);
        root.getChildren().add(animationList.getScrollPane());
    }
    
    private void selectAnimationFromScroll(ScrollableItem b){
        if (currentAnimation != null){
            currentAnimation.stop();
            mySpritePane.getChildren().remove(animationFrame.get(currentAnimation).getHitBox());
            mySpritePane.getChildren().remove(animationFrame.get(currentAnimation).getHurtBox());
        }

        currentAnimation = scrollToAnimation.get(b);
        FrameInfo frame = animationFrame.get(currentAnimation);
        frame.currentFrame = 1;
        stepForwardAnimation();
        stepBackAnimation();
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
        mySpritePane.getChildren().remove(currentSprite);
        File sprites = chooseImage("Choose Sprite Sheet");
        if (sprites !=  null){
            setImageView(spriteSheet,sprites.toURI().toString());
        }
        //Sprite mySprite = myRS.makeSpriteAnimation(spriteSheet.getImage(), 0.0, 0.0, 110.0, 55.0);
        currentSprite = myRS.makeSprite(spriteSheet.getImage(), 6.0, 14.0, 60.0, 60.0);
        currentSprite.fitWidthProperty().bind(mySpritePane.maxWidthProperty());
        currentSprite.fitHeightProperty().bind(mySpritePane.maxHeightProperty());

        mySpritePane.getChildren().add(currentSprite);

        currentSprite.setOnMousePressed(e-> startRectangle(e));
        currentSprite.setOnMouseReleased(e-> finishRectangle(e));
        currentAnimation = null;
        animationFrame = new HashMap<>();
        scrollToAnimation = new HashMap<>();
        initializeScrollPane();
    }

    private void startRectangle(MouseEvent e){
        if (testNull(currentAnimation, "Animation Not Set")){
            return;
        }
        FrameInfo frame = animationFrame.get(currentAnimation);
        if (hitOrHurt.getState().equals(HIT_TEXT)){
            mySpritePane.getChildren().remove(frame.getHitBox());
            frame.setHitBox(new Rectangle());
            frame.getHitBox().setX(e.getX());
            frame.getHitBox().setY(e.getY());
        }
        else{
            mySpritePane.getChildren().remove(frame.getHurtBox());
            frame.setHurtBox(new Rectangle());
            frame.getHurtBox().setX(e.getX());
            frame.getHurtBox().setY(e.getY());
        }
    }

    private void finishRectangle(MouseEvent e){
        if (testNull(currentAnimation, "Animation Not Set")){
            return;
        }
        double x2 = e.getX();
        double y2 = e.getY();

        FrameInfo frame = animationFrame.get(currentAnimation);


        if (hitOrHurt.getState().equals(HIT_TEXT)){
            double x1 = frame.getHitBox().getX();
            double y1 = frame.getHitBox().getY();
            double width = x2 - x1;
            double height = y2 - y1;


            frame.setHitBox(new Rectangle(width, height, Color.TRANSPARENT));
            frame.getHitBox().setStroke(Color.RED);
            frame.getHitBox().setStrokeWidth(5);
            mySpritePane.getChildren().add(frame.getHitBox());
            frame.getHitBox().relocate(x1, y1);
        }
        else{
            double x1 = frame.getHurtBox().getX();
            double y1 = frame.getHurtBox().getY();
            double width = x2 - x1;
            double height = y2 - y1;

            frame.setHurtBox(new Rectangle(width, height, Color.TRANSPARENT));
            frame.getHurtBox().setStroke(Color.BLUE);
            frame.getHurtBox().setStrokeWidth(5);
            mySpritePane.getChildren().add(frame.getHurtBox());
            frame.getHurtBox().relocate(x1, y1);
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
