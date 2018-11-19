package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

/** Algorithmically creates a grid of characters based on number of directories available
 *  @author bpx
 */
public class CharacterGrid extends VBox {

    public static final int THUMB_WIDTH = 132;
    public static final int THUMB_HEIGHT = 95;
    public static final int TEXT_HEIGHT = 10;

    private HashMap<ImageView,String> myImageMap;

    private BiConsumer<String,String> myCharacterConsumer;

    /** Creates a new {@code CharacterGrid} using the specified parameters
     *  @param directory The game directory
     *  @param charactersPerRow Number of thumbnails to show per row
     *  @param text The text base to use when labelling character thumbnails
     *  @param characterConsumer Lambda that will use the name of the character chosen
     *  */
    public CharacterGrid(File directory, int charactersPerRow, Text text, BiConsumer<String,String> characterConsumer){
        super(1.0);
        super.setAlignment(Pos.CENTER);
        super.setMinSize(1280,382);
        super.setStyle("-fx-background-color: #201D20;");
        myImageMap = new HashMap<>();
        myCharacterConsumer = characterConsumer;
        int charcount = 0;
        ArrayList<File> files  = new ArrayList<>();
        for(File f : new File(directory.getPath()+"\\characters").listFiles()){
            if(!f.getName().contains(".")){
                charcount++;
                files.add(f);
            }
        }
        int rowcount = (int)Math.ceil(charcount/(double)charactersPerRow);
        for(int i = 0;i<rowcount;i++){
            HBox row = new HBox(1.0);
            row.setPrefSize(1280,THUMB_HEIGHT+ TEXT_HEIGHT);
            row.setAlignment(Pos.CENTER);
            for(int j = 0; j < charactersPerRow; j++){
                if((charactersPerRow*(i))+j+1>charcount){
                    break;
                }
                else{
                    ImageView portrait = new ImageView(new Image(String.format("%s/%s",files.get((charactersPerRow*(i))+j).toURI(),"portrait.png")));
                    portrait.setPreserveRatio(true);
                    portrait.setFitWidth(132);
                    portrait.setViewport(new Rectangle2D(95,106, THUMB_WIDTH, THUMB_HEIGHT));
                    myImageMap.put(portrait,files.get((charactersPerRow*(i))+j).getName());
                    Text label = new Text(files.get((charactersPerRow*(i))+j).getName());
                    label.setFont(text.getFont());
                    label.setFill(Color.WHITE);
                    label.setStyle("-fx-font-size: 15");
                    System.out.println("making label: "+files.get((charactersPerRow*(i))+j).getName());
                    VBox portraitHolder = new VBox(1.0);
                    portraitHolder.setPrefSize(THUMB_WIDTH,THUMB_HEIGHT+TEXT_HEIGHT);
                    portraitHolder.setAlignment(Pos.BOTTOM_CENTER);
                    portraitHolder.getChildren().addAll(portrait,label);
                    row.getChildren().add(portraitHolder);
                }
            }
            super.getChildren().add(row);
        }
    }

    /** Checks whether the given {@code DragToken} intersects any portraits and activates lambda if true
     *  @param dragToken The {@code DragToken} to check intersections for
     */
    public void getCharacter(DragToken dragToken){
        for(ImageView img : myImageMap.keySet()){
            if(dragToken.isInside(new Rectangle2D(img.localToScene(img.getBoundsInLocal()).getMinX(),img.localToScene(img.getBoundsInLocal()).getMinY(),img.localToScene(img.getBoundsInLocal()).getWidth(),img.localToScene(img.getBoundsInLocal()).getHeight()))){
                myCharacterConsumer.accept(dragToken.getLabel(),myImageMap.get(img));
                return;
            }
        }
        myCharacterConsumer.accept(dragToken.getLabel(),null);
    }


}
