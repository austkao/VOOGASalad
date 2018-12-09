package editor.home;

import com.google.common.eventbus.EventBus;
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
import messenger.external.DeleteDirectoryEvent;
import messenger.external.EventBusFactory;
import renderer.external.RenderSystem;
import renderer.external.Structures.ScrollablePaneNew;
import renderer.external.Structures.TextBox;
import java.io.File;
import java.nio.file.Paths;
import java.util.function.Consumer;

public abstract class EditorHome extends Scene implements EditorScreen {

    protected ScrollablePaneNew myScroll;
    protected VBox myBox;
    protected RenderSystem rs;
    protected Button switchView;
    protected Button buttonNew;
    protected Button buttonEdit;
    protected Button buttonDelete;

    protected Group myRoot;
    protected EditorManager em;
    protected EditorSuper myEditor;
    protected EditorConstant myEC;
    protected Stage popupStage;
    private Consumer consumer;
    protected EventBus myEB;

    public EditorHome(Group root, EditorManager em) {
        super(root);
        myRoot = root;
        this.em = em;
        rs = new RenderSystem();
        myEB = EventBusFactory.getEventBus();
        initializeVBox();
        Text title = createTitle();
        root.getChildren().addAll(title);
        consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
    }

    protected abstract String getDir();

    protected ScrollablePaneNew initializeScroll(String directory) {
        File dir = Paths.get(em.getGameDirectoryString(), directory).toFile();
        ScrollablePaneNew scrollPane = new ScrollablePaneNew(200,150, 520, 600);
        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                scrollPane.loadFiles(file);
            }
        }
        switchView = getRender().makeStringButton("Switch", Color.BLACK,true, Color.WHITE,20.0,20.0,100.0,100.0,30.0);
        switchView.setOnMouseClicked(event -> scrollPane.switchView());
        myRoot.getChildren().addAll(scrollPane.getScrollPane(), switchView);
        return scrollPane;
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
        myRoot.getChildren().add(myBox);
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

    protected void nameNewObject(String title, String label) {
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

    protected abstract void createNewObject(String name);
    protected void deleteDirectory(File directory) {
        myEB.post(new DeleteDirectoryEvent("Delete Stage", directory));
    }

    public void updateScroll(String directory) {
        myRoot.getChildren().remove(myScroll);
        initializeScroll(directory);
    }
}
