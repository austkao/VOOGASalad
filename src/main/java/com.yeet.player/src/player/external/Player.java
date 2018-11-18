package player.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.scene.Group;
import javafx.stage.Stage;
import messenger.external.EventBusFactory;
import messenger.external.TestSuccesfulEvent;
import player.internal.*;
import renderer.external.Renderer;

import java.io.File;

/** Visualizer for game data and gameplay
 *  @author bpx
 */
public class Player {

    private EventBus myMessageBus;
    private Stage myStage;
    private Renderer myRenderer;

    private LoadingScreen myLoadingScreen;
    private SplashScreen mySplashScreen;
    private MainMenuScreen myMainMenuScreen;
    private CharacterSelectScreen myCharacterSelectScreen;
    private MatchRulesScreen myMatchRulesScreen;
    private CombatScreen myCombatScreen;
    private CombatResultsScreen myCombatResultsScreen;

    private File myDirectory;

    public Player(Stage stage, File gameDirectory, Renderer renderer){
        myRenderer = renderer;
        myMessageBus = EventBusFactory.getEventBus();
        myStage = stage;
        myDirectory = gameDirectory;
        //create loading screen
        myLoadingScreen = new LoadingScreen(new Group(),myRenderer);
    }

    /** Gives control of the {@code Stage} to the {@code Player} and begins sub-screen loading*/
    public void start(){
        myStage.setScene(myLoadingScreen);
        //pre-load all other screens
        mySplashScreen = new SplashScreen(new Group(), myRenderer, myDirectory, () -> myStage.setScene(myMainMenuScreen));
        myMainMenuScreen = new MainMenuScreen(new Group(), myRenderer, ()->myStage.setScene(myCharacterSelectScreen));
        myCharacterSelectScreen = new CharacterSelectScreen(new Group(), myRenderer, myDirectory);
        myMatchRulesScreen = new MatchRulesScreen(new Group(), myRenderer);
        myCombatScreen =  new CombatScreen(new Group(),myRenderer);
        myCombatResultsScreen = new CombatResultsScreen(new Group(),myRenderer);
        //finished loading
        System.out.println("finished loading!");
        myStage.setScene(mySplashScreen);
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

    @Subscribe
    public  void printMessage(TestSuccesfulEvent testSuccessfulEvent){
        // Simulate sending reciept
        System.out.println("Player: Test successful!");
    }


}
