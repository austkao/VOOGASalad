package editor;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import renderer.external.Structures.Level;

public class MapEditor extends EditorSuper{

    private Level myLevel;
    private Image image;
    private Scene myScene;
    private Group root;
    private EditorManager em;

    // this class shouldn't implement Renderer, it should use the Editor's instance of RenderSystem
    public MapEditor(EditorManager em){
        super(em);

    }
    public String toString(){
        return "MapEditor";
    }



}
