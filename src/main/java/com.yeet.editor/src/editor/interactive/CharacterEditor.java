package editor.interactive;

import editor.EditorManager;
import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import renderer.external.Structures.*;
import xml.XMLParser;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author ob29
 * @author rr202
 */

public class CharacterEditor extends EditorSuper {
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
    private Map<SpriteAnimation, AnimationInfo> animationFrame;

    private VBox mySliders;
    private SliderBox healthSlider;
    private SliderBox attackSlider;
    private SliderBox defenseSlider;
    private Consumer consumer;
    private Text frameText;
    private SwitchButton hitOrHurt;
    private File myDirectory;
    private InputEditor inputEditor;

    static class AnimationInfo {
        int currentFrame;
        int totalFrames;
        int attackPower;
        Map<Integer, Rectangle> hitBoxes;
        Map<Integer, Rectangle> hurtBoxes;
        List<String> input;

        AnimationInfo(int total, List<String> inputString, int power) {
            input = inputString;
            currentFrame = -1;
            totalFrames = total;
            hitBoxes = new HashMap<>();
            hurtBoxes = new HashMap<>();
            for (int i = 1; i <= totalFrames; i++){
                hitBoxes.putIfAbsent(i, new Rectangle());
                hurtBoxes.putIfAbsent(i, new Rectangle());
            }
            attackPower = power;
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
            String ret = "Frame "+currentFrame+"/"+totalFrames + "\ninput: ";
            for (int i = 0 ; i < input.size(); i++){
                if (i < input.size() - 1){
                    ret += input.get(i)+"-";
                }
                else{
                    ret += input.get(i);
                }
            }
            return ret;
        }
        void setInput(List<String> in){ input = in;}
        List<String> getInput(){return input;}
    }

