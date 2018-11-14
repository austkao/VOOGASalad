/**
 * Class representation of each level or stage.
 */

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



    /**
     * Constructs the level
     * @param bk = background image
     * @param windowWidth = width of window of pane
     * @param windowHeight = height of window of pane
     */
    public Level(Image bk, int windowWidth, int windowHeight){
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        window = new Pane();
        window.setPrefWidth(windowWidth);
        window.setPrefHeight(windowHeight);

        background = new ImageView();
        setBackground(bk);

        resetGrid();
    }

    /**
     * resets grid using constructed window width and height
     */
    public void resetGrid(){
        grid = new Tile[windowHeight/TILE_HEIGHT][windowWidth/TILE_WIDTH];
        window.getChildren().clear();
        window.getChildren().add(background);
    }

    /**
     * creates new tile given image, places it at index y, x (location x, y)
     * @param x
     * @param y
     * @param tileImage
     */
    public void processTile(int x, int y, Image tileImage){
        if (!isTile(x, y)){
            grid[y][x] = new Tile(tileImage, TILE_WIDTH, TILE_HEIGHT);
            grid[y][x].setLocation(x, y);
            window.getChildren().add(grid[y][x]);
        }
        else{
            window.getChildren().remove(grid[y][x]);
            grid[y][x] = null;
        }

    }

    /**
     * sets background image
     * @param bk image for background
     */
    public void setBackground(Image bk){
        background.setImage(bk);
        background.setFitHeight(windowHeight);
        background.setFitWidth(windowWidth);

    }

    public Pane getWindow(){
        return window;
    }

    public int getTileWidth(){
        return TILE_WIDTH;
    }
    public int getTileHeight(){
        return TILE_HEIGHT;
    }

    /**
     * checks whether tile exists at current location
     * @param x
     * @param y
     * @return
     */
    public boolean isTile(int x, int y){
        return grid[y][x] != null;
    }




}
