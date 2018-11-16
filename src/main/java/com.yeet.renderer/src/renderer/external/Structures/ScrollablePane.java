package renderer.external.Structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.File;

public class ScrollablePane extends Pane {

    private static final String DEFAULT_IMAGE_DIR = "/Users/orgil/cs308/voogasalad_yeet/src/main/java/com.yeet.main/resources/examplegame/stages/example_stage_1/tiles";
    private ObservableList<ScrollableItem> items;
    private ScrollPane scrollPane;


    public ScrollablePane(){
        items = FXCollections.observableArrayList();
        scrollPane = new ScrollPane();
        loadFiles();
        scrollPane.setPrefSize(140, 400);
        scrollPane.setLayoutX(0);
        scrollPane.setLayoutY(0);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(this);
        scrollPane.setLayoutX(50);
        scrollPane.setLayoutY(400);
        scrollPane.setScaleY(.8);
    }

    public void addItem(Image image){
        items.add(new ScrollableItem(image,0,0));
        this.getChildren().add(items.get(items.size()-1).getButton());
        items.get(items.size()-1).setPos(0,120*items.size());
        System.out.println(items.get(items.size()-1).getButton().getLayoutX());
    }

    public void removeItem(){

    }

    public void loadFiles(){
        File dir = new File(DEFAULT_IMAGE_DIR);
        System.out.println(dir.getAbsolutePath());
        for(File imgFile : dir.listFiles()) {
            if(imgFile.toString().endsWith(".png")){
                addItem(new Image(imgFile.toURI().toString()));
            }
        }
    }


    public ObservableList<ScrollableItem> getItems() {
        return items;
    }

    public ScrollPane getScrollPane(){
        return scrollPane;
    }
}
