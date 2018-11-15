package renderer.external.Structures;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import renderer.external.RenderSystem;

public class ScrollableItem {
    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;

    private Button button;
    private RenderSystem rs;
    private Font myEmphasisFont;
    private Font myPlainFont;
    private Image image;


    public ScrollableItem(Image image, double x, double y){
        this.image = image;
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT),DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT),DEFAULT_PLAIN_FONTSIZE);
        rs = new RenderSystem(myPlainFont,myEmphasisFont);
        button = rs.makeImageButton(image,x,y,20.0,25.0);
    }

    public Button getButton() {
        return button;
    }

    public void setPos(double x, double y){
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    public Image getImage(){
        return image;
    }
}
