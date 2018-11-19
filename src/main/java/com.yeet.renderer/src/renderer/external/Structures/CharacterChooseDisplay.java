package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static renderer.internal.RenderUtils.toRGBCode;

/** Custom extension of {@code StackPane} that is used to display what character the player has chosen, as well as toggle
 *  whether the selected player should be a human, computer, or none.
 *  @author bpx
 */
public class CharacterChooseDisplay extends StackPane {

    public static final String FORMAT_RECT = "-fx-border-radius: %s;-fx-background-radius: %s; -fx-background-color: %s";

    public enum State{
        HUMAN, CPU, NONE;
    }

    private DragToken myButton;
    private State myState;
    private Color myColor;
    private String myText;


    private StackPane bottom;
    private StackPane colorblock;
    private StackPane namepiece;
    private StackPane iconHolder;

    private ImageView portrait;

    private ImageView icon;
    private final Image none = new Image(this.getClass().getClassLoader().getResourceAsStream("none_icon.png"));
    private final Image human = new Image(this.getClass().getClassLoader().getResourceAsStream("human_icon.png"));
    private final Image cpu = new Image(this.getClass().getClassLoader().getResourceAsStream("cpu_icon.png"));

    private Text text;

    /** Creates a new {@code CharacterChooseDisplay} using the specified parameters
     *  @param color The {@code Color} the player will be represented by
     *  @param defaultText The default name of the player
     *  @param font The {@code Font} to display the name in
     *  @param button The token for choosing characters
     */
    public CharacterChooseDisplay(Color color, String defaultText, Font font, DragToken button){
        super();
        myButton = button;
        myButton.setDisable(true);
        myButton.setOpacity(0);
        myState = State.NONE;
        myColor = color;
        myText = defaultText;
        super.setAlignment(Pos.BOTTOM_RIGHT);
        super.setPrefSize(305.0,332.0);
        super.setStyle(String.format(FORMAT_RECT,"100 0 0 0","100 0 0 0","transparent"));
        portrait = new ImageView();
        portrait.setPreserveRatio(true);
        portrait.setFitHeight(332);
        portrait.setOpacity(0);
        bottom = new StackPane();
        bottom.setAlignment(Pos.TOP_LEFT);
        bottom.setMaxSize(305.0,107.0);
        bottom.setStyle(String.format(FORMAT_RECT,"0 35 0 0", "0 35 0 0", toRGBCode(Color.web("#1F1C1F"))));
        colorblock = new StackPane();
        colorblock.setAlignment(Pos.CENTER);
        colorblock.setMaxSize(296.0,69.0);
        colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35","transparent"));
        namepiece = new StackPane();
        namepiece.setAlignment(Pos.CENTER_LEFT);
        namepiece.setMaxSize(257.0,33.0);
        namepiece.setStyle(String.format(FORMAT_RECT,"17 17 17 17","17 17 17 17",toRGBCode(Color.WHITE)));
        iconHolder = new StackPane();
        HBox nameBox = new HBox(16.5);
        iconHolder.setAlignment(Pos.CENTER);
        iconHolder.setMaxSize(33,33);
        iconHolder.setStyle(String.format(FORMAT_RECT,"17 17 17 17","17 17 17 17",toRGBCode(Color.web("#1F1C1F"))));
        icon = new ImageView(none);
        icon.setFitWidth(33);
        icon.setFitHeight(33);
        text = new Text("None");
        text.setFont(font);
        super.getChildren().addAll(portrait,bottom);
        bottom.getChildren().addAll(colorblock);
        colorblock.getChildren().addAll(namepiece);
        namepiece.getChildren().addAll(nameBox);
        nameBox.getChildren().addAll(iconHolder, text);
        iconHolder.getChildren().addAll(icon);
        iconHolder.setOnMousePressed(event -> nextState());
    }

    /** Advances the {@code CharacterChooseDisplay} to the next state, NONE->HUMAN->CPU, then goes back to NONE*/
    private void nextState(){
        switch(myState){
            case NONE:
                myState = State.HUMAN;
                super.setStyle(String.format(FORMAT_RECT,"100 0 0 0","100 0 0 0",toRGBCode(Color.web("#1F1C1F"))));
                colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35",toRGBCode(myColor)));
                icon.setImage(human);
                text.setText(myText);
                portrait.setOpacity(1);
                myButton.resetColor();
                myButton.setOpacity(1);
                myButton.setDisable(false);
                break;
            case HUMAN:
                myState = State.CPU;
                super.setStyle(String.format(FORMAT_RECT, "0 0 0 0", "0 0 0 0","rgba(255,255,255,0.64)"));
                colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35",toRGBCode(Color.web("#5B585C"))));
                icon.setImage(cpu);
                text.setText("CPU");
                myButton.setColor(Color.web("#848484"));
                break;
            case CPU:
                myState = State.NONE;
                super.setStyle(String.format(FORMAT_RECT,"100 0 0 0","100 0 0 0","transparent"));
                colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35","transparent"));
                icon.setImage(none);
                text.setText("None");
                portrait.setOpacity(0);
                myButton.setOpacity(0);
                myButton.setDisable(true);
                break;
        }

    }

    /** Set the character portrait
     *  @param image The image representing the character chosen
     */
    public void setPortrait(Image image){
        portrait.setImage(image);
    }

    /** Returns the current {@code State} of the {@code CharacterChooseDisplay}*/
    public State getState(){
        if(myState.equals(State.HUMAN)){
            return State.HUMAN;
        }
        else if(myState.equals(State.CPU)){
            return State.CPU;
        }
        else{
            return State.NONE;
        }
    }

}
