package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import renderer.external.RenderSystem;
import renderer.external.Structures.Level;

/**
 * @author ob29
 */

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
        pane.setLayoutY(500);
        getRenderSystem().drawStage(pane, level);
        root.getChildren().add(pane);
        level.addTile(0, 0, tile);
    }
    public String toString(){
        return "MapEditor";
    }



}
