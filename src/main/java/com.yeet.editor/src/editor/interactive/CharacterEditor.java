package editor.interactive;

import editor.AnimationInfo;
import editor.EditorManager;
import javafx.animation.Animation;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import renderer.external.Scrollable;
import renderer.external.Structures.*;
import xml.XMLParser;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author rr202
 */

public class CharacterEditor extends EditorSuper {
    private static final String DEFAULT_PORTRAIT = "lucinaglasses.png";
    private static final String HIT_TEXT = "HITBOX";
    private static final String HURT_TEXT = "HURTBOX";

    private static final int BOX_STROKE = 5;
    private static final Color HITBOX_COLOR = Color.RED;
    private static final Color HURTBOX_COLOR = Color.BLUE;
    private static final Color BOX_FILL = null;

    private static final double thumbnailAspectRatio = 132.0/95.0; //
    private static final double portraitAspectRatio = 1.0;

    private ImageView portrait;
    private Rectangle thumbnail;
    private Rectangle portraitRectangle;
    private Image spriteSheet;
    private Sprite currentSprite;

    //Animation variables
    private SpriteAnimation currentAnimation;
    private Pane mySpritePane;
    private ScrollablePaneNew newAnimationList;
    private Map<String, SpriteAnimation> nameToAnimation;
    private Map<SpriteAnimation, AnimationInfo> animationFrame;

    private SliderBox healthSlider;
    private SliderBox attackSlider;
    private SliderBox defenseSlider;
    private Consumer consumer;
    private Text frameText;
    private SwitchButton hitOrHurt;
    private File myDirectory;
    private InputEditor inputEditor;



