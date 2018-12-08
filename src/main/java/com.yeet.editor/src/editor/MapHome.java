package editor;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import renderer.external.Structures.ScrollablePaneNew;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapHome extends EditorHome{
    private static final String RESOURCE_PATH = "/src/main/java/com.yeet.main/resources/";
    public MapHome(Group root, EditorManager em){
        super(root,em);
        setEditor();
        Path userPath = Paths.get(System.getProperty("user.dir"));
        File dir = new File(userPath+RESOURCE_PATH+"allmaps");
        ScrollablePaneNew scrollpane = new ScrollablePaneNew(dir,200,150);
        Button switchView = getRender().makeStringButton("Switch", Color.BLACK,true, Color.WHITE,20.0,20.0,20.0,100.0,30.0);
        switchView.setOnMouseClicked(event -> scrollpane.switchView());
        root.getChildren().addAll(scrollpane.getScrollPane(),switchView);

    }
    public String toString(){
        return "Map Home";
    }

    public void setEditor(){
        myEditor = new MapEditor(new Group(),em);
        myEditor.createBack(this);
    }

}
