package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.function.Consumer;

/** Extension of HBox with a {@code Slider} that allows for getting of its value from the container
 *  @author bpx
 */
public class SliderBox extends HBox {

    public static final Double SLIDER_DEFAULT = 50.0;
    public static final double SLIDER_HEIGHT = 50.0;

    private double myValue;

    /** Default constructor */
    public SliderBox(String text, Font font, Consumer<Double> fieldSetter, Double x, Double y, Double w){
        super();
        this.setAlignment(Pos.CENTER);
        this.setLayoutX(x);
        this.setLayoutY(y);
        Slider slider =  new Slider();
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
        slider.setValue(SLIDER_DEFAULT);
        slider.setPrefSize(w, SLIDER_HEIGHT);
        Label name = new Label(text);
        name.setFont(font);
        name.setLabelFor(slider);
        Label sliderValue = new Label(String.valueOf(SLIDER_DEFAULT));
        sliderValue.setLabelFor(slider);
        sliderValue.setFont(font);
        slider.setOnMouseReleased(event -> {
            //rounds slider value to 1 decimal place
            fieldSetter.accept(Math.round(slider.getValue() * 10.0) / 10.0);
            sliderValue.setText(String.valueOf(Math.round(slider.getValue() * 10.0) / 10.0));
            myValue = Math.round(slider.getValue() * 10.0) / 10.0;
        });
        this.getChildren().addAll(name,slider,sliderValue);
    }

    /** Returns the current value of the slider */
    public double getValue(){
        return myValue;
    }
}
