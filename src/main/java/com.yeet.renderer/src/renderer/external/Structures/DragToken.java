package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.function.BiConsumer;

/** Draggable token used for selecting characters on the {@code CharacterSelectScreen}
 *  @author bpx
 */
public class DragToken extends StackPane {

    private double xorigin;
    private double yorigin;
    private double xtranslate;
    private double ytranslate;

    private Color myColor;
    private Circle myCircle;

    /** Creates a new {@code DragToken} using the specified parameters
     *  @param label The {@code Text} to use for the label
     *  @param color The {@code Color} of the token
     *  @param x The initial x position of the token
     *  @param y The initial y position of the token
     *  @param radius The size of the token
     *  @param pointConsumer Accepts the coordinates of the center of the token upon being released
     */
    public DragToken(Text label, Color color, double x, double y, double radius, BiConsumer<Double, Double> pointConsumer){
        super();
        myColor = color;
        this.setPrefSize(radius, radius);
        this.setAlignment(Pos.CENTER);
        myCircle = new Circle(radius,color);
        this.getChildren().addAll(myCircle,label);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setOnMousePressed(event -> {
            xorigin = event.getSceneX();
            yorigin = event.getSceneY();
            xtranslate = super.getTranslateX();
            ytranslate = super.getTranslateY();
        });
        this.setOnMouseDragged(event -> {
            double xoffset = event.getSceneX()-xorigin;
            double yoffset = event.getSceneY()-yorigin;
            double newTranslateX = xtranslate + xoffset;
            double newTranslateY = ytranslate + yoffset;
            this.setTranslateX(newTranslateX);
            this.setTranslateY(newTranslateY);
        });
        this.setOnMouseReleased(event -> {
            pointConsumer.accept((this.getLayoutX()+this.getTranslateX()+(this.getWidth()/2)), (this.getLayoutY()+this.getTranslateY()-(this.getHeight()/2)));
        });

    }

    /** Sets the {@code Color} of the token
     *  @param color The new {@code Color} of the token
     */
    public void setColor(Color color){
        myCircle.setFill(color);
    }

    /** Changes the token back to the original color*/
    public void resetColor(){
        myCircle.setFill(myColor);
    }


}
