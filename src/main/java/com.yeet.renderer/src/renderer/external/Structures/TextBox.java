package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.Consumer;

/** Custom extension of {@code VBox} that contains a {@code TextField} that allows for getting the value of the {@code TextField}
 *  @author bpx
 */
public class TextBox extends VBox {

    public static final double VBOX_SPACING = 5.0;

    private String myText;

    public TextBox(Consumer<String> fieldSetter, String text, Double x, Double y, Double w, Double h, Font font){
        super(VBOX_SPACING);
        this.setAlignment(Pos.CENTER_RIGHT);
        TextField textField = new TextField(text);
        textField.setUserData(text);
        this.setLayoutX(x);
        this.setLayoutY(y);
        textField.setPrefSize(w,h);
        textField.setFont(font);
        Label textLabel = new Label("");
        textLabel.setFont(font);
        textLabel.setTextFill(Color.RED);
        textField.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ENTER){
                // field reverts to previous value if consumer fails
                try{
                    fieldSetter.accept(textField.getText());
                    textField.setUserData(textField.getText());
                    myText = textField.getText();
                    textLabel.setText("");
                }
                catch(Exception e){
                    textField.setText((String)textField.getUserData());
                    textLabel.setText("Invalid input.");
                }

            }
            else if(event.getCode()==KeyCode.ESCAPE){
                textField.setText((String)textField.getUserData());
            }
        });
        this.getChildren().addAll(textField,textLabel);
    }

    /** Returns the current stored value of the {@code TextField}*/
    public String getText(){
        return myText;
    }

}
