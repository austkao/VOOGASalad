package renderer.external.Structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.File;

public class ScrollablePane extends Pane {

    private ObservableList<ScrollableItem> items;
    private ScrollPane scrollPane;


    public ScrollablePane(File dir){
        items = FXCollections.observableArrayList();
        scrollPane = new ScrollPane();
        loadFiles(dir);
        scrollPane.setPrefSize(150, 400);
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
        items.get(items.size()-1).setPos(0,125*items.size());
    }

    public void removeItem(){

    }

    public void loadFiles(File dir){
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
