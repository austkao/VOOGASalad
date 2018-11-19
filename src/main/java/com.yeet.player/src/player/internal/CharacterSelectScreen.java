package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import renderer.external.Renderer;
import renderer.external.Structures.CharacterChooseDisplay;
import renderer.external.Structures.CharacterGrid;
import renderer.external.Structures.DragToken;

import java.io.File;

/** Dynamic layout for the display of all available characters, allows users to select their
 *  character for a fight
 *  @author bpx
 */
public class CharacterSelectScreen extends Screen {

    public static final int CHAR_PER_ROW = 8;
    public static final int BUTTON_SIZE = 40;
    private File myDirectory;

    private CharacterGrid myCharGrid;

    private CharacterChooseDisplay display1;
    private CharacterChooseDisplay display2;
    private CharacterChooseDisplay display3;
    private CharacterChooseDisplay display4;


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
        myCharGrid = super.getMyRenderer().makeCharacterGrid(myDirectory,CHAR_PER_ROW,this::setCharacter);
        HBox charBox = new HBox(10);
        charBox.setMaxHeight(332.0);
        charBox.setAlignment(Pos.CENTER);
        DragToken button1 = super.getMyRenderer().makeDragToken("P1",Color.web("#FD1B1B"), 40,130,548,40, this::getCharacter);
        DragToken button2 = super.getMyRenderer().makeDragToken("P2",Color.web("#4C7FFF"),40,439,548,40, this::getCharacter);
        DragToken button3 = super.getMyRenderer().makeDragToken("P3",Color.web("#FFF61B"),40,752,545,40, this::getCharacter);
        DragToken button4 = super.getMyRenderer().makeDragToken("P4",Color.web("#1FCB17"),40,1079,545,40, this::getCharacter);
        display1 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#FD1B1B"),"Player 1", button1);
        display2 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#4C7FFF"),"Player 2", button2);
        display3 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#FFF61B"),"Player 3", button3);
        display4 = super.getMyRenderer().makeCharacterChooseDisplay(Color.web("#1FCB17"),"Player 4", button4);
        super.getMyRoot().getChildren().addAll(bg,holder,button1,button2,button3,button4);
        holder.getChildren().addAll(myCharGrid,charBox);
        charBox.getChildren().addAll(display1,display2,display3,display4);
    }

    private void setCharacter(String player, String charName){
        if(player.equalsIgnoreCase("P1")){
            display1.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
        }
        else if(player.equalsIgnoreCase("P2")){
            display2.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
        }
        else if(player.equalsIgnoreCase("P3")){
            display3.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
        }
        else if(player.equalsIgnoreCase("P4")){
            display4.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
        }
    }


    private void getCharacter(DragToken token){
        myCharGrid.getCharacter(token);
    }

}
