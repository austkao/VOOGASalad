package renderer.external.Structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.HashMap;

public class ScrollablePane extends Pane {

    private ObservableList<ScrollableItem> items;
    private ScrollPane scrollPane;
    private HashMap<String, String> currentImages;


    public ScrollablePane(File dir){
        currentImages = new HashMap<>();
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
        ScrollableItem si= new ScrollableItem(image,0,0);
        items.add(si);
        this.getChildren().add(items.get(items.size()-1).getButton());
        items.get(items.size()-1).setPos(0,125*items.size());
    }

    public void removeItem(){

    }

    public void loadFiles(File dir){
        for(File imgFile : dir.listFiles()) {
            if(imgFile.toString().endsWith(".png")){
                Image itemImage = new Image(imgFile.toURI().toString());
                addItem(itemImage);
                currentImages.put(itemImage.toString(), imgFile.getName());
                System.out.println(itemImage.toString());
            }
        }
    }


    public ObservableList<ScrollableItem> getItems() {
        return items;
    }

    public ScrollPane getScrollPane(){
        return scrollPane;
    }

    public HashMap getCurrentImages() {
        return currentImages;
    }
}
