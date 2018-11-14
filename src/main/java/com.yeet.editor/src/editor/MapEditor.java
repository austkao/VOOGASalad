package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import renderer.external.RenderSystem;
import renderer.external.Structures.Level;

/**
 * @author ob29
 */
import java.util.Random;

public class MapEditor extends EditorSuper{


    public MapEditor(Group root,EditorManager em){
        super(root,em);
        Image back = new Image(this.getClass().getClassLoader().getResourceAsStream("hs.png"));
        Image tile = new Image(this.getClass().getClassLoader().getResourceAsStream("activator_rail.png"));
        Level level = new Level(back);
        Pane pane = new Pane();
        pane.setPrefWidth(500);
        pane.setPrefHeight(500);
        pane.setLayoutX(500);
        pane.setLayoutY(0);
        getRenderSystem().drawStage(pane, level);
        root.getChildren().add(pane);
        level.addTile(0, 0, tile);
        Button addTile = getRenderSystem().makeStringButton("add tile", Color.BLACK,true,Color.WHITE,30.0,50.0,100.0,200.0,50.0);
        root.getChildren().add(addTile);
        addTile.setOnMouseClicked(e -> process(level,tile));

    }

    public void process(Level level, Image image){
        Random rand = new Random();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8 ; j++){
                level.addTile(i, j, image);
            }
        }
        //int  x = rand.nextInt(7) + 1;
        //int  y = rand.nextInt(7) + 1;
        //level.addTile(x,y,image);
    }

    public String toString(){
        return "MapEditor";
    }



}
