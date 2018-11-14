package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import renderer.external.RenderSystem;
import renderer.external.Structures.Level;
import renderer.external.Structures.Tile;

/**
 * @author ob29
 */
import java.io.File;
import java.util.Random;

public class MapEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "hs.png";
    private static final String DEFAULT_TILE = "acacia_log.png";



    Pane mapPane;
    ScrollPane blocks;
    Level level;
    private Group root;

    public MapEditor(Group root,EditorManager em){
        super(root,em);
        this.root = root;
        initializeMap(500, 500, root);
        initializeScrollPane();

        Image backgroundDefault = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_BACKGROUND_IMAGE));
        level = new Level(backgroundDefault, (int)mapPane.getPrefWidth(), (int)mapPane.getPrefHeight());
        getRenderSystem().drawStage(mapPane, level);

        mapPane.setOnMouseClicked(e -> clickAddTile(e));

        //Button addTile = getRenderSystem().makeStringButton("add tile", Color.BLACK,true,Color.WHITE,30.0,50.0,100.0,200.0,50.0);
        //root.getChildren().add(addTile);
        //addTile.setOnMouseClicked(e -> process(level, );

        FileChooser setBG = getRenderSystem().makeFileChooser("image");
        Button addBG = getRenderSystem().makeStringButton("set Background", Color.BLACK,true,Color.WHITE,30.0,50.0,200.0,200.0,50.0);

        root.getChildren().add(addBG);
        addBG.setOnMouseClicked(e -> chooseBackground());


    }

    private void initializeScrollPane(){

    }
    private void initializeMap(int width, int height, Group root){
        mapPane = new Pane();
        mapPane.setPrefWidth(width);
        mapPane.setPrefHeight(height);
        root.getChildren().add(mapPane);
    }

    private void chooseBackground(){
        FileChooser fileChooser = getRenderSystem().makeFileChooser("image");
        fileChooser.setTitle("Open Background Image");
        File backgroundFile = fileChooser.showOpenDialog(getWindow());
        if (backgroundFile != null)
            level.setBackground(new Image(backgroundFile.toURI().toString()));
    }

    private void clickAddTile(MouseEvent e){
        Image tileDefault = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_TILE));
        //Tile tile = new Tile(tileDefault, 50, 50);
        level.addTile((int)e.getX()/50, (int)e.getY()/50, tileDefault);
    }


    public void process(Level level, Image image){
        level.addTile(0,0,image);
    }



    public String toString(){
        return "MapEditor";
    }



}
