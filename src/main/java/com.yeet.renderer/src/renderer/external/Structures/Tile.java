package renderer.external.Structures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView{

    private int width;
    private int height;

    public Tile (Image image, int w, int h){
        super(image);
        setFitWidth(w);
        setFitHeight(h);
        width = w;
        height = h;
    }

    //unused
    public void setLocation(int x, int y){
        setX(x*width);
        setY(y*height);
    }
}
