/**
 * Class representation of each level or stage.
 */

package renderer.external.Structures;

import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Level {

    private int windowHeight;
    private int windowWidth;


    private static final int TILE_WIDTH = 50;
    private static final int TILE_HEIGHT = 50;

    private Tile [][] grid;
    private GridPane window;
    private String backgroundURL;


    /**
     * Constructs the level
     * @param windowWidth = width of window of pane
     * @param windowHeight = height of window of pane
     * @param backgroundURL = url for background image
     */
    public Level(int windowWidth, int windowHeight, String backgroundURL){
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        window = new GridPane();
        window.setGridLinesVisible(true);
        window.setPrefWidth(windowWidth);
        window.setPrefHeight(windowHeight);

        int numCols = windowWidth/TILE_WIDTH;
        int numRows = windowHeight/TILE_HEIGHT;

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            window.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            window.getRowConstraints().add(rowConst);
        }

        this.backgroundURL = backgroundURL;
        resetGrid();
    }

    /**
     * resets grid using constructed window width and height
     */
    public void resetGrid(){
        grid = new Tile[windowHeight/TILE_HEIGHT][windowWidth/TILE_WIDTH];
        window.getChildren().clear();
        setBackground(backgroundURL);
    }

    /**
     * creates new tile given image, places it at index y, x (location x, y)
     * @param x
     * @param y
     * @param tileImage
     */
    public void processTile(int x, int y, Image tileImage){
        if (!isTile(x, y)){
            grid[y][x] = new Tile(tileImage, TILE_WIDTH, TILE_HEIGHT, x, y);
            window.add(grid[y][x], x, y);
        }
        else{
            window.getChildren().remove(grid[y][x]);
            grid[y][x] = null;
        }
    }

    /**
     * sets background image
     * @param backgroundURL image for background
     */
    public void setBackground(String backgroundURL){
        this.backgroundURL = backgroundURL;
        String formatted = String.format("-fx-background-image: url('%s');", backgroundURL);
        formatted = formatted + String.format("-fx-background-size: cover;");
        window.setStyle(formatted);
    }

    public GridPane getWindow(){
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
