package renderer.external.Structures;


import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
        rs.applyStyleAndEffect(button);
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
        rs.applyStyleAndEffect(imageButton);
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
