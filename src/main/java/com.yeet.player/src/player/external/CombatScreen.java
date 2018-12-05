package player.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.external.InputSystem;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import messenger.external.*;
import physics.external.PhysicsSystem;
import physics.external.combatSystem.CombatSystem;
import player.internal.GameLoop;
import player.internal.SceneSwitch;
import player.internal.Screen;
import renderer.external.Renderer;
import renderer.external.Structures.Sprite;
import renderer.external.Structures.SpriteAnimation;
import xml.XMLParser;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/** Displays a stage and visualizes character combat animation
 *  @author bpx
 */
public class CombatScreen extends Screen {

    public static final double TILE_SIZE = 40.0;
    private EventBus myMessageBus;

    private SceneSwitch prevScene;
    private Consumer<Integer> nextScene;

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
    private HashMap<Integer, Sprite> mySpriteMap;
    private HashMap<Integer, HashMap<String,SpriteAnimation>> myAnimationMap;

    private HashMap<String, ArrayList<String>> myBackgroundMap;
    private HashMap<String,ArrayList<String>> myMusicMap;
    private HashMap<String, ArrayList<String>> myStageMap;
    private HashMap<String, ArrayList<String>> mySpawnMap;

    private ArrayList<ImageView> myTiles;

    public CombatScreen(Group root, Renderer renderer, File gameDirectory, SceneSwitch prevScene, Consumer<Integer> nextScene) {
        super(root, renderer);
        //set up message bus
        myMessageBus = EventBusFactory.getEventBus();
        myMessageBus.register(this);
        //set up scene links
        this.prevScene = prevScene;
        this.nextScene = nextScene;
        //set up resource managers
        myGameDirectory =  gameDirectory;
        myCharacterMap = new HashMap<>();
        myTileMap = new HashMap<>();
        mySpriteMap = new HashMap<>();
        myAnimationMap = new HashMap<>();
        myTiles = new ArrayList<>();
        super.setOnKeyPressed(event->myMessageBus.post(new KeyInputEvent(event.getCode())));
    }

    public void setupCombatScene(HashMap<Integer, String> characterNames, String stageName){
        myParser = new XMLParser(new File(myGameDirectory.getPath()+"/stages/"+stageName+"/stageproperties.xml"));
        myBackgroundMap = myParser.parseFileForElement("background");
        myMusicMap = myParser.parseFileForElement("music");
        myStageMap = myParser.parseFileForElement("map");
        mySpawnMap =  myParser.parseFileForElement("position");
        ImageView background = new ImageView(new Image(myGameDirectory.toURI()+"data/background/"+myBackgroundMap.get("bgFile").get(0)));
        background.setFitWidth(1280.0);
        background.setFitHeight(800.0);
        super.getMyRoot().getChildren().addAll(background);
        for(int i=0; i<myStageMap.get("x").size();i++){
            ImageView tile = new ImageView(new Image(myGameDirectory.toURI()+"/data/tiles/"+myStageMap.get("image").get(i)));
            tile.setFitHeight(TILE_SIZE);
            tile.setFitWidth(TILE_SIZE);
            tile.setLayoutX(Integer.parseInt(myStageMap.get("x").get(i))* TILE_SIZE);
            tile.setLayoutY(Integer.parseInt(myStageMap.get("y").get(i))* TILE_SIZE);
            myTiles.add(tile);
            myTileMap.put(i,new Rectangle2D.Double(Integer.parseInt(myStageMap.get("x").get(i))*TILE_SIZE,Integer.parseInt(myStageMap.get("y").get(i))*TILE_SIZE, TILE_SIZE, TILE_SIZE));
            super.getMyRoot().getChildren().add(tile);
        }
        for(int i=0;i<characterNames.keySet().size();i++){
            if(!characterNames.get(i).equals("")){
                //create sprites and set default viewport
                XMLParser spritePropertiesParser = new XMLParser(new File(myGameDirectory.getPath()+"/characters/"+characterNames.get(i)+"/sprites/spriteproperties.xml"));
                HashMap<String,ArrayList<String>> spriteProperties = spritePropertiesParser.parseFileForElement("sprite");
                Sprite sprite = new Sprite(new Image(myGameDirectory.toURI()+"/characters/"+characterNames.get(i)+"/sprites/spritesheet.png"),
                        Double.parseDouble(((spriteProperties.get("offsetX").get(0)))),Double.parseDouble(((spriteProperties.get("offsetY").get(0)))),
                        Double.parseDouble(((spriteProperties.get("width").get(0)))),Double.parseDouble(spriteProperties.get("height").get(0)));
                sprite.setLayoutX(Integer.parseInt(mySpawnMap.get("xPos").get(i))*TILE_SIZE);
                sprite.setLayoutY(Integer.parseInt(mySpawnMap.get("yPos").get(i))*TILE_SIZE);
                mySpriteMap.put(i,sprite);
                myCharacterMap.put(i,new Point2D.Double(Integer.parseInt(mySpawnMap.get("xPos").get(i))*TILE_SIZE,Integer.parseInt(mySpawnMap.get("yPos").get(i))*TILE_SIZE));
                super.getMyRoot().getChildren().add(sprite);
                //set up animations for the sprite
                XMLParser animationPropertiesParser = new XMLParser(new File(myGameDirectory.getPath()+"/characters/"+characterNames.get(i)+"/sprites/animationproperties.xml"));
                HashMap<String, ArrayList<String>> animationInfo = animationPropertiesParser.parseFileForElement("animation");
                myAnimationMap.put(i,new HashMap<>());
                for(int j = 0; j<animationInfo.get("name").size(); j++){
                    Duration duration = Duration.seconds(Double.parseDouble(animationInfo.get("duration").get(j)));
                    String name = animationInfo.get("name").get(j);
                    int count = Integer.parseInt(animationInfo.get("count").get(j));
                    int columns = Integer.parseInt(animationInfo.get("columns").get(j));
                    double offsetX = Double.parseDouble(animationInfo.get("offsetX").get(j));
                    double offsetY = Double.parseDouble(animationInfo.get("offsetY").get(j));
                    double width = Double.parseDouble(animationInfo.get("width").get(j));
                    double height = Double.parseDouble(animationInfo.get("height").get(j));
                    SpriteAnimation animation = new SpriteAnimation(sprite,duration,count,columns,offsetX,offsetY,width,height);
                    myAnimationMap.get(i).put(name,animation);
                }
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
            if(directionsMap.get(i)==0){
                // face right
                mySpriteMap.get(i).setScaleX(-1);
            }
            else if(directionsMap.get(i)==Math.PI){
                //face left
                mySpriteMap.get(i).setScaleX(1);
            }
            /*System.out.println("Retrieving state for player: "+i);
            if(myCombatSystem.getPlayerState(i).equals(PlayerState.INITIAL)){
                mySpriteMap.get(i).defaultViewport();
            }*/
        }
    }

    @Subscribe
    public void showAttackAnimation(AttackSuccessfulEvent attackSuccessfulEvent){
        int id = attackSuccessfulEvent.getInitiatorID();
        myAnimationMap.get(id).get("special").play();
    }

    @Subscribe
    public void showMoveAnimation(MoveSuccessfulEvent moveSuccessfulEvent){
        int id = moveSuccessfulEvent.getInitiatorID();
        myAnimationMap.get(id).get("run").play();
    }

    @Subscribe
    public void endCombat(GameOverEvent gameOverEvent){
        Platform.runLater(
                () -> {
                    nextScene.accept(gameOverEvent.getWinnerID());
                }
        );
    }
}
