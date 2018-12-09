package renderer.external.Structures;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import renderer.external.Scrollable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScrollablePaneNew extends Pane {

    private ObservableList<Scrollable> items;
    private ScrollPane scrollPane;
    private VBox normalView;
    private VBox gridView;
    private boolean gridFlag = true;
    private ToggleGroup tgNormal;
    private ToggleGroup tgGrid;



    public ScrollablePaneNew(File dir,double x, double y){

        this(x,y);
        loadFiles(dir);

    }

    public ScrollablePaneNew(double x, double y){
        tgNormal = new ToggleGroup();
        tgGrid = new ToggleGroup();
        scrollPane = new ScrollPane();
        normalView = new VBox(8);
        gridView = new VBox(5);
        items = FXCollections.observableArrayList();
        initializeScrollPane(x,y);
        buildNormalView();
        buildGridView();
        switchView();
    }

    private void initializeScrollPane(double x, double y){
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.setPrefSize(520, 600);
        scrollPane.setFitToWidth(true);
        scrollPane.setLayoutX(x);
        scrollPane.setLayoutY(y);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void buildNormalView(){
        normalView.setMaxWidth(460.0);
        normalView.setAlignment(Pos.CENTER);
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
            tgGrid.getToggles().add(items.get(i).getImageButton());
        }
        hboxes.add(currentH);
        gridView.getChildren().addAll(hboxes);
    }





    public void addItem(Image image){
        ScrollItem si= new ScrollItem(image,new Text("Hi my name is Hi my name is Hi my name is SLim SHady"));
        tgNormal.getToggles().add(si.getButton());
        items.add(si);
        normalView.getChildren().add(items.get(items.size()-1).getButton());
    }

    public void addItem(Scrollable item){
        items.add(item);
        tgNormal.getToggles().add((ToggleButton)item.getButton());
        normalView.getChildren().add(items.get(items.size()-1).getButton());
    }


    public void switchView(){
        if(gridFlag){
            StackPane holder = new StackPane(normalView); //must wrap VBox in some other layout pane to center it within scrollpane
            holder.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                    scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
            scrollPane.setContent(holder);
            gridFlag = false;
        } else {
            scrollPane.setContent(gridView);
            gridFlag = true;
        }
    }


    public void removeItem(){
        final List<Scrollable> removalCandidates = new ArrayList<>();
        for(Scrollable s:items){
            if (s.getButton().isSelected()) {
                removalCandidates.add(s);
            }
        }
        items.removeAll(removalCandidates);
        normalView.getChildren().remove(removalCandidates.get(0).getButton());
    }

    public void modifyItem(Text t){ //TODO allow for changing titles
        final List<Scrollable> modifyCandidates = new ArrayList<>();
        for(Scrollable s:items){
            if (s.getButton().isSelected()) {
                modifyCandidates.add(s);
            }
        }

        modifyCandidates.get(0).setNodeGraphic(t,"");
    }



    public void loadFiles(File dir){
        for(File imgFile : dir.listFiles()) {
            if(imgFile.toString().endsWith(".png")){
                Image itemImage = new Image(imgFile.toURI().toString());
                addItem(itemImage);
            }
        }
    }

    public ObservableList<Scrollable> getItems() {
        return items;
    }

    public ScrollPane getScrollPane(){
        return scrollPane;
    }

}
