package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
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
        getRenderSystem().drawStage(root, level);
        level.addTile(0, 0, tile);
    }
    public String toString(){
        return "MapEditor";
    }



}
