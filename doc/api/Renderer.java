import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/** External API for Renderer system, focused on creating UI elements and other graphical
 *  functionality necessary to edit and play games using the game engine.
 *  @author Benjamin Xu
 *  @author Orgil Batzaya */
public interface Renderer{

    /** Creates a {@code Button} with a label
     *  @param text The label for the button
     *  @param buttonColor The fill {@code Color} for the button
     *  @param textColor The fill {@code Color} for the label
     *  @param x The x position of the button
     *  @param y The y position of the button */
    public Button makeStringButton(String text, Color buttonColor, Color textColor, Double x, Double y);

    /** Creates a button using an image
     *  @param image The {@code ImageView} that will be used as the button
     *  @param x The x position of the button
     *  @param y The y position of the button */
    public Button makeImageButton(Image image, Double x, Double y);

    /** Creates emphatic text
     *  @param text The text to display
     *  @param fontsize The size of the font
     *  @param color The fill {@code Color} for the font
     *  @param x The x position of the text
     *  @param y The y position of the text*/
    public Text makeEmphasisText(String text, Integer fontsize, Color color, Double x, Double y);

    /** Creates normal text
     *  @param text The text to display
     *  @param fontsize The size of the font
     *  @param color The fill {@code Color} for the font
     *  @param x The x position of the text
     *  @param y The y position of the text*/
    public Text makePlainText(String text, Integer fontsize, Color color, Double x, Double y);

    /** Creates a scrollable Pane that displays its contents in a grid
     *  @param blockList The content list, contains {@code ImageView} objects to display on the grid*/
    public ScrollPane makeGridScrollPane(List<ImageView> contentList);

    /** Creates a scrollable Pane that displays its contents in a list
     *  @param dataList contains the {@code Data} to display */
    public ScrollPane makeListScrollPane(List<Data> dataList);

    /** Draws a {@code Level} to the specified target
     *  @param root The target {@code Group} to draw to
     *  @param level The {@code Level} to draw */
    public void drawStage(Group root, Level level);

    /** Creates an editable {@code TextField}
     *  @param text The default text to display in the {@code TextField}*/
    public TextField makeTextField(String text);

    public Slider makeSlider(Double field);

    public FileChooser makeFileChooser(String filetype);

    public DirectoryChooser makeDirectoryChooser();

    public SwitchButton makeSwitchButtons(List<String> options);

    public Carousel makeCarousel(List<String> options);

}