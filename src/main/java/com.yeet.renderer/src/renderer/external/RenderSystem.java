package renderer.external;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import renderer.external.Structures.*;

import java.util.List;
import java.util.function.Consumer;

import static renderer.internal.RenderUtils.toRGBCode;

/** Provides a high-level tool for the rapid creation of standardized and stylized core UI elements and graphics
 *  @author bpx
 *  @author ob29
 *  @author rr202
 */
public class RenderSystem implements Renderer{

    public static final String BUTTON_FORMAT = "-fx-background-color: %s; -fx-font-family: '%s'; -fx-background-radius: %s; -fx-background-insets: 0; -fx-font-size: %s;";
    public static final String BUTTON_SCALE = "-fx-scale-x: %s; -fx-scale-y: %s;";
    public static final double BUTTON_SCALE_FACTOR = 1.2;
    public static final Double SLIDER_DEFAULT = 50.0;
    public static final double SLIDER_HEIGHT = 50.0;
    public static final double VBOX_SPACING = 5.0;

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
    public Button makeStringButton(String text, Color buttonColor, Boolean emphasis, Color textColor, Double fontSize, Double x, Double y, Double width, Double height){
        Font font;
        if(emphasis){
            font = myEmphasisFont;
        }
        else{
            font = myPlainFont;
        }
        Button button = new Button(text);
        //BUTTON_FORMAT: Color buttonColor, String fontName, Double borderRadius, Double fontSize
        button.setStyle(String.format(BUTTON_FORMAT,toRGBCode(buttonColor),font.getName(),height,fontSize));
        button.setTextFill(textColor);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(width,height);
        Font finalFont = font;
        //BUTTON_SCALE: Double xScale, Double yScale
        button.setOnMouseEntered(event->button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(buttonColor),finalFont.getName(),height,fontSize, myButtonScaleFactor,myButtonScaleFactor)));
        button.setOnMouseExited(event -> button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(buttonColor),finalFont.getName(),height,fontSize,1.0,1.0)));
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
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        button.setGraphic(imageView);
        button.setLayoutX(x);
        button.setLayoutY(y);
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
            newtext.setStyle(String.format("-fx-font-family: '%s'; -fx-font-size: %s; -fx-text-fill: %s;",myEmphasisFont.getName(),fontsize,toRGBCode(color)));
        }
        else{
            newtext.setStyle(String.format("-fx-font-family: '%s'; -fx-font-size: %s; -fx-text-fill: %s;",myPlainFont.getName(),fontsize,toRGBCode(color)));
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
    public void drawStage(Group root, Image image){
        Level level = new Level(image);
        root.getChildren().add(level.getWindow());
    }

    /** Creates an editable {@code TextField}
     *  @param text The default text to display in the {@code TextField}
     *  @param x The x position of the {@code TextField}
     *  @param y The y position of the {@code TextField}
     *  @param w The width of the {@code TextField}
     *  @param h The height of the {@code TextField}
     */
    public VBox makeTextField(Consumer<String> fieldSetter, String text, Double x, Double y, Double w, Double h, Font font){
        VBox textBox = new VBox(VBOX_SPACING);
        textBox.setAlignment(Pos.CENTER_RIGHT);
        TextField textField = new TextField(text);
        textField.setUserData(text);
        textBox.setLayoutX(x);
        textBox.setLayoutY(y);
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
        textBox.getChildren().addAll(textField,textLabel);
        return textBox;
    }

    /** Creates a {@code Slider} that modifies a field
     *  @param fieldSetter The lambda that will modify the necessary parameter using the {@code Slider} value
     *  @param x The x position of the {@code Slider}
     *  @param y The y position of the {@code Slider}
     *  @param w The width of the {@code Slider}
     *  @param font The font of the label text
     */
    public HBox makeSlider(Consumer<Double> fieldSetter, Double x, Double y, Double w, Font font){
        HBox sliderBox = new HBox();
        sliderBox.setAlignment(Pos.CENTER);
        sliderBox.setLayoutX(x);
        sliderBox.setLayoutY(y);
        Slider slider =  new Slider();
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
        slider.setValue(SLIDER_DEFAULT);
        slider.setPrefSize(w, SLIDER_HEIGHT);
        Label sliderValue = new Label(String.valueOf(SLIDER_DEFAULT));
        sliderValue.setLabelFor(slider);
        sliderValue.setFont(font);
        slider.setOnMouseReleased(event -> {
            //rounds slider value to 1 decimal place
            fieldSetter.accept(Math.round(slider.getValue() * 10.0) / 10.0);
            sliderValue.setText(String.valueOf(Math.round(slider.getValue() * 10.0) / 10.0));
        });
        sliderBox.getChildren().addAll(slider,sliderValue);
        return sliderBox;
    }

    /** Creates a {@code FileChooser} for a specific file type
     *  @param filetype The file type to be accepted, can be "image","audio",or "xml", or "all"
     */
    public FileChooser makeFileChooser(String filetype){
        FileChooser.ExtensionFilter extensionFilter;
        if(filetype.equalsIgnoreCase("xml")){
            extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        }
        else if(filetype.equalsIgnoreCase("audio")){
            extensionFilter = new FileChooser.ExtensionFilter("Audio files (*.mp3, *.wav, *.aac, *.aiff)", "*.mp3","*.wav","*.aac","*.aiff");
        }
        else if(filetype.equalsIgnoreCase("image")){
            extensionFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.gif, *.jpg, *.mpo)", "*.png","*.gif","*.jpg","*.mpo");
        }
        else if(filetype.equalsIgnoreCase("all")){
            return new FileChooser();
        }
        else{
            throw new IllegalArgumentException("Invalid filetype parameter");
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extensionFilter);
        return fileChooser;
    }

    /** Creates a {@code DirectoryChooser}
     */
    public DirectoryChooser makeDirectoryChooser(){
        return new DirectoryChooser();
    }

    /** Creates a horizontal set of string-labelled {@code Button} objects where only one can be active at a time
     *  @param options The possible options for the buttons
     */
    public SwitchButton makeSwitchButtons(List<String> options, boolean emphasis, Color bgColor, Color textColor, Double spacing, Double x, Double y, Double w, Double h){
        if(emphasis){
            return new SwitchButton(options,spacing,x,y,w,h,bgColor,textColor,myEmphasisFont);
        }
        else{
            return new SwitchButton(options,spacing,x,y,w,h,bgColor,textColor,myPlainFont);
        }
    }

    /** Creates a {@code Carousel} that displays {@code String} objects, that can be cycled through with left and right arrow buttons
     *  @param options The possible options for the {@code Carousel}
     *  @param emphasis Whether to use emphasis font or plain font
     *  @param bgColor The main background fill for the {@code Carousel}
     *  @param textColor The text fill color
     *  @param spacing Amount of spacing between main carousel elements
     *  @param x X position of the {@code Carousel}
     *  @param y Y Position of the {@code Carousel}
     *  @param w The width of the {@code Carousel} {@code String} display
     *  @param h The height of the {@code Carousel}
     */
    public Carousel makeCarousel(List<String> options, boolean emphasis, Color bgColor, Color textColor, Double spacing, Double x, Double y, Double w, Double h){
        if(emphasis){
            return new Carousel(options,spacing, x,y,w,h,bgColor,textColor,myEmphasisFont);
        }
        else{
            return new Carousel(options,spacing, x,y,w,h,bgColor,textColor,myPlainFont);
        }
    }

    /** Creates an animation using a {@code Sprite}
     * @param sprite The {@code Sprite} that will be animated
     * @param duration The total length of the animation
     * @param count The total number of frames
     * @param columns The number of frames per row
     * @param offsetX The offset of the first frame in the x direction
     * @param offsetY The offset of the first frame in the y direction
     * @param width The width of each animation frame
     * @param height The height of each animation frame
     */
    public SpriteAnimation makeSpriteAnimation(Sprite sprite, Duration duration,
                                               Integer count, Integer columns,
                                               Double offsetX, Double offsetY,
                                               Double width, Double height){

        return new SpriteAnimation(sprite, duration, count, columns, offsetX, offsetY, width, height);
    }

    /** Creates a {@code Sprite} from an {@code Image} and sets its viewport to the default frame
     *  @param image The {@code Image} to conver to a {@code Sprite}
     *  @param offsetX The offset of the first frame in the x direction
     *  @param offsetY The offset of the first frame in the y direction
     *  @param width The width of the first frame
     *  @param height The height of the first frame
     */
    public Sprite makeSprite(Image image, Double offsetX, Double offsetY, Double width, Double height){
        Sprite sprite = new Sprite(image, width, height);
        sprite.setViewport(offsetX,offsetY);
        return sprite;
    }
}
