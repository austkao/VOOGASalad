package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import renderer.external.Structures.Level;

public class MapEditor{

    private Level myLevel;
    private Image image;
    private Scene myScene;

    // this class shouldn't implement Renderer, it should use the Editor's instance of RenderSystem
    public MapEditor(){
        myLevel = new Level(image);
        Group root = new Group();
        myScene = new Scene(root);
    }

}
