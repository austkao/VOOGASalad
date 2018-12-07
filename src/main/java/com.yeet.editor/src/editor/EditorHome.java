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

public abstract class EditorHome extends Scene {

    private ScrollablePane myScroll;
    private VBox myBox;
    private RenderSystem rs;

    private Group root;
    protected EditorManager em;
    protected EditorSuper myEditor;

    public EditorHome(Group root, EditorManager em) {
        super(root);
        this.root = root;
        this.em = em;
        rs = new RenderSystem();
        initializeVBox();
        Text t = rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
        root.getChildren().add(t);

    }

    private void initializeScroll() {

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










}
