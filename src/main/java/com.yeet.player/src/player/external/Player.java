package player.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import messenger.external.EventBusFactory;
import messenger.external.TestSuccesfulEvent;
import player.internal.*;
import renderer.external.Renderer;

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

    private Image myLoadingImage;
    private Image mySplashImage;

    public Player(Stage stage, Renderer renderer){
        myRenderer = renderer;
        myMessageBus = EventBusFactory.getEventBus();
        myStage = stage;
        //create loading screen
        myLoadingScreen = new LoadingScreen(new Group(),myRenderer);
    }

    public void start(){
        myStage.setScene(myLoadingScreen);
        //pre-load all other screens
        mySplashScreen = new SplashScreen(new Group(),myRenderer);
        myMainMenuScreen = new MainMenuScreen(new Group(), myRenderer);
        myCharacterSelectScreen = new CharacterSelectScreen(new Group(), myRenderer);
        myMatchRulesScreen = new MatchRulesScreen(new Group(), myRenderer);
        myCombatScreen =  new CombatScreen(new Group(),myRenderer);
        myCombatResultsScreen = new CombatResultsScreen(new Group(),myRenderer);
        //finished loading
        //myStage.setScene(mySplashScreen);
    }

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
