package renderer.external.Structures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView{

    private int xIndex;
    private int yIndex;

    public Tile (Image image, int w, int h, int x, int y){
        super(image);
        setFitWidth(w);
        setFitHeight(h);
        xIndex = x;
        yIndex = y;
    }

    //unused
    public void setLocation(int x, int y){
        //setX(x*width);
        //setY(y*height);
        xIndex = x;
        yIndex = y;
    }
}
