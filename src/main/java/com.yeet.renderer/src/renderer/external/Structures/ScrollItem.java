package renderer.external.Structures;

import javafx.geometry.Pos;
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
        imageView.setPreserveRatio(true);
        //imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        button = new Button(desc.getText(),imageView);
        button.setTextFill(Color.WHITE);
        button.wrapTextProperty().setValue(true);
        rs.styleButton(button);
        rs.buttonHoverEffect(button);
    }
    private void initializeImageButton(){
        imageButton = new Button();
        ImageView copy = new ImageView(image);
        copy.setPreserveRatio(true);
        //imageView.setFitWidth(100);
        copy.setFitHeight(100);
        imageButton.setGraphic(copy);
        rs.styleButton(imageButton);
        rs.buttonHoverEffect(imageButton);
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
