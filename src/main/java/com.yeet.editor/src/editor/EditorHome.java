package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import renderer.external.Structures.ScrollablePane;
import renderer.external.Structures.ScrollablePaneNew;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class EditorHome extends Scene {
    private static final String RESOURCE_PATH = "/src/main/java/com.yeet.main/resources/";

    private ScrollablePaneNew myScroll;
    private VBox myBox;
    private RenderSystem rs;
    private Button switchView;

    private Group root;
    protected EditorManager em;
    protected EditorSuper myEditor;


    public EditorHome(Group root, EditorManager em) {
        super(root);
        this.root = root;
        this.em = em;
        rs = new RenderSystem();
        initializeVBox();
        initializeScroll();
        Text title = rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        root.getChildren().addAll(title,myScroll.getScrollPane(),switchView);

    }

    protected abstract String getDir();

    private void initializeScroll() {
        Path userPath = Paths.get(System.getProperty("user.dir"));
        File dir = new File(userPath+RESOURCE_PATH+getDir());
        myScroll = new ScrollablePaneNew(dir,200,150);
        switchView = getRender().makeStringButton("Switch", Color.BLACK,true, Color.WHITE,20.0,20.0,100.0,100.0,30.0);
        switchView.setOnMouseClicked(event -> myScroll.switchView());
    }
    public RenderSystem getRender(){
        return rs;
    }

    public abstract void setEditor();

    private void initializeVBox() {
        myBox = new VBox(20);
        Button buttonNew = rs.makeStringButton("New " + toString().split(" ")[0], Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        Button buttonEdit = rs.makeStringButton("Edit " + toString().split(" ")[0], Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        Button buttonDelete = rs.makeStringButton("Delete " + toString().split(" ")[0], Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        Button buttonBack = rs.makeStringButton("Back", Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        buttonNew.setOnMouseClicked(e -> em.changeScene(myEditor));
        buttonBack.setOnMouseClicked(e -> em.setEditorHomeScene());
        buttonEdit.setOnMouseClicked(e -> em.setEditorHomeScene()); //TODO link with scrollpane
        myBox.getChildren().addAll(buttonNew, buttonEdit, buttonDelete, buttonBack);
        myBox.setLayoutX(800);
        myBox.setLayoutY(200);
        root.getChildren().add(myBox);
    }

    public VBox getMyBox(){
        return myBox;
    }










}
