package player.internal;

import javafx.scene.Group;
import javafx.scene.Scene;
import renderer.external.Renderer;

/** Super class for all scenes for the player
 *  @author bpx
 */
public abstract class Screen extends Scene {

    public Screen(Group root, Renderer renderer) {
        super(root);
    }

}
