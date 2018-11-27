package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import player.internal.Elements.CharacterChooseDisplay;
import player.internal.Elements.CharacterGrid;
import player.internal.Elements.DragToken;
import player.internal.Elements.MenuTopper;
import renderer.external.Renderer;

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


    /** Creates a new {@code CharacterSelectScreen} with the specified parameters
     *  @param root The {@code Group} to instantiate the internal {@code Scene} using
     *  @param renderer The {@code Renderer} to use to generate graphics
     *  @param gameDirectory The directory where the game files are located
     *  @param previousScene {@code SceneSwitch} lambda to go back to previous screen
     *  @param nextScene {@code SceneSwitch} lambda to progress to next screen
     */
    public CharacterSelectScreen(Group root, Renderer renderer, File gameDirectory, SceneSwitch previousScene, SceneSwitch nextScene) {
        super(root, renderer);
        super.setFill(Color.WHITE);
        myDirectory = gameDirectory;
        VBox holder = new VBox(0.0);
        holder.setPrefSize(1280,800);
        holder.setAlignment(Pos.BOTTOM_CENTER);
        ImageView bg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("characterselect_bg.jpg")));
        bg.setFitWidth(1280);
        bg.setFitHeight(800);
        bg.setOpacity(0.52);
        HBox menuBlock = new MenuTopper(30.0,previousScene);
        myCharGrid = new CharacterGrid(myDirectory,CHAR_PER_ROW, super.getMyRenderer().makeText("",true,15,Color.WHITE,0.0,0.0), this::setCharacter);
        Rectangle spacer = new Rectangle(1280,10,Color.TRANSPARENT);
        HBox charBox = new HBox(10);
        charBox.setMaxHeight(332.0);
        charBox.setAlignment(Pos.CENTER);
        DragToken button1 = new DragToken(super.getMyRenderer().makeText("P1",true,40,Color.WHITE,0.0,0.0),Color.web("#FD1B1B"),130,548,40, this::getCharacter);
        DragToken button2 = new DragToken(super.getMyRenderer().makeText("P2",true,40,Color.WHITE,0.0,0.0),Color.web("#4C7FFF"),439,548,40, this::getCharacter);
        DragToken button3 = new DragToken(super.getMyRenderer().makeText("P3",true,40,Color.WHITE,0.0,0.0),Color.web("#FFF61B"),752,548,40, this::getCharacter);
        DragToken button4 = new DragToken(super.getMyRenderer().makeText("P4",true,40,Color.WHITE,0.0,0.0),Color.web("#1FCB17"),1079,548,40, this::getCharacter);
        display1 = new CharacterChooseDisplay(Color.web("#FD1B1B"),super.getMyRenderer().makeText("Player 1",false,40,Color.BLACK,0.0,0.0),super.getMyRenderer().makeText("",true,60,Color.WHITE,0.0,0.0), button1);
        display2 = new CharacterChooseDisplay(Color.web("#4C7FFF"),super.getMyRenderer().makeText("Player 2",false,40,Color.BLACK,0.0,0.0),super.getMyRenderer().makeText("",true,60,Color.WHITE,0.0,0.0), button2);
        display3 = new CharacterChooseDisplay(Color.web("#FFF61B"),super.getMyRenderer().makeText("Player 3",false,40,Color.BLACK,0.0,0.0),super.getMyRenderer().makeText("",true,60,Color.WHITE,0.0,0.0), button3);
        display4 = new CharacterChooseDisplay(Color.web("#1FCB17"),super.getMyRenderer().makeText("Player 4",false,40,Color.BLACK,0.0,0.0),super.getMyRenderer().makeText("",true,60,Color.WHITE,0.0,0.0), button4);
        super.getMyRoot().getChildren().addAll(bg,holder,button1,button2,button3,button4);
        holder.getChildren().addAll(menuBlock,myCharGrid,spacer,charBox);
        charBox.getChildren().addAll(display1,display2,display3,display4);
    }

    /** Sets a specific player's character based on name
     *  @param player The player to set, can be P1, P2, P3, or P4
     *  @param charName The name of the character's data directory under the characters folder
     */
    private void setCharacter(String player, String charName){
        if(player.equalsIgnoreCase("P1")){
            display1.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
            display1.setCharacterName(charName);
        }
        else if(player.equalsIgnoreCase("P2")){
            display2.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
            display2.setCharacterName(charName);
        }
        else if(player.equalsIgnoreCase("P3")){
            display3.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
            display3.setCharacterName(charName);
        }
        else if(player.equalsIgnoreCase("P4")){
            display4.setPortrait(new Image(myDirectory.toURI()+"\\characters\\"+charName+"\\portrait.png"));
            display4.setCharacterName(charName);
        }
    }


    /** Uses the {@code CharacterGrid} to identify the target of the {@code DragToken}
     *  @param token The {@code DragToken} to use
     */
    private void getCharacter(DragToken token){
        myCharGrid.getCharacter(token);
    }

}