    public CharacterEditor(EditorManager em, InputEditor editor){
        super(new Group(),em);
        this.inputEditor = editor;
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

    public CharacterEditor(EditorManager em, InputEditor editor, File characterDirectory, boolean isEdit) {
        this(em, editor);
        myDirectory = characterDirectory;
        isSaved = isEdit;
        if(isEdit) {
            File characterProperties = Paths.get(characterDirectory.getPath(), "characterproperties.xml").toFile();
            loadCharacterData(characterProperties);
        }
    }

    private void makeButtons(){
        Button addPortrait = myRS.makeStringButton("set portrait", Color.BLACK,true,Color.WHITE,
                30.0,25.0,250.0,200.0,50.0);
        addPortrait.setOnMouseClicked(e -> choosePortrait());
        Button saveFile = myRS.makeStringButton("Save File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 150.0, 200.0, 50.0);
        saveFile.setOnMouseClicked(e -> createSaveFile());

        Button getSpriteSheet = myRS.makeStringButton("Import Sprite Sheet", Color.FORESTGREEN, true,
                Color.WHITE, 20.0, 600.0, 25.0, 200.0, 50.0);
        getSpriteSheet.setOnMouseClicked(e -> chooseSpriteSheet());

        Button setAnimation = myRS.makeStringButton("Set Sprite Animation", Color.ORCHID, true,
                Color.WHITE, 20.0, 500.0, 100.0, 200.0, 50.0);
        setAnimation.setOnMouseClicked(e -> makeSpriteAnimation());

        Button setInputCombo = myRS.makeStringButton("Set Input Combo", Color.CORNFLOWERBLUE, true,
                Color.WHITE, 20.0, 725.0, 100.0, 200.0, 50.0);
        setInputCombo.setOnMouseClicked(e -> setInputCombo());

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
        frameText.setWrappingWidth(350.0);

        List<String> options = new ArrayList<>();
        options.add(HURT_TEXT);
        options.add(HIT_TEXT);
        Font switchFont = new Font(15);
        hitOrHurt = new SwitchButton(options, 250.0, 400.0, 350.0, 20.0, 5.0, Color.BLACK,
                Color.FLORALWHITE, switchFont);
        //hitOrHurt = myRS.makeSwitchButtons(options, true, Color.BLACK,
        //        Color.FLORALWHITE, 5.0,400.0, 400.0, 50.0, 15.0 );

        root.getChildren().addAll(addPortrait, saveFile, getSpriteSheet, setAnimation, playAnimation,
                setInputCombo, stepForward, stepBackward, frameText, hitOrHurt );
    }
    private void makeSliders(){
        mySliders = new VBox(10);
        healthSlider = myRS.makeSlider("health",50.0,consumer,0.0,0.0,400.0);
        attackSlider = myRS.makeSlider("attack",50.0,consumer,0.0,0.0,400.0);
        defenseSlider = myRS.makeSlider("defense",50.0,consumer,0.0,0.0,400.0);

        mySliders.getChildren().addAll(healthSlider,attackSlider,defenseSlider);
        mySliders.setLayoutX(950.0);
        mySliders.setLayoutY(200.0);
        root.getChildren().add(mySliders);
    }

    private void initializeSpritePane(){
        mySpritePane = new Pane();
        mySpritePane.setLayoutX(600);
        mySpritePane.setLayoutY(400);
        mySpritePane.setMinSize(300, 300);
        mySpritePane.setMaxSize(300, 300);
        root.getChildren().add(mySpritePane);
    }
    private void initializeScrollPane(){
        animationList = new ScrollablePane(50.0,300.0);
        root.getChildren().add(animationList.getScrollPane());
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

    private void playAnimation(){
        if (testNull(currentAnimation, "Current Animation not set")){
            return;
        }
        AnimationInfo frame = animationFrame.get(currentAnimation);
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
    private void stepAnimation(int adjust){
        AnimationInfo frame = animationFrame.get(currentAnimation);
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

    private void chooseSpriteSheet(){
        mySpritePane.getChildren().remove(currentSprite);
        File sprites = chooseImage("Choose Sprite Sheet");
        if (sprites !=  null){
            setImageView(spriteSheet,sprites.toURI().toString());
        }
        //Sprite mySprite = myRS.makeSpriteAnimation(spriteSheet.getImage(), 0.0, 0.0, 110.0, 55.0);

        TextInputDialog text = new TextInputDialog("");
        resetText("Enter X Offset of initial frame", text);
        double offsetX = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Y Offset", text);
        double offsetY = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Width", text);
        double width = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Height", text);
        double height = Double.parseDouble(text.showAndWait().orElse("0"));

        //currentSprite = myRS.makeSprite(spriteSheet.getImage(), 6.0, 14.0, 60.0, 60.0);
        currentSprite = myRS.makeSprite(spriteSheet.getImage(), offsetX, offsetY, width, height);
        currentSprite.fitWidthProperty().bind(mySpritePane.maxWidthProperty());
        currentSprite.fitHeightProperty().bind(mySpritePane.maxHeightProperty());

        mySpritePane.getChildren().add(currentSprite);


        //idea of anchor came form here:
        //https://coderanch.com/t/689100/java/rectangle-dragging-image
        AtomicReference<Point2D> anchor = new AtomicReference<>(new Point2D(0, 0));
        currentSprite.setOnMousePressed(e -> anchor.set(startSelection(e, anchor.get())));
        currentSprite.setOnMouseDragged(e -> dragSelection(e, anchor.get()));
        //currentSprite.setOnMouseReleased(e -> finishSelection(e, anchor.get()));

        currentAnimation = null;
        animationFrame = new HashMap<>();
        scrollToAnimation = new HashMap<>();
        initializeScrollPane();
    }

    private Point2D startSelection(MouseEvent e, Point2D anchor){
        if (testNull(currentAnimation, "Animation Not Set")){
            return anchor;
        }

        AnimationInfo frame = animationFrame.get(currentAnimation);
        Rectangle newBox = new Rectangle();
        newBox.setX(e.getX());
        newBox.setY(e.getY());
        newBox.setFill(null);
        newBox.setStrokeWidth(5);
        mySpritePane.getChildren().add(newBox);

        if (hitOrHurt.getState().equals(HIT_TEXT)){
            mySpritePane.getChildren().remove(frame.getHitBox());
            newBox.setStroke(Color.RED);
            frame.setHitBox(newBox);
        }
        else{
            mySpritePane.getChildren().remove(frame.getHurtBox());
            newBox.setStroke(Color.BLUE);
            frame.setHurtBox(newBox);
        }

        anchor = new Point2D(e.getX(), e.getY());
        return anchor;
    }

    private void dragSelection(MouseEvent e, Point2D anchor){
        if (testNull(currentAnimation, "Animation Not Set")){
            return;
        }
        AnimationInfo frame = animationFrame.get(currentAnimation);
        Rectangle selection = new Rectangle();
        if (hitOrHurt.getState().equals(HIT_TEXT)){
            selection = frame.getHitBox();
        }
        else{
            selection = frame.getHurtBox();
        }
        selection.setWidth(Math.abs(e.getX() - anchor.getX()));
        selection.setHeight(Math.abs(e.getY() - anchor.getY()));
        selection.setX(Math.min(anchor.getX(), e.getX()));
        selection.setY(Math.min(anchor.getY(), e.getY()));
    }

    private void finishSelection(MouseEvent e, Point2D anchor){
        if (testNull(currentAnimation, "Animation Not Set")){
            return;
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

    private void makeSpriteAnimation(){
        if (testNull(spriteSheet.getImage(), "Sprite Sheet Not Set")){
            return;
        }
        File image = chooseImage("Choose thumbnail for animation");
        if (testNull(image, "No valid image provided")){
            return;
        }
        ScrollableItem animPic = animationList.addItem(new Image(image.toURI().toString()));

        List<String> inputString = getCombo();

        SpriteAnimation myAnimation = getNewAnimation();

        TextInputDialog text = new TextInputDialog("");
        text.setContentText("Enter Attack Power");
        int power = Integer.parseInt(text.showAndWait().orElse("0"));

        scrollToAnimation.put(animPic, myAnimation);
        animPic.getButton().setOnMouseClicked(e -> selectAnimationFromScroll(animPic));
        animationFrame.put(myAnimation, new AnimationInfo(myAnimation.getCount(), inputString, power));
    }

    private void resetText (String mesasge, TextInputDialog text){
        text.setContentText(mesasge);
        text.getEditor().setText("");
    }

    private SpriteAnimation getNewAnimation(){
        TextInputDialog text = new TextInputDialog();
        resetText("Enter Time In seconds", text);
        double time = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Frame Count", text);
        int count = Integer.parseInt(text.showAndWait().orElse("0"));
        resetText("Enter Number of Columns", text);
        int columns = Integer.parseInt(text.showAndWait().orElse("0"));
        resetText("Enter X Offset", text);
        double offsetX = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Y Offset", text);
        double offsetY = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Width", text);
        double width = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Height", text);
        double height = Double.parseDouble(text.showAndWait().orElse("0"));
        SpriteAnimation myAnimation = new SpriteAnimation(currentSprite, Duration.seconds(time), count, columns,
                offsetX, offsetY, width, height);
        myAnimation.setCycleCount(Animation.INDEFINITE);
        return myAnimation;
    }
    private void selectAnimationFromScroll(ScrollableItem b){
        if (currentAnimation != null){
            currentAnimation.stop();
            mySpritePane.getChildren().remove(animationFrame.get(currentAnimation).getHitBox());
            mySpritePane.getChildren().remove(animationFrame.get(currentAnimation).getHurtBox());
        }

        currentAnimation = scrollToAnimation.get(b);
        AnimationInfo frame = animationFrame.get(currentAnimation);
        frame.currentFrame = 1;
        stepForwardAnimation();
        stepBackAnimation();
    }

    private String showAlertInputOptions(int num){

        //Set<String> options = inputEditor.getInputTypes();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Enter input #" + num);

        boolean disabled = false;

//        for (String option: options){
//            ButtonType optionButton = new ButtonType(option, ButtonBar.ButtonData.OK_DONE);
//            dialog.getDialogPane().getButtonTypes().add(optionButton);
//            dialog.getDialogPane().lookupButton(optionButton).setDisable(disabled);
//        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        ButtonType input = dialog.showAndWait().orElse(ButtonType.CLOSE);

        if (input == ButtonType.CLOSE){
            return "";
        }
        return input.getText();

    }
    private void setInputCombo(){
        if (testNull(currentAnimation, "Animation not selected")){
            return;
        }
        animationFrame.get(currentAnimation).setInput(getCombo());
        frameText.setText(animationFrame.get(currentAnimation).toString());
    }
    private List<String> getCombo(){
        List<String> combo = new ArrayList<>();
        int inputNum = 1;
        String nextInput = showAlertInputOptions(inputNum);
        while (!"".equals(nextInput)){
            combo.add(nextInput);
            inputNum++;
            nextInput = showAlertInputOptions(inputNum);
        }
        return combo;
    }



    public String toString(){
        return "Character Editor";
    }

    private void loadCharacterData(File file) {
        try {
            XMLParser parser = loadXMLFile(file);
            HashMap<String, ArrayList<String>> data = parser.parseFileForElement("character");
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
            File xmlFile = Paths.get(myEM.getGameDirectoryString(), "characters", "characterproperties.xml").toFile();
            generateSave(structure, data, xmlFile);
            isSaved = true;
            root.getChildren().add(saved);
        } catch (Exception ex) {
            System.out.println("Invalid save");
        }
    }

    /**
     * general method for choosing an image
     * @returns file chosen
     */
    private File chooseImage(String message){
        FileChooser fileChooser = myRS.makeFileChooser("image");
        fileChooser.setTitle(message);
        if(isSaved) {
            isSaved = false;
            root.getChildren().remove(saved);
        }
        return fileChooser.showOpenDialog(getWindow());
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
    private void setImageView(ImageView img, String portraitURL){
        img.setImage(new Image(portraitURL));
    }
}
