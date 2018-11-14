package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import renderer.external.RenderSystem;
import renderer.external.Structures.Level;

/**
 * @author ob29
 */
import java.util.Random;

public class MapEditor extends EditorSuper{
    private static final String DEFAULT_BACKGROUND_IMAGE = "hs.png";


    Pane mapPane;
    ScrollPane blocks;
    FileChooser fileChooser;
    Level level;

    public MapEditor(Group root,EditorManager em){
        super(root,em);
        initializeMap(500, 500);

        Image backgroundDefault = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_BACKGROUND_IMAGE));
        level = new Level(backgroundDefault, (int)mapPane.getPrefWidth(), (int)mapPane.getPrefHeight());
        getRenderSystem().drawStage(mapPane, level);

        Button addTile = getRenderSystem().makeStringButton("add tile", Color.BLACK,true,Color.WHITE,30.0,50.0,100.0,200.0,50.0);
        root.getChildren().add(addTile);
        addTile.setOnMouseClicked(e -> process(level,tile));

    }

    private void initializeMap(int width, int height){
        mapPane = new Pane();
        mapPane.setPrefWidth(width);
        mapPane.setPrefHeight(height);
    }


    public void process(Level level, Image image){
        level.addTile(0,0,image);
    }

    public String toString(){
        return "MapEditor";
    }



}
