package renderer.external.Structures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class Level {

    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 400;


    private static final int TILE_WIDTH = 50;
    private static final int TILE_HEIGHT = 50;

    Tile [][] grid;
    ImageView background;
    Pane window;

    public Level(Image bk){
        grid = new Tile[WINDOW_HEIGHT/TILE_HEIGHT][WINDOW_WIDTH/TILE_WIDTH];
        background = new ImageView();
        window = new Pane();
        window.setPrefWidth(WINDOW_WIDTH);
        window.setPrefHeight(WINDOW_HEIGHT);
        window.getChildren().add(background);

    }

    public void addTile(int x, int y, Image tileImage){
        grid[y][x] = new Tile(tileImage, TILE_WIDTH, TILE_HEIGHT);
        grid[y][x].setLocation(x, y);
        window.getChildren().add(grid[y][x]);
    }

    public void setBackground(Image bk){
        background.setImage(bk);

    }




}
