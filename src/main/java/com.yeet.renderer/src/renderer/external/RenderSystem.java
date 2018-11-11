package renderer.external;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import renderer.external.Structures.*;

import java.util.List;

/** Provides a high-level tool for the rapid creation of core UI elements and graphics */
public class RenderSystem {

    public static final String BUTTON_FORMAT = "-fx-background-color: %s; -fx-font-family: '%s'; -fx-background-radius: %s; -fx-background-insets: 0; -fx-font-size: %s;";
    public static final String BUTTON_SCALE = "-fx-scale-x: %s; -fx-scale-y: %s;";
    public static final double BUTTON_SCALE_FACTOR = 1.2;

    private Font myEmphasisFont;
    private Font myPlainFont;

    private Double myButtonScaleFactor;

    /** Create a new {@code RenderSystem} with the specified default stylistic options*/
    public RenderSystem(Font plainFont,Font emphasisfont){
        myPlainFont=plainFont;
        myEmphasisFont = emphasisfont;
        myButtonScaleFactor = BUTTON_SCALE_FACTOR;
    }


    /** Creates a {@code Button} with a label
     *  @param text The label for the button
     *  @param buttonColor The hex string for the color of the button background
     *  @param emphasis Whether to use emphasis text or plain text
     *  @param textColor The fill {@code Color} for the label
     *  @param fontSize The font size for the text label of the button
     *  @param x The x position of the button
     *  @param y The y position of the button
     *  @param width The width of the button
     *  @param height The height of the button */
    public Button makeStringButton(String text, String buttonColor, Boolean emphasis, Color textColor, Double fontSize, Double x, Double y, Double width, Double height){
        Font font;
        if(emphasis){
            font = myEmphasisFont;
        }
        else{
            font = myPlainFont;
        }
        Button button = new Button(text);
        //BUTTON_FORMAT: String buttonColor, String fontName, Double borderRadius, Double fontSize
        button.setStyle(String.format(BUTTON_FORMAT,buttonColor,font.getName(),height,fontSize));
        button.setTextFill(textColor);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(width,height);
        Font finalFont = font;
        //BUTTON_SCALE: Double xScale, Double yScale
        button.setOnMouseEntered(event->button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,buttonColor,finalFont.getName(),height,fontSize, myButtonScaleFactor,myButtonScaleFactor)));
        button.setOnMouseExited(event -> button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,buttonColor,finalFont.getName(),height,fontSize,1.0,1.0)));
        return button;
    }

    /** Creates a button using an image
     *  @param image The {@code ImageView} that will be used as the button
     *  @param x The x position of the button
     *  @param y The y position of the button
     *  @param width The width of the button
     *  @param height The height of the button */
    public Button makeImageButton(Image image, Double x, Double y, Double width, Double height){
        Button button = new Button();
        button.setGraphic(new ImageView(image));
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setMaxSize(width,height);
        return button;
    }

    /** Creates text
     *  @param text The text to display
     *  @param emphasis Whether to use emphasis font or plain font
     *  @param fontsize The size of the font
     *  @param color The fill {@code Color} for the font
     *  @param x The x position of the text
     *  @param y The y position of the text*/
    public Text makeText(String text, Boolean emphasis, Integer fontsize, Color color, Double x, Double y){
        Text newtext = new Text(text);
        if(emphasis){
            newtext.setFont(myEmphasisFont);
        }
        else{
            newtext.setFont(myPlainFont);
        }
        newtext.setX(x);
        newtext.setY(y);
        return newtext;
    }

    /** Creates a scrollable Pane that displays its contents in a grid
     *  @param contentList The content list, contains {@code ImageView} objects to display on the grid*/
    public ScrollPane makeGridScrollPane(List<ImageView> contentList){
        return new ScrollPane();
    }

    /** Creates a scrollable Pane that displays its contents in a list
     *  @param dataList contains the {@code ScrollableItem} to display */
    public ScrollPane makeListScrollPane(List<ScrollableItem> dataList){
        return new ScrollPane();
    }

    /** Draws a {@code Level} to the specified target
     *  @param root The target {@code Group} to draw to
     *  @param level The {@code Level} to draw */
    public void drawStage(Group root, Level level){

    }

    /** Creates an editable {@code TextField}
     *  @param text The default text to display in the {@code TextField}*/
    public TextField makeTextField(String text){
        return new TextField();
    }

    /** Creates a {@code Slider} that modifies a field
     *  @param field The {@code Double} that will be modified by the {@code Slider}*/
    public Slider makeSlider(Double field){
        return new Slider();
    }

    /** Creates a {@code FileChooser} for a specific file type
     *  @param filetype The file type to be accepted, can be "image","audio",or "xml" */
    public FileChooser makeFileChooser(String filetype){
        return new FileChooser();
    }

    /** Creates a {@code DirectoryChooser}*/
    public DirectoryChooser makeDirectoryChooser(){
        return new DirectoryChooser();
    }

    /** Creates a horizontal set of string-labelled {@code Button} objects where only one can be active at a time
     *  @param options The possible options for the buttons */
    public SwitchButton makeSwitchButtons(List<String> options){
        return new SwitchButton();
    }

    /** Creates a {@code Carousel} that displays {@code String} objects, that can be cycled through with left and right arrow buttons
     *  @param options The possible options for the {@code Carousel}*/
    public Carousel makeCarousel(List<String> options){
        return new Carousel();
    }

    /** Creates an animation using a {@code Sprite}
     * @param sprite The {@code Sprite} that will be animated
     * @param duration The total length of the animation
     * @param count The total number of frames
     * @param columns The number of frames per row
     * @param offsetX The offset of the first frame in the x direction
     * @param offsetY The offset of the first frame in the y direction
     * @param width The width of each animation frame
     * @param height The height of each animation frame*/
    public SpriteAnimation makeSpriteAnimation(Sprite sprite, Duration duration,
                                               Integer count, Integer columns,
                                               Double offsetX, Double offsetY,
                                               Double width, Double height){
        return null;
    }

    /** Creates a {@code Sprite} from an {@code Image} and sets its viewport to the default frame
     *  @param image The {@code Image} to conver to a {@code Sprite}
     *  @param offsetX The offset of the first frame in the x direction
     *  @param offsetY The offset of the first frame in the y direction
     *  @param width The width of the first frame
     *  @param height The height of the first frame
     */
    public Sprite makeSprite(Image image, Double offsetX, Double offsetY, Double width, Double height){
        return null;
    }
}
