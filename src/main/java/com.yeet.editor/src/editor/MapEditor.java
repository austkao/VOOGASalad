package editor;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import renderer.external.Structures.Level;
import renderer.external.Structures.ScrollableItem;
import renderer.external.Structures.ScrollablePane;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ob29
 * @author rr202
 * @author ak457
 */

public class MapEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "fd.jpg";
    private static final String DEFAULT_IMAGE_DIR = "/src/main/java/com.yeet.main/resources/examplegame/data/tiles";
    private static final String DEFAULT_BACKGROUND_DIR = "/src/main/java/com.yeet.main/resources/examplegame/data/background";

    private Image currentTileFile;
    private ScrollablePane myScrollablePane;
    private Level level;
    private Group root;
    private MapSettingEditor myMapSettings;
    private EditorManager myEM;

    /**
     * Constructs the Map Editor object given the root and the editor manager
     * @param root
     * @param em
     */
    public MapEditor(Group root,EditorManager em){
        super(root,em);
        this.root = root;
        myEM = em;
        try {
            initializeLevel(800, 500, 250, 100,
                    this.getClass().getClassLoader().getResource(DEFAULT_BACKGROUND_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        myMapSettings = new MapSettingEditor(new Group(), em, this);
        initializeScrollPane();
        currentTileFile = myScrollablePane.getItems().get(0).getImage();
        //getRenderSystem().drawStage(mapPane, level);
        level.setOnMouseClicked(e -> clickProcessTile(e));
        Button addBG = getRenderSystem().makeStringButton("Set Background", Color.BLACK,true,Color.WHITE,
                30.0,1000.0,700.0,200.0,50.0);
        addBG.setOnMouseClicked(e -> chooseBackground());

        Button resetGrid = getRenderSystem().makeStringButton("Reset Grid", Color.LAVENDER, true, Color.WHITE,
                30.0,25.0, 300.0, 200.0, 50.0);
        resetGrid.setOnMouseClicked(e -> level.resetGrid());

        Button chooseTile = getRenderSystem().makeStringButton("Choose Tile", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 375.0, 200.0, 50.0);
        chooseTile.setOnMouseClicked(e -> chooseTileImage());

        Button saveFile = getRenderSystem().makeStringButton("Save File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 150.0, 200.0, 50.0);
        saveFile.setOnMouseClicked(e -> createSaveFile());

        Button loadFile = getRenderSystem().makeStringButton("Load File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 75.0, 200.0, 50.0);
        loadFile.setOnMouseClicked(e -> loadMapFile());
        Button editSettingsButton = getRenderSystem().makeStringButton("Edit Map Settings", Color.CRIMSON, true, Color.WHITE,
                30.0,1000.0, 600.0, 200.0, 50.0);
        editSettingsButton.setOnMouseClicked(e -> goToSettings());
        root.getChildren().addAll(addBG, resetGrid, chooseTile, saveFile, loadFile, editSettingsButton);
    }

    private void initializeScrollPane(){
        Path filePath = Paths.get(System.getProperty("user.dir"));
        File paneFile = new File(filePath+DEFAULT_IMAGE_DIR);
        myScrollablePane = new ScrollablePane(paneFile,50.0,400);
        for(ScrollableItem b: myScrollablePane.getItems()){
            b.getButton().setOnMouseClicked(e -> selectTileFromScroll(b.getImage()));
        }
        root.getChildren().add(myScrollablePane.getScrollPane());
    }

    /**
     * initializes the level object given dimensions and the root
     * @param width
     * @param height
     */
    private void initializeLevel(int width, int height, int x, int y, String background){
        level = new Level(width, height, background);
        level.setLayoutX(x);
        level.setLayoutY(y);
        root.getChildren().add(level);
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

    /**
     * User selects background, and it is applied to level.
     */
    private void chooseBackground(){
        //ListView<String> backgroundList = myRe
                File backgroundFile = chooseImage("Choose Background File");
        if (backgroundFile != null)
            level.setBackground(backgroundFile.toURI().toString());
    }

    private void chooseTileImage(){
        File tileFile = chooseImage("Choose Tile Image");
        try{
            Image image = new Image(tileFile.toURI().toString());
            if (image != null)
                currentTileFile = image;
            myScrollablePane.addItem(currentTileFile);
            int size = myScrollablePane.getItems().size();
            myScrollablePane.getItems().get(size-1).getButton().setOnMouseClicked(e->selectTileFromScroll(image));
        }
        catch (Exception e){
            System.out.println("Invalid image");
        }
    }

    /**
     * Given mouse event, approximates position in relation to grid and
     * either adds or removes tile currently at that position.
     * @param e
     */
    private void clickProcessTile(MouseEvent e){
        int xindex = (int)e.getX()/level.getTileWidth();
        int yindex = (int)e.getY()/level.getTileHeight();
        level.processTile(xindex, yindex, currentTileFile);
    }

    private void selectTileFromScroll(Image image){
        currentTileFile = image;
    }


    public String toString(){
        return "MapEditor";
    }

    private void snapShot(Pane pane) {
        WritableImage image = pane.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File("chart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }


    private void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> mapAttributes = new ArrayList<>();
        mapAttributes.add("x");
        mapAttributes.add("y");
        mapAttributes.add("image");
        structure.put("map", mapAttributes);
        HashMap<String, String> imageMap = myScrollablePane.getCurrentImages();
        HashMap<String, ArrayList<String>> levelMap = level.createLevelMap();
        ArrayList<String> temp = new ArrayList<>();
        for(String s : levelMap.get("image")) {
            System.out.println(s);
            temp.add(imageMap.get(s));
        }
        levelMap.get("image").clear();
        levelMap.get("image").addAll(temp);
        try {
            generateSave(structure, levelMap);
        } catch (Exception ex) {
            System.out.println("Invalid save");
        }
    }

    private void loadMapFile() {
        try {
            HashMap<String, ArrayList<String>> data = loadXMLFile("map");
            ArrayList<String> xPos = data.get("x");
            ArrayList<String> yPos = data.get("y");
            if(xPos.size() != yPos.size()) {
                throw new IOException("Incorrect information contained within xml");
            }
            for(int i = 0; i < xPos.size(); i++) {
                level.processTile(Integer.parseInt(xPos.get(i)), Integer.parseInt(yPos.get(i)), currentTileFile);
            }
        } catch (Exception ex) {
            System.out.println("Cannot load file");
        }
    }

    private void goToSettings() {
        myEM.changeScene(myMapSettings);
    }
}