    private CharacterEditor(EditorManager em, InputEditor editor, Scene prev){
        super(new Group(),em, prev);
        this.inputEditor = editor;
        portrait = initializeImageView(200, 300, 275, 25);

        nameToAnimation = new HashMap<>();
        initializeScrollPane();

        //this.setPortrait(DEFAULT_PORTRAIT);

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
    public CharacterEditor(EditorManager em, InputEditor editor, Scene prev, File characterDirectory, boolean isEdit) {
        this(em, editor, prev);
        myDirectory = characterDirectory;
        isSaved = isEdit;
        if(isEdit) {
            loadFiles(characterDirectory);
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
        setAnimation.setOnMouseClicked(e -> makeNewSpriteAnimation());

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

        root.getChildren().addAll(addPortrait, saveFile, getSpriteSheet, setAnimation, playAnimation,
                setInputCombo, stepForward, stepBackward, frameText, hitOrHurt );
    }
    private void makeSliders(){
        VBox mySliders = new VBox(10);
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
        newAnimationList = new ScrollablePaneNew(30.0, 350.0, 200.0, 400.0);
        root.getChildren().add(newAnimationList.getScrollPane());
    }
    /**
     * User selects background, and it is applied to level.
     */
    private void choosePortrait(){
        File portraitFile = chooseImage("Choose Portrait File");
        if (testNull(portraitFile, "File not valid"))
            return;
        Rectangle thumbRec = new Rectangle();
        getRectOnImageView("Draw Thumbnail, then close window to continue", new ImageView(new Image(portraitURL)), Color.PURPLE, thumbRec, thumbnailAspectRatio);
        Rectangle portRec = new Rectangle();
        getRectOnImageView("Draw Portrait, then close window to continue", new ImageView(new Image(portraitURL)), Color.PEACHPUFF, portRec, portraitAspectRatio);

        setPortrait(portraitFile.toURI().toString(), thumbRec, portRec);
    }
    private void setPortrait(String portraitURL, Rectangle thumbnail, Rectangle portraitRect){
        portrait.setImage(new Image(portraitURL));
        this.thumbnail = thumbnail;
        portraitRectangle = portraitRect;
    }


    private void getRectOnImageView(String message, ImageView imgview, Color color, Rectangle ret, double ratio){
        Stage popUpPicture = new Stage();
        imgview.setFitHeight(600);
        imgview.setPreserveRatio(true);
        popUpPicture.setTitle(message);
        Group newRoot = new Group();
        Scene pictureWindow = new Scene(newRoot);
        popUpPicture.setScene(pictureWindow);


        newRoot.getChildren().add(imgview);
        AtomicReference<Point2D> anchor = new AtomicReference<>(new Point2D(0.0, 0.0));
        imgview.setOnMousePressed(e -> {
            ret.setFill(BOX_FILL);
            ret.setStroke(color);
            ret.setStrokeWidth(BOX_STROKE);
            anchor.set(new Point2D(e.getX(), e.getY()));
            ret.setX(e.getX());
            ret.setY(e.getY());
        });
        imgview.setOnMouseDragged(e -> {
            updateRectangle(e, anchor.get(), ret, ratio);
        });

        newRoot.getChildren().add(ret);

        while (ret.getWidth() == 0 || ret.getHeight() == 0){
            popUpPicture.showAndWait();
        }
    }
    private void selectNewAnimationFromScroll(Scrollable b){
        if (currentAnimation != null){
            currentAnimation.jumpTo(new Duration(0));
            currentAnimation.stop();
            mySpritePane.getChildren().remove(animationFrame.get(currentAnimation).getHitBox());
            mySpritePane.getChildren().remove(animationFrame.get(currentAnimation).getHurtBox());
        }
        currentAnimation = nameToAnimation.get(b.getButton().getText());
        AnimationInfo frame = animationFrame.get(currentAnimation);
        frame.setCurrentFrame(1);
        currentAnimation.jumpTo(new Duration(0));
        currentAnimation.stop();;
        stepForwardAnimation();
    }
    private void chooseSpriteSheet(){
        mySpritePane.getChildren().remove(currentSprite);
        File sprites = chooseImage("Choose Sprite Sheet");

        /*
        TextInputDialog text = new TextInputDialog("");
        resetText("Enter X Offset of initial frame", text);
        double offsetX = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Y Offset", text);
        double offsetY = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Width", text);
        double width = Double.parseDouble(text.showAndWait().orElse("0"));
        resetText("Enter Height", text);
        double height = Double.parseDouble(text.showAndWait().orElse("0"));
        initializeSpriteSheet(sprites, offsetX, offsetY, width, height);
        */

        initializeSpriteSheet(sprites, 6, 14, 60, 60);
    }
    private void initializeSpriteSheet(File sprites, double offsetX, double offsetY, double width, double height) {
        if (testNull(sprites, "File not valid")){
            return;
        }
        spriteSheet = new Image(sprites.toURI().toString());
        currentSprite = myRS.makeSprite(spriteSheet, offsetX, offsetY, width, height);
        currentSprite.fitWidthProperty().bind(mySpritePane.maxWidthProperty());
        currentSprite.fitHeightProperty().bind(mySpritePane.maxHeightProperty());

        mySpritePane.getChildren().add(currentSprite);


        //idea of anchor came form here:
        //https://coderanch.com/t/689100/java/rectangle-dragging-image
        AtomicReference<Point2D> anchor = new AtomicReference<>(new Point2D(0, 0));
        currentSprite.setOnMousePressed(e -> anchor.set(startHitHurtBoxSelection(e, anchor.get())));
        currentSprite.setOnMouseDragged(e -> dragHitHurtBoxSelection(e, anchor.get()));

        currentAnimation = null;
        animationFrame = new HashMap<>();
        nameToAnimation = new HashMap<>();
        initializeScrollPane();
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
            frame.setCurrentFrame(1);
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
                new Duration(currentAnimation.getCycleDuration().toMillis()*adjust / frame.getTotalFrames())));
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


    private Point2D startHitHurtBoxSelection(MouseEvent e, Point2D anchor){
        if (testNull(currentAnimation, "Animation Not Set")){
            return anchor;
        }

        AnimationInfo frame = animationFrame.get(currentAnimation);
        Rectangle newBox = new Rectangle();
        newBox.setX(e.getX());
        newBox.setY(e.getY());
        newBox.setFill(BOX_FILL);
        newBox.setStrokeWidth(BOX_STROKE);
        mySpritePane.getChildren().add(newBox);

        if (hitOrHurt.getState().equals(HIT_TEXT)){
            mySpritePane.getChildren().remove(frame.getHitBox());
            newBox.setStroke(HITBOX_COLOR);
            frame.setHitBox(newBox);
        }
        else{
            mySpritePane.getChildren().remove(frame.getHurtBox());
            newBox.setStroke(HURTBOX_COLOR);
            frame.setHurtBox(newBox);
        }

        anchor = new Point2D(e.getX(), e.getY());
        return anchor;
    }
    private void dragHitHurtBoxSelection(MouseEvent e, Point2D anchor){
        if (testNull(currentAnimation, "Animation Not Set")){
            return;
        }
        AnimationInfo frame = animationFrame.get(currentAnimation);
        Rectangle selection;
        if (hitOrHurt.getState().equals(HIT_TEXT)){
            selection = frame.getHitBox();
        }
        else{
            selection = frame.getHurtBox();
        }
        updateRectangle(e, anchor, selection, -1);
    }
    private void updateRectangle(MouseEvent e, Point2D anchor, Rectangle rect, double ratio){
        rect.setWidth(Math.abs(e.getX() - anchor.getX()));
        rect.setHeight(Math.abs(e.getY() - anchor.getY()));
       if (ratio != -1)
       {
            if (rect.getWidth() > rect.getHeight()*ratio){
                rect.setHeight(rect.getWidth()/ratio);
            }
            else{
                rect.setWidth(rect.getHeight()*ratio);
            }
       }
        rect.setX(Math.min(anchor.getX(), e.getX()));
        rect.setY(Math.min(anchor.getY(), e.getY()));
    }

    private void makeNewSpriteAnimation(){
        if (testNull(spriteSheet, "Sprite Sheet Not Set")){
            return;
        }

        TextInputDialog text = new TextInputDialog("");
        resetText("Enter Animation Name", text);
        String name = text.showAndWait().orElse("unknown");
        resetText("Enter Attack Power", text);
        int power = Integer.parseInt(text.showAndWait().orElse("0"));

        List<String> inputString = getCombo();
        /*
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
        */

        SpriteAnimation myAnimation = new SpriteAnimation(currentSprite, Duration.seconds(2), 11, 11,
                6.0, 640.0, 61.0, 60.0);
        initializeSpriteAnimation(inputString, power, myAnimation, name);
    }
    private void initializeSpriteAnimation(List<String> inputString, double power, SpriteAnimation myAnimation, String name) {
        myAnimation.setCycleCount(Animation.INDEFINITE);

        ScrollItem animText = new ScrollItem(new WritableImage(1, 1), new Text(name));
        newAnimationList.addItem(animText);
        nameToAnimation.put(name, myAnimation);
        animText.getButton().setOnMouseClicked(e -> selectNewAnimationFromScroll(animText));

        animationFrame.put(myAnimation, new AnimationInfo(myAnimation.getCount(), inputString, power, name));
    }


    private String showAlertInputOptions(int num){
        Set<String> options = inputEditor.getMoveSet();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Enter input #" + num);

       for (String option: options){
            ButtonType optionButton = new ButtonType(option, ButtonBar.ButtonData.OK_DONE);
           dialog.getDialogPane().getButtonTypes().add(optionButton);
           dialog.getDialogPane().lookupButton(optionButton).setDisable(false);
       }

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

    @Override
    public String toString(){
        return "Character Editor";
    }

    @Override
    public String getDirectoryString() {
        return myDirectory.getPath();
    }

    private void loadFiles(File directory) {
        File characterProperties = Paths.get(directory.getPath(), "characterproperties.xml").toFile();
        loadCharacterData(characterProperties);
        File spriteProperties = Paths.get(directory.getPath(), "sprites","spriteproperties.xml").toFile();
        loadSpriteData(spriteProperties);
        File attackProperties = Paths.get(directory.getPath(), "attacks","attackproperties.xml").toFile();
        loadAttacksData(attackProperties);
    }
    private void loadCharacterData(File file) {
        try {
            XMLParser parser = loadXMLFile(file);
            HashMap<String, ArrayList<String>> data = parser.parseFileForElement("character");
            ArrayList<String> health = data.get("health");
            ArrayList<String> attack = data.get("attack");
            ArrayList<String> defense = data.get("defense");


            HashMap<String, ArrayList<String>> portraitBoxInfo = parser.parseFileForElement("portrait");
            HashMap<String, ArrayList<String>> thumbnailBoxInfo = parser.parseFileForElement("thumbnail");

            Double thumbX = Double.parseDouble(thumbnailBoxInfo.get("thumbX").get(0));
            Double thumbY = Double.parseDouble(thumbnailBoxInfo.get("thumbY").get(0));
            Double thumbW = Double.parseDouble(thumbnailBoxInfo.get("w").get(0));
            Double thumbH = Double.parseDouble(thumbnailBoxInfo.get("g").get(0));
            Double portX = Double.parseDouble(portraitBoxInfo.get("x").get(0));
            Double portY = Double.parseDouble(portraitBoxInfo.get("y").get(0));
            Double portS = Double.parseDouble(portraitBoxInfo.get("size").get(0));

            healthSlider.setNewValue(Double.parseDouble(health.get(0)));
            attackSlider.setNewValue(Double.parseDouble(attack.get(0)));
            defenseSlider.setNewValue(Double.parseDouble(defense.get(0)));

            File portraitFile = Paths.get(myDirectory.getPath(), myDirectory.getName() + ".png").toFile();
            setPortrait(portraitFile.toURI().toString(), new Rectangle(thumbX, thumbY, thumbW, thumbH),
                    new Rectangle(portX, portY, portS, portS));

        } catch (Exception ex) {
            System.out.println("Cannot load Character Information");
        }
    }
    private void loadAttacksData(File file) {
        try {
            XMLParser parser = loadXMLFile(file);
            HashMap<String, ArrayList<String>> attacks = parser.parseFileForElement("attack");
            HashMap<String, ArrayList<String>> frames = parser.parseFileForElement("frame");

            int numAttacks = attacks.get("attackPower").size();
            int numFrames = frames.get("hitHeight").size();


            List<String> inputs = attacks.get("inputCombo");
            List<String> powers = attacks.get("attackPower");
            List<String> names = attacks.get("name");

            for (int i = 0; i < numAttacks; i++){
                SpriteAnimation newAnim = createSpriteAnimationFromData(attacks, i);
                initializeSpriteAnimation(Arrays.asList(inputs.get(i).split("-")),
                        Double.parseDouble(powers.get(i)), newAnim, names.get(i));
            }
            for (int i = 0; i < numFrames; i++){
                setUpAnimationInfo(frames, i);
            }

        } catch (Exception ex) {
            System.out.println("Cannot load attacks file");
            ex.printStackTrace();
        }
    }
    private void setUpAnimationInfo(HashMap<String, ArrayList<String>> frames, int index) {
        String  fname = frames.get("fname").get(index);
        int    frameNumber    = Integer.parseInt(frames.get("number").get(index));
        double hix = Double.parseDouble(frames.get("hitXPos").get(index));
        double hiy = Double.parseDouble(frames.get("hitYPos").get(index));
        double hux = Double.parseDouble(frames.get("hurtXPos").get(index));
        double huy = Double.parseDouble(frames.get("hurtYPos").get(index));
        double hiw = Double.parseDouble(frames.get("hitWidth").get(index));
        double hih = Double.parseDouble(frames.get("hitHeight").get(index));
        double huw = Double.parseDouble(frames.get("hurtWidth").get(index));
        double huh = Double.parseDouble(frames.get("hurtHeight").get(index));

        SpriteAnimation currentAnimation = nameToAnimation.get(fname);
        AnimationInfo frameInfo = animationFrame.get(currentAnimation);

        frameInfo.setCurrentFrame(frameNumber);
        frameInfo.setHitBox(new Rectangle(hix, hiy, hiw, hih));
        frameInfo.getHitBox().setStrokeWidth(BOX_STROKE);
        frameInfo.getHitBox().setStroke(HITBOX_COLOR);
        frameInfo.getHitBox().setFill(BOX_FILL);
        frameInfo.setHurtBox(new Rectangle(hux, huy, huw, huh));
        frameInfo.getHurtBox().setStrokeWidth(BOX_STROKE);
        frameInfo.getHurtBox().setStroke(HURTBOX_COLOR);
        frameInfo.getHurtBox().setFill(BOX_FILL);
    }
    private SpriteAnimation createSpriteAnimationFromData(HashMap<String, ArrayList<String>> attacks, int index) {
        Duration duration = Duration.seconds(Double.parseDouble(attacks.get("duration").get(index)));
        int count = Integer.parseInt(attacks.get("count").get(index));
        int columns = Integer.parseInt(attacks.get("columns").get(index));
        double offsetX = Double.parseDouble(attacks.get("offsetX").get(index));
        double offsetY = Double.parseDouble(attacks.get("offsetY").get(index));
        double width = Double.parseDouble(attacks.get("width").get(index));
        double height = Double.parseDouble(attacks.get("height").get(index));
        return new SpriteAnimation(currentSprite, duration, count, columns, offsetX, offsetY, width, height);
    }

    private void loadSpriteData(File file) {
        try {
            XMLParser parser = loadXMLFile(file);
            HashMap<String, ArrayList<String>> sprites = parser.parseFileForElement("sprite");

            double h = Double.parseDouble(sprites.get("height").get(0));
            double w = Double.parseDouble(sprites.get("width").get(0));
            double x = Double.parseDouble(sprites.get("offsetX").get(0));
            double y = Double.parseDouble(sprites.get("offsetY").get(0));


            File spriteSheetFile = Paths.get(myDirectory.getPath(), "sprites","spritesheet" + ".png").toFile();
            initializeSpriteSheet(spriteSheetFile, x, y, w, h);

        } catch (Exception ex) {
            System.out.println("Cannot load Sprite file");
        }
    }
    private void createSaveFile() {
        saveCharacterProperties();
        saveAttackProperties();
        saveSpriteProperties();
        isSaved = true;
        root.getChildren().add(saved);
    }
    private void saveCharacterProperties() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        structure.put("character", new ArrayList<>(List.of("health","attack","defense")));
        structure.put("thumbnail", new ArrayList<>(List.of("thumbX", "thumbY", "w", "h")));
        structure.put("portrait", new ArrayList<>(List.of("x", "y", "size")));


        HashMap<String, ArrayList<String>> data = new HashMap<>();


        data.put("health", new ArrayList<>(List.of(Double.toString(healthSlider.getValue()))));
        data.put("attack", new ArrayList<>(List.of(Double.toString(attackSlider.getValue()))));
        data.put("defense", new ArrayList<>(List.of(Double.toString(defenseSlider.getValue()))));

        data.put("x", new ArrayList<>(List.of(Double.toString(portraitRectangle.getX()))));
        data.put("y", new ArrayList<>(List.of(Double.toString(portraitRectangle.getY()))));
        data.put("size", new ArrayList<>(List.of(Double.toString(portraitRectangle.getWidth()))));

        data.put("thumbX", new ArrayList<>(List.of(Double.toString(thumbnail.getX()))));
        data.put("thumbY", new ArrayList<>(List.of(Double.toString(thumbnail.getY()))));
        data.put("w", new ArrayList<>(List.of(Double.toString(thumbnail.getWidth()))));
        data.put("h", new ArrayList<>(List.of(Double.toString(thumbnail.getHeight()))));

        try {
            File xmlFile = Paths.get(myDirectory.getPath(), "characterproperties.xml").toFile();
            generateSave(structure, data, xmlFile);
        } catch (Exception ex) {
            System.out.println("Invalid character save");
        }

        try {
            File file = Paths.get(myDirectory.getPath(), myDirectory.getName() + ".png").toFile();
            ImageIO.write(SwingFXUtils.fromFXImage(portrait.getImage(), null), "png", file);
        } catch (Exception ex){
            System.out.println("Could not save Portrait");
        }

        try {
            File file = Paths.get(myDirectory.getPath(), "sprites", "spritesheet" + ".png").toFile();
            ImageIO.write(SwingFXUtils.fromFXImage(spriteSheet, null), "png", file);
        } catch (Exception ex){
            System.out.println("Could not save Sprite Sheet");
        }
    }
    private void saveAttackProperties() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> attackAttributes = new ArrayList<>(List.of("name","duration","count","columns","offsetX","offsetY","width","height", "attackPower", "inputCombo"));
        structure.put("attack", attackAttributes);
        ArrayList<String> frameAttributes = new ArrayList<>(List.of("fname","number","hitXPos","hitYPos","hitWidth","hitHeight","hurtXPos","hurtYPos","hurtWidth","hurtHeight"));
        structure.put("frame", frameAttributes);
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        for(String s : attackAttributes) {
            data.putIfAbsent(s, new ArrayList<>());
        }
        for(String s: frameAttributes) {
            data.putIfAbsent(s, new ArrayList<>());
        }
        for(SpriteAnimation ani : animationFrame.keySet()) {
            AnimationInfo aniInfo = animationFrame.get(ani);
            data.get("name").add(aniInfo.getName());
            data.get("duration").add(ani.getCycleDuration().toSeconds() +"");
            data.get("count").add(aniInfo.getTotalFrames()+"");
            data.get("columns").add(ani.getColumns() + "");
            data.get("offsetX").add(ani.getOffsetX()+"");
            data.get("offsetY").add(ani.getOffsetY()+"");
            data.get("width").add(ani.getWidth()+"");
            data.get("height").add(ani.getHeight()+"");
            data.get("attackPower").add(aniInfo.getAttackPower()+"");
            data.get("inputCombo").add(aniInfo.getInputAsString());
            for(int i = 1; i <= aniInfo.getTotalFrames(); i++){
                aniInfo.setCurrentFrame(i);
                Rectangle hitBox = aniInfo.getHitBox();
                Rectangle hurtBox = aniInfo.getHurtBox();
                data.get("fname").add(aniInfo.getName());
                data.get("number").add(Integer.toString(i));
                data.get("hitXPos").add(hitBox.getX()+"");
                data.get("hitYPos").add(hitBox.getY()+"");
                data.get("hurtXPos").add(hurtBox.getX()+"");
                data.get("hurtYPos").add(hurtBox.getY()+"");
                data.get("hitWidth").add(hitBox.getWidth()+"");
                data.get("hitHeight").add(hitBox.getHeight()+"");
                data.get("hurtWidth").add(hurtBox.getWidth()+"");
                data.get("hurtHeight").add(hurtBox.getHeight()+"");
            }
        }
        try {
            File xmlFile = Paths.get(myDirectory.getPath(), "attacks","attackproperties.xml").toFile();
            generateSave(structure, data, xmlFile);
        } catch (Exception ex) {
            System.out.println("Invalid attacks save");
        }
    }
    private void saveSpriteProperties() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> spriteAttributes = new ArrayList<>(List.of("offsetX", "offsetY", "width", "height"));
        structure.put("sprite", spriteAttributes);
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        ArrayList<String> offsetX = new ArrayList<>(List.of(Double.toString(currentSprite.getDefaultViewport().getMinX())));
        ArrayList<String> offsetY = new ArrayList<>(List.of(Double.toString(currentSprite.getDefaultViewport().getMinY())));
        ArrayList<String> width = new ArrayList<>(List.of(Double.toString(currentSprite.getDefaultViewport().getWidth())));
        ArrayList<String> height = new ArrayList<>(List.of(Double.toString(currentSprite.getDefaultViewport().getHeight())));

        data.put("offsetX", offsetX);
        data.put("offsetY", offsetY);
        data.put("width", width);
        data.put("height", height);
        try {
            File xmlFile = Paths.get(myDirectory.getPath(), "sprites", "spriteproperties.xml").toFile();
            generateSave(structure, data, xmlFile);
        } catch (Exception ex) {
            System.out.println("Invalid sprite save");
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
    /**
    Alert system found at:
    https://stackoverflow.com/questions/8309981/how-to-create-and-show-common-dialog-error-warning-confirmation-in-javafx-2
    **/
    private boolean testNull(Object object, String message){
        if (object == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message,
                    ButtonType.OK);
            alert.showAndWait();
            return true;
        }
        return false;
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
    private void resetText (String message, TextInputDialog text){
        text.setContentText(message);
        text.getEditor().setText("");
    }
}
