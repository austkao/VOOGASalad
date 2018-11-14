package renderer.external.Structures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Level {

    private int windowHeight;
    private int windowWidth;


    private static final int TILE_WIDTH = 50;
    private static final int TILE_HEIGHT = 50;

    Tile [][] grid;
    ImageView background;
    Pane window;

    public Level(Image bk, int windowWidth, int windowHeight){
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        grid = new Tile[windowHeight/TILE_HEIGHT][windowWidth/TILE_WIDTH];
        background = new ImageView();
        setBackground(bk);
        window = new Pane();
        window.setPrefWidth(windowWidth);
        window.setPrefHeight(windowHeight);
        window.getChildren().add(background);

    }

    public void addTile(int x, int y, Image tileImage){
        grid[y][x] = new Tile(tileImage, TILE_WIDTH, TILE_HEIGHT);
        grid[y][x].setLocation(x, y);
        window.getChildren().add(grid[y][x]);
    }

    public void setBackground(Image bk){
        background.setImage(bk);
        background.setFitHeight(windowHeight);
        background.setFitWidth(windowWidth);

    }

    public Pane getWindow(){
        return window;
    }




}
