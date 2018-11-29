package player.internal.Elements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class StageGrid extends TilePane {

    public static final int gridWidth = 835;
    public static final int gridHeight = 720;
    public static final double RATIO = 3.0/2.0;
    public static final double SPACING = 5.0;

    public StageGrid(File directory, BiConsumer<String, ImageView> biConsumer, Consumer<String> consumer){
        super();
        this.setHgap(SPACING);
        this.setVgap(SPACING);
        this.setStyle("-fx-background-color: black");
        int stageCount = 0;
        ArrayList<File> files  = new ArrayList<>();
        for(File f : new File(directory.getPath()+"//stages").listFiles()){
            if(!f.getName().contains(".")){
                stageCount++;
                files.add(f);
            }
        }
        int columnCount = 1;
        while((gridWidth/(float)columnCount)*(1/RATIO)*(stageCount/(float)columnCount)>gridHeight){
            columnCount++;
        }
        int rowCount = (int)Math.ceil(stageCount/(float)columnCount);
        double w = gridWidth/(float)columnCount;
        double h = gridHeight/(float)rowCount;
        for(int i = 0; i < stageCount; i++) {
            ImageView imageView = new ImageView(new Image(String.format("%s/%s",files.get(i).toURI(),"thumb.png")));
            imageView.setFitWidth(w-SPACING);
            imageView.setFitHeight(h-SPACING);
            this.getChildren().add(imageView);
            int finalI = i;
            imageView.setOnMouseEntered(event -> biConsumer.accept(files.get(finalI).getName(), imageView));
            imageView.setOnMousePressed(event -> consumer.accept(files.get(finalI).getName()));
        }
        this.setMinSize(gridWidth,gridHeight);
        this.setPrefSize(gridWidth,gridHeight);
        this.setMaxSize(gridWidth,gridHeight);
        this.setAlignment(Pos.CENTER_LEFT);
    }

}
