package renderer.external.Structures;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScrollablePaneNew extends Pane {

    private ObservableList<ScrollItem> items;
    private ScrollPane scrollPane;
    private HashMap<String, String> currentImages;
    private VBox normalView;
    private VBox gridView;
    private boolean gridFlag = true;



    public ScrollablePaneNew(File dir,double x, double y){
        scrollPane = new ScrollPane();
        normalView = new VBox(8);
        gridView = new VBox(5);
        items = FXCollections.observableArrayList();
        scrollPane.setPrefSize(500, 500);
        scrollPane.setFitToWidth(true);
        scrollPane.setLayoutX(x);
        scrollPane.setLayoutY(y);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        //scrollPane.setScaleY(.8);
        //currentImages = new HashMap<>();
        loadFiles(dir);
        buildGridView();
        switchView();
    }

    private void buildGridView(){
        List<HBox> hboxes = new ArrayList<>();
        HBox currentH = new HBox(3);
        for(int i = 0; i < items.size(); i++){
            if(i%3 == 0 && i != 0){
                hboxes.add(currentH);
                currentH = new HBox(3);
            }
            currentH.getChildren().add(items.get(i).getImageButton());
        }
        gridView.getChildren().addAll(hboxes);
    }



    public void addItem(Image image){
        ScrollItem si= new ScrollItem(image,new Text("Hi my name is Hi my name is Hi my name is SLim SHady"));
        items.add(si);
        normalView.getChildren().add(items.get(items.size()-1).getButton());

        //items.get(items.size()-1).setPos(0,125*items.size());
    }

    public void switchView(){
        if(gridFlag){
            scrollPane.setContent(normalView);
            gridFlag = false;
        } else {
            scrollPane.setContent(gridView);
            gridFlag = true;
        }
    }


    public void removeItem(){

    }

    public void loadFiles(File dir){
        for(File imgFile : dir.listFiles()) {
            if(imgFile.toString().endsWith(".png")){
                Image itemImage = new Image(imgFile.toURI().toString());
                addItem(itemImage);
                //currentImages.put(itemImage.toString(), imgFile.getName());
            }
        }
    }

    public ObservableList<ScrollItem> getItems() {
        return items;
    }

    public ScrollPane getScrollPane(){
        return scrollPane;
    }

    public HashMap getCurrentImages() {
        return currentImages;
    }
}
