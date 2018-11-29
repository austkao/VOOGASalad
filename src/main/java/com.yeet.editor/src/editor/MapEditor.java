package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import renderer.external.Structures.Level;
import renderer.external.Structures.ScrollableItem;
import renderer.external.Structures.ScrollablePane;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author ob29
 * @author rr202
 * @author ak457
 */

public class MapEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "fd.jpg";
    private static final String DEFAULT_IMAGE_DIR = "/src/main/java/com.yeet.main/resources/examplegame/data/tiles";
    private static final String DEFAULT_BACKGROUND_DIR = "/src/main/java/com.yeet.main/resources/examplegame/data/background";
    private static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    private static final int DEFAULT_PLAIN_FONTSIZE = 12;
    private static final String DEFAULT_BGM = "BGM.mp3";

    private Consumer consumer;
    private Image currentTileFile;
    private ScrollablePane myScrollablePane;
    private Level level;
    private Group root;
    private String myBackgroundImage;
    private String myBGMFileName;
    private TextBox myBGM;

    /**
     * Constructs the Map Editor object given the root and the editor manager
     * @param root
     * @param em
     */
    public MapEditor(Group root,EditorManager em){
        super(root,em);
        this.root = root;
        myBackgroundImage = DEFAULT_BACKGROUND_IMAGE;
        myBGMFileName = DEFAULT_BGM;
        try {
            initializeLevel(800, 500, 250, 100,
                    this.getClass().getClassLoader().getResource(DEFAULT_BACKGROUND_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        initializeScrollPane();
        currentTileFile = myScrollablePane.getItems().get(0).getImage();
        //getRenderSystem().drawStage(mapPane, level);
        level.setOnMouseClicked(e -> clickProcessTile(e));
        Button addBG = myRS.makeStringButton("Set Background", Color.BLACK,true,Color.WHITE,
                30.0,1000.0,700.0,200.0,50.0);
        addBG.setOnMouseClicked(e -> chooseBackground());

        Button resetGrid = myRS.makeStringButton("Reset Grid", Color.LAVENDER, true, Color.WHITE,
                30.0,25.0, 300.0, 200.0, 50.0);
        resetGrid.setOnMouseClicked(e -> level.resetGrid());

        Button chooseTile = myRS.makeStringButton("Choose Tile", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 375.0, 200.0, 50.0);
        chooseTile.setOnMouseClicked(e -> chooseTileImage());

        Button saveFile = myRS.makeStringButton("Save File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 150.0, 200.0, 50.0);
        saveFile.setOnMouseClicked(e -> createSaveFile());

        Button loadFile = myRS.makeStringButton("Load File", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 75.0, 200.0, 50.0);
        loadFile.setOnMouseClicked(e -> loadMapFile());
        Font myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        myBGM = myRS.makeTextField(consumer, myBGMFileName, 100.0, 100.0, 800.0, 50.0, myPlainFont);
        Button myBGMButton = myRS.makeStringButton("Set Background Music", Color.BLACK,true,Color.WHITE,
                30.0,950.0,100.0,200.0,50.0);
        myBGMButton.setOnMouseClicked(e -> chooseBGM());
        root.getChildren().addAll(addBG, resetGrid, chooseTile, saveFile, loadFile, myBGM, myBGMButton);
    }

    private void initializeScrollPane(){
        Path filePath = Paths.get(System.getProperty("user.dir"));
        File paneFile = new File(filePath+DEFAULT_IMAGE_DIR);
        myScrollablePane = new ScrollablePane(paneFile);
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
        FileChooser fileChooser = myRS.makeFileChooser("image");
        fileChooser.setTitle(message);
        return fileChooser.showOpenDialog(getWindow());
    }

    /**
     * User selects background, and it is applied to level.
     */
    private void chooseBackground(){
        File backgroundFile = new File(myEM.getGameDirectoryString()+"/data/background");
        ListView<String> backgroundList = myRS.makeDirectoryFileList(backgroundFile, false);
        Stage edit = new Stage();
        edit.setScene(new Scene(new Group(backgroundList)));
        backgroundList.setOnMouseClicked(e -> {
            level.setBackground(backgroundFile.toURI()+"/"+backgroundList.getSelectionModel().getSelectedItem());
            edit.close();
        });
        edit.show();
    }

    private void chooseBGM(){
        File musicFile = new File(myEM.getGameDirectoryString()+"/data/bgm");
        ListView<String> musicList = myRS.makeDirectoryFileList(musicFile, false);
        Stage edit = new Stage();
        edit.setScene(new Scene(new Group(musicList)));
        musicList.setOnMouseClicked(e -> {
            myBGMFileName = musicList.getSelectionModel().getSelectedItem();
            myBGM.setText(myBGMFileName);
            edit.close();
        });
        edit.show();
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


    private void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> mapAttributes = new ArrayList<>(List.of("x","y","image"));
        structure.put("map", mapAttributes);
        structure.put("background", new ArrayList<>(List.of("bgFile")));
        structure.put("music", new ArrayList<>(List.of("mFile")));
        structure.put("position", new ArrayList<>(List.of("id","xPos","yPos")));
        HashMap<String, String> imageMap = myScrollablePane.getCurrentImages();
        HashMap<String, ArrayList<String>> levelMap = level.createLevelMap();
        ArrayList<String> temp = new ArrayList<>();
        for(String s : levelMap.get("image")) {
            System.out.println(s);
            temp.add(imageMap.get(s));
        }
        levelMap.get("image").clear();
        levelMap.get("image").addAll(temp);
        levelMap.put("bgFile", new ArrayList<>(List.of(myBackgroundImage)));
        levelMap.put("mFile", new ArrayList<>(List.of(myBGMFileName)));
        levelMap.put("id", new ArrayList<>(List.of("0","1","2","3")));
        levelMap.put("xPos", new ArrayList<>(List.of("0","1","2","3")));
        levelMap.put("yPos", new ArrayList<>(List.of("0","0","0","0")));
        try {
            generateSave(structure, levelMap);
        } catch (Exception ex) {
            System.out.println("Invalid save");
            ex.printStackTrace();
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
}
