package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;



public class ScrollItem {

    private ToggleButton button;
    private ToggleButton imageButton;
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
        button = new ToggleButton(desc.getText(),imageView);
        button.setTextFill(Color.WHITE);
        button.wrapTextProperty().setValue(true);
        rs.styleButton(button);
        rs.buttonHoverEffect(button);
        button.selectedProperty().addListener((p, ov, nv) -> {
            selectEffect(button);
        });

        //button.setOnMouseClicked(e -> selectEffect(button));
    }

    private void selectEffect(ToggleButton b){
        if(b.isSelected()){
            DropShadow drop = new DropShadow(12.0,Color.BLUE);
            drop.setHeight(30.0);
            b.setEffect(drop);
        }else{
            b.setEffect(null);
        }
    }
    private void initializeImageButton(){
        imageButton = new ToggleButton();
        ImageView copy = new ImageView(image);
        copy.setPreserveRatio(true);
        //imageView.setFitWidth(100);
        copy.setFitHeight(100);
        imageButton.setGraphic(copy);
        rs.styleButton(imageButton);
        rs.buttonHoverEffect(imageButton);
        imageButton.selectedProperty().addListener((p, ov, nv) -> {
            selectEffect(imageButton);
        });
    }

    public ToggleButton getButton() {
        return button;
    }

    public ToggleButton getImageButton(){return imageButton;}

    public void setPos(double x, double y){
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    public Image getImage(){
        return image;
    }
}
