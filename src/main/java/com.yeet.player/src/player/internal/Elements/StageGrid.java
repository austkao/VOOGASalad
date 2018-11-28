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

    public StageGrid(File directory, BiConsumer<String, ImageView> biConsumer, Consumer<String> consumer){
        int stageCount = 0;
        ArrayList<File> files  = new ArrayList<>();
        for(File f : new File(directory.getPath()+"//stages").listFiles()){
            if(!f.getName().contains(".")){
                stageCount++;
                files.add(f);
            }
        }

        double d = Math.sqrt((gridWidth*gridHeight)/stageCount)-(100/stageCount);
        for(int i = 0; i < stageCount; i++) {
            ImageView imageView = new ImageView(new Image(String.format("%s/%s",files.get(i).toURI())));
            this.getChildren().add(imageView);
            imageView.setFitWidth(d);
            imageView.setFitHeight(d);
        }

        this.setMaxWidth(gridWidth);
        this.setMaxHeight(gridHeight);
        this.setAlignment(Pos.TOP_CENTER);
    }

}
