package editor;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * This interface defines methods that will be used by any screen in the game editor
 * @author ak457
 */
public interface EditorScreen {
    Text createTitle();
    Button createBack(Scene scene);
    String toString();
    String getGameDirectoryString();
    //void changeScene();
}
