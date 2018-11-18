package player.internal;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import renderer.external.Renderer;
import renderer.external.Structures.CharacterChooseDisplay;

import java.io.File;
import java.util.ArrayList;

/** Dynamic layout for the display of all available characters, allows users to select their
 *  character for a fight
 *  @author bpx
 */
public class CharacterSelectScreen extends Screen {

    public static final int CHAR_PER_ROW = 8;
    private File myDirectory;

    public CharacterSelectScreen(Group root, Renderer renderer, File gameDirectory) {
        super(root, renderer);
        super.setFill(Color.WHITE);
        myDirectory = gameDirectory;
        VBox holder = new VBox(15);
        holder.setPrefSize(1280,800);
        holder.setAlignment(Pos.BOTTOM_CENTER);
        ImageView bg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("characterselect_bg.jpg")));
        bg.setFitWidth(1280);
        bg.setFitHeight(800);
        bg.setOpacity(0.52);
        VBox charGrid = setUpCharGrid();
        HBox charBox = new HBox(10);
        charBox.setMaxHeight(332.0);
        charBox.setAlignment(Pos.CENTER);
        CharacterChooseDisplay char1 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#FD1B1B"),"Player 1");
        CharacterChooseDisplay char2 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#4C7FFF"),"Player 2");
        CharacterChooseDisplay char3 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#FFF61B"),"Player 3");
        CharacterChooseDisplay char4 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#1FCB17"),"Player 4");
        super.getMyRoot().getChildren().addAll(bg,holder);
        holder.getChildren().addAll(charGrid,charBox);
        charBox.getChildren().addAll(char1, char2, char3, char4);
        char1.setPortrait(new Image(this.getClass().getClassLoader().getResourceAsStream("lucina.png")));
    }

    /** Algorithmically creates a grid of characters based on number of directories available */
    private VBox setUpCharGrid(){
        VBox grid = new VBox(1.0);
        grid.setAlignment(Pos.CENTER);
        grid.setPrefSize(1280,382);
        grid.setStyle("-fx-background-color: #201D20;");
        int charcount = 0;
        ArrayList<File> files  = new ArrayList<>();
        for(File f : new File(myDirectory.getPath()+"\\characters").listFiles()){
            if(!f.getName().contains(".")){
                charcount++;
                files.add(f);
            }
        }
        int rowcount = (int)Math.ceil(charcount/(double)CHAR_PER_ROW);
        for(int i = 0;i<rowcount;i++){
            HBox row = new HBox(1.0);
            row.setAlignment(Pos.CENTER);
            for(int j = 0; j < CHAR_PER_ROW; j++){
                if((CHAR_PER_ROW*(i))+j+1>charcount){
                    break;
                }
                else{
                    ImageView portrait = new ImageView(new Image(String.format("%s/%s",files.get((CHAR_PER_ROW*(i))+j).toURI(),"portrait.png")));
                    portrait.setPreserveRatio(true);
                    portrait.setFitWidth(132);
                    portrait.setViewport(new Rectangle2D(56,25,132,95));
                    row.getChildren().add(portrait);
                }
            }
            grid.getChildren().add(row);
        }
        return grid;
    }

}
