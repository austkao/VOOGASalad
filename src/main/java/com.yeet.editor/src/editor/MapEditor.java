package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import renderer.external.Structures.Level;
import renderer.external.Structures.ScrollableItem;
import renderer.external.Structures.ScrollablePane;

/**
 * @author ob29
 * @author rr202
 */
import java.io.File;
import java.net.URISyntaxException;

public class MapEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "fd.jpg";
    private static final String DEFAULT_TILE = "acacia_log.png";

    private Image currentTileFile;

    private ScrollablePane scrollablePane;
    private Level level;
    private Group root;

    /**
     * Constructs the Map Editor object given the root and the editor manager
     * @param root
     * @param em
     */
    public MapEditor(Group root,EditorManager em){
        super(root,em);
        this.root = root;
        try {
            initializeLevel(800, 500,
                    this.getClass().getClassLoader().getResource(DEFAULT_BACKGROUND_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        initializeScrollPane();

        currentTileFile = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_TILE));


        //getRenderSystem().drawStage(mapPane, level);

        level.setOnMouseClicked(e -> clickProcessTile(e));

        Button addBG = getRenderSystem().makeStringButton("set Background", Color.BLACK,true,Color.WHITE,
                30.0,25.0,200.0,200.0,50.0);
        root.getChildren().add(addBG);
        addBG.setOnMouseClicked(e -> chooseBackground());

        Button resetGrid = getRenderSystem().makeStringButton("Reset Grid", Color.LAVENDER, true, Color.WHITE,
                30.0,25.0, 275.0, 200.0, 50.0);
        root.getChildren().add(resetGrid);
        resetGrid.setOnMouseClicked(e -> level.resetGrid());

        Button chooseTile = getRenderSystem().makeStringButton("Choose Tile", Color.CRIMSON, true, Color.WHITE,
                30.0,25.0, 350.0, 200.0, 50.0);
        root.getChildren().add(chooseTile);
        chooseTile.setOnMouseClicked(e -> chooseTileImage());

        scrollablePane = new ScrollablePane();
        for(ScrollableItem b: scrollablePane.getItems()){
            b.getButton().setOnMouseClicked(e -> selectTileFromScroll(b.getImage()));
        }
        root.getChildren().add(scrollablePane.getScrollPane());
    }

    private void initializeScrollPane(){

    }

    /**
     * initializes the level object given dimensions and the root
     * @param width
     * @param height
     */
    private void initializeLevel(int width, int height, String background){
        level = new Level(width, height, background);
        level.setLayoutX(250);
        level.setLayoutY(100);
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
            scrollablePane.addItem(currentTileFile);
            int size = scrollablePane.getItems().size();
            scrollablePane.getItems().get(size-1).getButton().setOnMouseClicked(e->selectTileFromScroll(image));
        }
        catch (Exception e){
            currentTileFile = currentTileFile;
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



}
