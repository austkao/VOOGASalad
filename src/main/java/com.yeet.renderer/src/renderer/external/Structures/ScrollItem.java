package renderer.external.Structures;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;

import static renderer.external.RenderSystem.BUTTON_FORMAT;
import static renderer.external.RenderSystem.BUTTON_SCALE;
import static renderer.external.RenderUtils.toRGBCode;


public class ScrollItem {

    private static final double TILE_WIDTH = 128;


    private Button button;
    private Button imageButton;
    private Image image;
    private ImageView imageView;
    private RenderSystem rs;


    public ScrollItem(Image image, Text desc){
        rs = new RenderSystem();
        this.image = image;
        this.imageView = new ImageView(image);
        initializeButton(image,desc);
        initializeImageButton();


        //button.setBackground(Background.EMPTY); //toggle background
    }
    private void initializeButton(Image image, Text desc){
        button = new Button(desc.getText(),imageView);
        button.setTextFill(Color.WHITE);
        button.wrapTextProperty().setValue(true);
        button.setStyle(String.format(BUTTON_FORMAT,
                toRGBCode(Color.BLACK),
                rs.getPlainFont().getName(),20,20));
        button.setOnMouseEntered(event->button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(Color.BLACK),rs.getPlainFont().getName(),20,20, 1.1,1.1)));
        button.setOnMouseExited(event -> button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(Color.BLACK),rs.getPlainFont().getName(),20,20,1.0,1.0)));
    }
    private void initializeImageButton(){
        imageButton = new Button();
        ImageView copy = new ImageView(image);
        imageButton.setGraphic(copy);
        imageButton.setStyle(String.format(BUTTON_FORMAT,
                toRGBCode(Color.BLACK),
                rs.getPlainFont().getName(),20,20));
        imageButton.setOnMouseEntered(event->imageButton.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(Color.BLACK),rs.getPlainFont().getName(),20,20, 1.1,1.1)));
        imageButton.setOnMouseExited(event -> imageButton.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(Color.BLACK),rs.getPlainFont().getName(),20,20,1.0,1.0)));
    }

    public Button getButton() {
        return button;
    }

    public Button getImageButton(){return imageButton;}

    public void setPos(double x, double y){
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    public Image getImage(){
        return image;
    }
}
