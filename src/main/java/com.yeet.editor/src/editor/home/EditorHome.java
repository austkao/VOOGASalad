package editor.home;

import editor.EditorConstant;
import editor.EditorManager;
import editor.EditorScreen;
import editor.interactive.EditorSuper;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import renderer.external.Structures.ScrollablePaneNew;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public abstract class EditorHome extends Scene implements EditorScreen {
    private static final String RESOURCE_PATH = "/src/main/java/com.yeet.main/resources/";

    protected ScrollablePaneNew myScroll;
    protected VBox myBox;
    protected RenderSystem rs;
    protected Button switchView;
    protected Button buttonNew;
    protected Button buttonEdit;
    protected Button buttonDelete;

    private Group root;
    protected EditorManager em;
    protected EditorSuper myEditor;
    protected EditorConstant myEC;
    protected Stage popupStage;
    private Consumer consumer;

    public EditorHome(Group root, EditorManager em) {
        super(root);
        this.root = root;
        this.em = em;
        rs = new RenderSystem();
        initializeVBox();
        initializeScroll();
        Text title = createTitle();
        root.getChildren().addAll(title,myScroll.getScrollPane(),switchView);
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
    }

    protected abstract String getDir();

    private void initializeScroll() {
        Path userPath = Paths.get(System.getProperty("user.dir"));
        File dir = new File(userPath+RESOURCE_PATH+getDir());
        myScroll = new ScrollablePaneNew(dir,200,150, 520, 600);
        switchView = getRender().makeStringButton("Switch", Color.BLACK,true, Color.WHITE,20.0,20.0,100.0,100.0,30.0);
        switchView.setOnMouseClicked(event -> myScroll.switchView());
    }
    public RenderSystem getRender(){
        return rs;
    }

    public abstract void setEditor();

    private void initializeVBox() {
        myBox = new VBox(20);
        buttonNew = rs.makeStringButton("New " + toString().split(" ")[0], Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        buttonEdit = rs.makeStringButton("Edit " + toString().split(" ")[0], Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        buttonDelete = rs.makeStringButton("Delete " + toString().split(" ")[0], Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        Button buttonBack = createBack(new Scene(new Group()));
        buttonNew.setOnMouseClicked(e -> em.changeScene(myEditor));
        myBox.getChildren().addAll(buttonNew, buttonEdit, buttonDelete, buttonBack);
        myBox.setLayoutX(800);
        myBox.setLayoutY(200);
        root.getChildren().add(myBox);
    }

    public VBox getMyBox(){
        return myBox;
    }


    public Text createTitle() {
        return rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
    }

    public Button createBack(Scene scene) {
        Button buttonBack = rs.makeStringButton("Back", Color.BLACK, true, Color.WHITE, 30.0, 0.0, 0.0, 300.0, 50.0);
        buttonBack.setOnMouseClicked(e -> em.setEditorHomeScene());
        return buttonBack;
    }

    public void nameNewObject(String title, String label) {
        popupStage = new Stage();
        popupStage.setTitle(title);
        TextBox stageName = rs.makeTextField(consumer, "", 100.0,20.0,200.0,30.0, rs.getPlainFont());
        Text stageLabel = rs.makeText(label, false, 12, Color.BLACK, 20.0, 50.0);
        Button create = rs.makeStringButton("Create", Color.BLACK, false, Color.GRAY, 12.0,50.0, 100.0, 100.0, 30.0);
        Button cancel = rs.makeStringButton("Cancel", Color.BLACK, false, Color.GRAY, 12.0,200.0, 100.0, 100.0, 30.0);
        create.setOnMouseClicked(e -> createNewObject(stageName.getText()));
        cancel.setOnMouseClicked(e -> popupStage.close());
        Scene creationScene = new Scene(new Group(stageName, stageLabel, create, cancel), 400, 200);
        popupStage.setScene(creationScene);
        popupStage.show();
    }

    public abstract void createNewObject(String name);
}
