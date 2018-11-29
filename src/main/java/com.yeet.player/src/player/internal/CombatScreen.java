package player.internal;

import com.google.common.eventbus.Subscribe;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import messenger.external.PositionsUpdateEvent;
import renderer.external.Renderer;
import renderer.external.Structures.Sprite;
import xml.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/** Displays a stage and visualizes character combat animation
 *  @author bpx
 */
public class CombatScreen extends Screen {

    private XMLParser myParser;

    private File myGameDirectory;

    private HashMap<String, ArrayList<String>> myStageMap;
    private HashMap<String, ArrayList<String>> mySpawnMap;
    private HashMap<Integer, Sprite> mySpriteMap;
    private ArrayList<ImageView> myTiles;

    public CombatScreen(Group root, Renderer renderer, File gameDirectory, String stageName, Consumer<KeyCode> keyConsumer) {
        super(root, renderer);
        myParser = new XMLParser(new File(gameDirectory.getPath()+"/stages/"+stageName+"/stageproperties.xml"));
        myGameDirectory =  gameDirectory;
        myStageMap = myParser.parseFileForElement("map");
        mySpawnMap =  myParser.parseFileForElement("position");
        mySpriteMap = new HashMap<>();
        myTiles = new ArrayList<>();
        ImageView background = new ImageView(new Image(gameDirectory.toURI()+"/data/background/background.png"));
        background.setFitWidth(1280.0);
        background.setFitHeight(800.0);
        super.getMyRoot().getChildren().addAll(background);
        for(int i=0; i<myStageMap.get("x").size();i++){
            ImageView tile = new ImageView(new Image(gameDirectory.toURI()+"/data/tiles/"+myStageMap.get("image").get(i)));
            tile.setFitHeight(40.0);
            tile.setFitWidth(40.0);
            tile.setLayoutX(Integer.parseInt(myStageMap.get("x").get(i))*40.0);
            tile.setLayoutY(Integer.parseInt(myStageMap.get("y").get(i))*40.0);
            myTiles.add(tile);
            super.getMyRoot().getChildren().add(tile);
        }
        super.setOnKeyPressed(event -> keyConsumer.accept(event.getCode()));
    }

    public void setCharacters(HashMap<Integer, String> characterNames){
        for(int i=0;i<characterNames.keySet().size();i++){
            if(!characterNames.get(i).equals("")){
                XMLParser propertiesParser = new XMLParser(new File(myGameDirectory.getPath()+"/characters/"+characterNames.get(i)+"/sprites/spriteproperties.xml"));
                HashMap<String,ArrayList<String>> spriteProperties = propertiesParser.parseFileForElement("sprite");
                Sprite sprite = new Sprite(new Image(myGameDirectory.toURI()+"/characters/"+characterNames.get(i)+"/sprites/spritesheet.png"),Double.parseDouble(((spriteProperties.get("width").get(0)))),Double.parseDouble(spriteProperties.get("height").get(0)));
                sprite.setLayoutX(Integer.parseInt(mySpawnMap.get("xPos").get(i))*40.0);
                sprite.setLayoutY(Integer.parseInt(mySpawnMap.get("yPos").get(i))*40.0);
                mySpriteMap.put(i,sprite);
                super.getMyRoot().getChildren().add(sprite);
                //super.getMyRoot().getChildren().add(new ImageView(new Image()))
            }
        }
    }

    @Subscribe
    private void update(PositionsUpdateEvent event){
        for(Integer i : event.getPositions().keySet()){
            mySpriteMap.get(i).setLayoutX(event.getPositions().get(i).getX());
            mySpriteMap.get(i).setLayoutY(event.getPositions().get(i).getY());
        }
    }
}
