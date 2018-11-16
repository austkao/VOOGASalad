package player.internal;

import javafx.scene.Group;
import renderer.external.Renderer;

/** Dynamic layout for the display of all available characters, allows users to select their
 *  character for a fight
 *  @author bpx
 */
public class CharacterSelectScreen extends Screen {

    public CharacterSelectScreen(Group root, Renderer renderer) {
        super(root, renderer);
    }

}
