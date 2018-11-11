package player.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.stage.Stage;
import messenger.external.EventBusFactory;
import messenger.external.TestSuccesfulEvent;
import renderer.external.Renderer;

/** Visualizer for game data and gameplay
 *  @author bpx
 */
public class Player {

    private EventBus myMessageBus;
    private Stage myStage;
    private Renderer myRenderer;

    public Player(Stage stage, Renderer renderer){
        myRenderer = renderer;
        myMessageBus = EventBusFactory.getEventBus();
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
