package player.internal;

import javafx.scene.Group;
import renderer.external.Renderer;

/** Creates a dynamic display for all stages available to the user, allows the user to choose
 *  a stage to do battle on
 *  @author bpx
 */
public class StageSelectScreen extends Screen {

    public StageSelectScreen(Group root, Renderer renderer) {
        super(root, renderer);
    }
}
