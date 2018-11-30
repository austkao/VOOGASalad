package player.external;

import com.google.common.eventbus.EventBus;
import input.external.InputSystem;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import messenger.external.EventBusFactory;
import messenger.external.KeyInputEvent;
import physics.external.PhysicsSystem;
import physics.external.combatSystem.CombatSystem;
import player.internal.GameLoop;
import player.internal.Screen;
import renderer.external.Renderer;
import renderer.external.Structures.Sprite;
import xml.XMLParser;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Displays a stage and visualizes character combat animation
 *  @author bpx
 */
public class CombatScreen extends Screen {

    private EventBus myMessageBus;

    private InputSystem myInputSystem;
    private CombatSystem myCombatSystem;
    private PhysicsSystem myPhysicsSystem;
    private GameLoop myGameLoop;

    private XMLParser myParser;

    private File myGameDirectory;

    private String myStageName;

    private MediaPlayer myBGMPlayer;



    private HashMap<Integer, Point2D> myCharacterMap;
    private HashMap<Integer, Rectangle2D> myTileMap;
    private HashMap<String, ArrayList<String>> myBackgroundMap;
    private HashMap<String,ArrayList<String>> myMusicMap;
    private HashMap<String, ArrayList<String>> myStageMap;
    private HashMap<String, ArrayList<String>> mySpawnMap;
    private HashMap<Integer, Sprite> mySpriteMap;
    private ArrayList<ImageView> myTiles;

    public CombatScreen(Group root, Renderer renderer, File gameDirectory, String stageName) {
        super(root, renderer);
        //set up message bus
        myMessageBus = EventBusFactory.getEventBus();
        myMessageBus.register(this);
        //set up resource managers
        myParser = new XMLParser(new File(gameDirectory.getPath()+"/stages/"+stageName+"/stageproperties.xml"));
        myGameDirectory =  gameDirectory;
        myStageName = stageName;
        myCharacterMap = new HashMap<>();
        myTileMap = new HashMap<>();
        myBackgroundMap = myParser.parseFileForElement("background");
        myMusicMap = myParser.parseFileForElement("music");
        myStageMap = myParser.parseFileForElement("map");
        mySpawnMap =  myParser.parseFileForElement("position");
        mySpriteMap = new HashMap<>();
        myTiles = new ArrayList<>();
        //set up visuals
        ImageView background = new ImageView(new Image(gameDirectory.toURI()+"data/background/"+myBackgroundMap.get("bgFile").get(0)));
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
            myTileMap.put(i,new Rectangle2D.Double(Integer.parseInt(myStageMap.get("x").get(i))*40.0,Integer.parseInt(myStageMap.get("y").get(i))*40.0, 40.0, 40.0));
            super.getMyRoot().getChildren().add(tile);
        }
        super.setOnKeyPressed(event->myMessageBus.post(new KeyInputEvent(event.getCode())));
    }

    public void setupCombatScene(HashMap<Integer, String> characterNames){
        for(int i=0;i<characterNames.keySet().size();i++){
            if(!characterNames.get(i).equals("")){
                System.out.println(characterNames.get(i));
                XMLParser propertiesParser = new XMLParser(new File(myGameDirectory.getPath()+"/characters/"+characterNames.get(i)+"/sprites/spriteproperties.xml"));
                HashMap<String,ArrayList<String>> spriteProperties = propertiesParser.parseFileForElement("sprite");
                Sprite sprite = new Sprite(new Image(myGameDirectory.toURI()+"/characters/"+characterNames.get(i)+"/sprites/spritesheet.png"),Double.parseDouble(((spriteProperties.get("width").get(0)))),Double.parseDouble(spriteProperties.get("height").get(0)));
                sprite.setLayoutX(Integer.parseInt(mySpawnMap.get("xPos").get(i))*40.0);
                sprite.setLayoutY(Integer.parseInt(mySpawnMap.get("yPos").get(i))*40.0);
                mySpriteMap.put(i,sprite);
                myCharacterMap.put(i,new Point2D.Double(Integer.parseInt(mySpawnMap.get("xPos").get(i))*40.0,Integer.parseInt(mySpawnMap.get("yPos").get(i))*40.0));
                super.getMyRoot().getChildren().add(sprite);
            }
        }
        //set up combat systems
        myInputSystem = new InputSystem(myGameDirectory);
        myMessageBus.register(myInputSystem);
        myPhysicsSystem = new PhysicsSystem();
        myGameLoop = new GameLoop(myPhysicsSystem,this);
        myMessageBus.register(myPhysicsSystem);
        myCombatSystem = new CombatSystem(getCharacterMap(),getTileMap(),myPhysicsSystem);
        myMessageBus.register(myCombatSystem);
        //music and audio
        myBGMPlayer = new MediaPlayer(new Media(new File(myGameDirectory.getPath()+"/data/bgm/"+myMusicMap.get("mFile").get(0)).toURI().toString()));
        myBGMPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        myBGMPlayer.play();
    }

    public void startLoop(){
        myGameLoop.startLoop();
    }

    public void stopLoop(){
        //TODO: stops game loop
    }

    public HashMap<Integer, Point2D> getCharacterMap(){
        return (HashMap<Integer, Point2D>) myCharacterMap.clone();
    }

    public HashMap<Integer, Rectangle2D> getTileMap() {
        return myTileMap;
    }

    public void update(Map<Integer, Point2D> characterMap, Map<Integer, Double> directionsMap){
        for(int i=0;i<mySpriteMap.keySet().size();i++){
            mySpriteMap.get(i).setLayoutX(characterMap.get(i).getX());
            mySpriteMap.get(i).setLayoutY(characterMap.get(i).getY());
            //TODO: set direction of sprites
        }
    }
}
