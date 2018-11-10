package editor;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class workspace {
    public static final int TURTLE_VIEW_WIDTH = 500;

    private Pane root;
    private Rectangle selection;

    private Tile [][] grid;

    public workspace() {
        root = new Pane();
        setBackgroundColor(Color.WHITE);
        selection = new Rectangle(0, 0, 0, 0);
        selection.setOpacity(0.1);
        selection.setFill(Color.valueOf("#00ff48"));
        selection.setStroke(Color.BLACK);
        selection.setStrokeWidth(5.0);
        root.getChildren().add(selection);
    }



    public Rectangle selection() { return selection; }
    public void setBackgroundColor(Color c) { root.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY))); }
    public Pane view() { return root; }
}
