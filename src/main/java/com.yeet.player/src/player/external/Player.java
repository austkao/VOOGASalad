package player.external;

import audio.external.AudioSystem;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import messenger.external.*;
import messenger.external.ReplayUtils.ReplayUtilities;
import player.internal.*;
import renderer.external.Renderer;

import java.io.File;
import java.util.function.Consumer;

/** Visualizer for game data and gameplay
 *  @author bpx
 */
public class Player {

    public static final String SELECT_SE = "src/main/java/com.yeet.player/resources/select.mp3";
    private EventBus myMessageBus;
    private Stage myStage;
    private Renderer myRenderer;
    private Scene originalScene;

    private Consumer<Scene> myEditorLink;

    private MediaPlayer mySEPlayer;

    private LoadingScreen myLoadingScreen;
    private SplashScreen mySplashScreen;
    private MainMenuScreen myMainMenuScreen;
    private SettingsScreen mySettingsScreen;
    private SoundsSettingsScreen mySoundsSettingsScreen;
    private CharacterSelectScreen myCharacterSelectScreen;
    private MatchRulesScreen myMatchRulesScreen;
    private StageSelectScreen myStageSelectScreen;
    private CombatScreen myCombatScreen;
    private CombatResultsScreen myCombatResultsScreen;

    private File myDirectory;

    public Player(Stage stage, File gameDirectory, Renderer renderer){
        myRenderer = renderer;
        myMessageBus = EventBusFactory.getEventBus();
        myStage = stage;
        myDirectory = gameDirectory;
        mySEPlayer = new MediaPlayer(new Media(new File(SELECT_SE).toURI().toString()));
        //create loading screen
        myLoadingScreen = new LoadingScreen(new Group(),myRenderer);
        AudioSystem myAudioSystem = new AudioSystem(gameDirectory);
        myMessageBus.register(myAudioSystem);
    }

    /** Gives control of the {@code Stage} to the {@code Player} and begins sub-screen loading*/
    public void start(){
        originalScene = myStage.getScene();
        myStage.setScene(myLoadingScreen);
        //pre-load all other screens
        mySplashScreen = new SplashScreen(new Group(), myRenderer, myDirectory, () -> {
            myStage.setScene(myMainMenuScreen);
            myMessageBus.post(new MenuStartEvent());
        });
        mySettingsScreen = new SettingsScreen(new Group(),myRenderer, mySEPlayer,()->myStage.setScene(myMainMenuScreen),()->myStage.setScene(mySoundsSettingsScreen),()->myEditorLink.accept(mySettingsScreen));
        myMainMenuScreen = new MainMenuScreen(new Group(), myRenderer, mySEPlayer,()-> {
            myMessageBus.post(new ExitMenuEvent());
            myMessageBus.post(new FightStartEvent()); // FightStartEvent is the character select screen!
            myStage.setScene(myCharacterSelectScreen);

        },()-> {
            myMessageBus.post(new ExitMenuEvent());
            myStage.setScene(originalScene);
        },()-> {
            myStage.setScene(mySettingsScreen);
        });
        mySoundsSettingsScreen = new SoundsSettingsScreen(myDirectory,new Group(),myRenderer,()->myStage.setScene(mySettingsScreen),(volume)->mySEPlayer.setVolume(volume));
        myCharacterSelectScreen = new CharacterSelectScreen(new Group(), myRenderer, myDirectory, ()-> {
            myStage.setScene(myMainMenuScreen);
            myMessageBus.post(new FightEndEvent());
            myMessageBus.post(new MenuStartEvent());
        },()-> {
            myStage.setScene(myStageSelectScreen);
        });
        myMatchRulesScreen = new MatchRulesScreen(new Group(), myRenderer);
        myStageSelectScreen = new StageSelectScreen(new Group(),myRenderer,myDirectory,()-> {
            myStage.setScene(myCharacterSelectScreen);
        },()-> {
            myMessageBus.post(new FightEndEvent());
            myStage.setScene(myLoadingScreen);
            myCombatScreen.setupCombatScene(myCharacterSelectScreen.getCharacterMap(), myCharacterSelectScreen.getColorMap(),myCharacterSelectScreen.getGamemode(),myCharacterSelectScreen.getTypeValue(),myCharacterSelectScreen.getBots(),myStageSelectScreen.getStage());
            myStage.setScene(myCombatScreen);
            myCombatScreen.startLoop();
        });
        myCombatScreen =  new CombatScreen(new Group(),myRenderer,myDirectory,()->myStage.setScene(myCharacterSelectScreen),(winnerID,rankList)-> {
            //setup combat results screen
            myMessageBus.post(new MenuStartEvent());
            myCombatResultsScreen.setWinner(myCharacterSelectScreen.getCharacterList(),rankList, myCharacterSelectScreen.getCharacterChooserList());
            myStage.setScene(myCombatResultsScreen);
        });
        myCombatResultsScreen = new CombatResultsScreen(new Group(),myRenderer,()-> {
            myMessageBus.post(new ExitMenuEvent());
            myMessageBus.post(new FightStartEvent());
            myStage.setScene(myCharacterSelectScreen);
        });
        //finished loading
        myStage.setScene(mySplashScreen);
        System.out.println(ReplayUtilities.getCurrentTimeUsingCalendar());
    }

    /** Sets the location of the active game directory to load files from
     *  @param directory The new game directory
     */
    public void setDirectory(File directory){
        myDirectory = directory;
    }

    /** Test */
    public void doSomething(){
        TestSuccesfulEvent testSuccesfulEvent = new TestSuccesfulEvent();
        myMessageBus.post(testSuccesfulEvent);
    }

    public void setEditorLink(Consumer<Scene> editorScene){
        myEditorLink = editorScene;
    }

    @Subscribe
    public  void printMessage(TestSuccesfulEvent testSuccessfulEvent){
        // Simulate sending reciept
        System.out.println("Player: Test successful!");
    }


}
