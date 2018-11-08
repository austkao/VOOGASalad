import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/** External API for Renderer system, focused on creating UI elements and other graphical
 *  functionality necessary to edit and play games using the game engine.
 *  @author Benjamin Xu
 *  @author Orgil Batzaya */
public interface Renderer{

    public Button makeStringButton(String text);

    public Button makeImageButton(Image image);

    public Text makeEmphasisText(String text, Integer fontsize, Color color, Double x, Double y);

    public Text makePlainText(String text, Integer fontsize, Color color, Double x, Double y);

    public ScrollPane makeGridScrollPane(List<Block> blockList);

    public ScrollPane makeListScrollPane(List<Level> levelList);

    public void drawStage(Group root, Level level);

    public TextField makeTextField(String text);

    public Slider makeSlider(Double field);

    public FileChooser makeFileChooser(String filetype);

    public DirectoryChooser makeDirectoryChooser();

    public SwitchButton makeSwitchButtons(List<String> options);

    public Carousel makeCarousel(List<String> options);

}