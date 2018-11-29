package player.internal.Elements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static renderer.external.RenderUtils.centerCrop;

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
            StackPane imageHolder = new StackPane();
            imageHolder.setMinSize(w-SPACING,h-SPACING);
            imageHolder.setPrefSize(w-SPACING,h-SPACING);
            imageHolder.setMaxSize(w-SPACING,h-SPACING);
            imageHolder.setAlignment(Pos.CENTER);
            ImageView imageView = new ImageView(new Image(String.format("%s/%s",files.get(i).toURI(),"thumb.png")));
            if(imageView.getImage().isError()){
                imageView.setImage(new Image(String.format("%s/%s",files.get(i).toURI(),"thumb.jpg")));
            }
            centerCrop(imageView);
            imageView.setFitWidth(w-SPACING);
            imageView.setFitHeight((w-SPACING));
            imageHolder.getChildren().addAll(imageView);
            this.getChildren().add(imageHolder);
            int index = i;
            imageView.setOnMouseEntered(event -> {
                biConsumer.accept(files.get(index).getName(), imageView);
                imageView.setScaleX(1.1);
                imageView.setScaleY(1.1);
            });
            imageView.setOnMouseExited(event -> {
                imageView.setScaleX(1.0);
                imageView.setScaleY(1.0);
            });
            imageView.setOnMousePressed(event -> consumer.accept(files.get(index).getName()));
        }
        this.setMinSize(gridWidth,gridHeight);
        this.setPrefSize(gridWidth,gridHeight);
        this.setMaxSize(gridWidth,gridHeight);
        this.setAlignment(Pos.CENTER);
    }

}
