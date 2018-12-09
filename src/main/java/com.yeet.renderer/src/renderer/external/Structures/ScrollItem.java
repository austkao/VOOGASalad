package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.Node;
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
import renderer.external.Scrollable;


public class ScrollItem implements Scrollable {

    private ToggleButton button;
    private ToggleButton imageButton;
    private Image image;
    private ImageView imageView;
    private RenderSystem rs;


    public ScrollItem(Image image, Text desc){
        rs = new RenderSystem();
        this.image = image;
        this.imageView = new ImageView(image);
        initializeButton(desc);
        initializeImageButton();
    }


    public void initializeButton(Text desc){
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);
        button = new ToggleButton(desc.getText(),imageView);
        button.setTextFill(Color.WHITE);
        button.wrapTextProperty().setValue(true);
        applyStyleAndEffect(button);
    }

    @Override
    public void setNodeGraphic(Node key, String text) {

    }

    private void initializeImageButton(){
        imageButton = new ToggleButton();
        ImageView copy = new ImageView(image);
        copy.setPreserveRatio(true);
        copy.setFitHeight(100);
        imageButton.setGraphic(copy);
        applyStyleAndEffect(imageButton);
    }

    private void applyStyleAndEffect(ToggleButton t){
        rs.styleButton(t);
        rs.buttonHoverEffect(t);
        t.selectedProperty().addListener((p, ov, nv) -> {
            selectEffect(t);
        });
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
