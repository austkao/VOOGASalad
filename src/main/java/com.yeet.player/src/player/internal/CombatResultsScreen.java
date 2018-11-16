package player.internal;

import javafx.scene.Group;
import renderer.external.Renderer;

/** Displays the winner of the combat as well as other related statistics and information
 *  @author bpx
 */
public class CombatResultsScreen extends Screen {

    public CombatResultsScreen(Group root, Renderer renderer) {
        super(root, renderer);
    }
}
