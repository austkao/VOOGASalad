package renderer.external.Structures;
import javafx.scene.image.Image;
import org.junit.Test;

import static org.testng.Assert.assertEquals;


public class LevelTest {
    // do not change this test
    private static final String DEFAULT_BACKGROUND_IMAGE = "fd.jpg";
    private static final String DEFAULT_TILE = "acacia_log.png";

    @Test
    public void testSizeChangeWithAdd () {
        int expected = 3;
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_BACKGROUND_IMAGE));
        Image tile = new Image(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_TILE));

        Level level = new Level(image,500,500);
        level.processTile(0,0,tile);
        level.processTile(0,0,tile);
        level.processTile(0,0,tile);


        assertEquals(expected, level.window.getChildren().size(), "Add size");
    }
}
