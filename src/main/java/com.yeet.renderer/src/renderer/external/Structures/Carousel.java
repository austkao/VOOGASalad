package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static renderer.external.RenderUtils.toRGBCode;

/**
 * Creates a carousel of {@code String} elements that can be progressed or regressed through arrow controls
 *  @author bpx
 */
public class Carousel extends HBox {

    public static final int INACTIVE = 30;
    public static final String FORMAT_FONT = "-fx-font-family: '%s'; -fx-font-size: %s; -fx-text-fill: %s;";

    private int index;

    /** Create a new Carousel with the specified parameters
     *  @param content The possible options for the {@code Carousel}
     *  @param x X position of the {@code Carousel}
     *  @param y Y Position of the {@code Carousel}
     *  @param w The width of the {@code Carousel} {@code String} display
     *  @param h The height of the {@code Carousel}
     *  @param spacing Amount of spacing between main carousel elements
     *  @param mainColor The main background fill for the {@code Carousel}
     *  @param secondaryColor The text fill color
     *  @param font The font for the text display */
    public Carousel(List<String> content, Double x, Double y, Double w, Double h, Double spacing, Color mainColor, Color secondaryColor, Font font){
        super(spacing);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefWidth(w);
        this.setPrefHeight(h);
        this.setAlignment(Pos.CENTER);
        Polygon leftArrow = new Polygon();
        leftArrow.getPoints().setAll(0.0,h/3,
                                    2*h/3,0.0,
                                    2*h/3,2*h/3);
        leftArrow.setFill(Color.GRAY);
        leftArrow.setOpacity(INACTIVE);
        leftArrow.setStroke(Color.BLACK);
        this.getChildren().add(leftArrow);
        HBox textbox = new HBox();
        textbox.setStyle("-fx-background-color: "+toRGBCode(mainColor)+";" +
                "-fx-border-color: black");
        index = 0;
        Text label = new Text(content.get(index));
        //FORMAT_FONT: String fontName, Double fontSize, Color textColor
        label.setStyle(String.format(FORMAT_FONT,font.getName(),font.getSize(),toRGBCode(secondaryColor)));
        label.setFill(secondaryColor);
        textbox.setMinWidth(w);
        textbox.setFillHeight(true);
        textbox.getChildren().add(label);
        textbox.setAlignment(Pos.CENTER);
        this.getChildren().add(textbox);
        Polygon rightArrow = new Polygon();
        rightArrow.getPoints().setAll(0.0,2*h/3,
                                    0.0,0.0,
                                    2*h/3,h/3);
        if(content.size()>1){
            rightArrow.setFill(mainColor);
        }
        else{
            rightArrow.setFill(Color.GRAY);
            rightArrow.setOpacity(INACTIVE);
        }
        rightArrow.setStroke(Color.BLACK);
        this.getChildren().add(rightArrow);
        leftArrow.setOnMousePressed(event -> {
            if(index>0){
                index--;
                label.setText(content.get(index));
            }
            if(index==0){
                leftArrow.setFill(Color.GRAY);
                leftArrow.setOpacity(INACTIVE);
            }
            if(index<content.size()-1){
                rightArrow.setFill(mainColor);
                rightArrow.setOpacity(100);
            }
        });
        rightArrow.setOnMousePressed(event -> {
            if(index<content.size()-1){
                index++;
                label.setText(content.get(index));
            }
            if(index==content.size()-1){
                rightArrow.setFill(Color.GRAY);
                rightArrow.setOpacity(INACTIVE);
            }
            if(index>0){
                leftArrow.setFill(mainColor);
                leftArrow.setOpacity(100);
            }
        });
    }
}
