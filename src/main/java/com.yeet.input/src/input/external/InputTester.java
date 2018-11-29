package input.external;

import audio.external.AudioSystem;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import messenger.external.ActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.KeyInputEvent;

import java.io.File;

public class InputTester extends Application {

    EventBus myMessageBus;
    public InputTester(){
        myMessageBus = EventBusFactory.getEventBus();
    }

    /**
     *
     * Tester for catching events
     */
    @Subscribe
    public void printAction(ActionEvent ae){
        System.out.println(ae.getName());
    }

    public void doSomething() throws InterruptedException {
        //NORMALLY, this would be called by the front-end
        KeyInputEvent test = new KeyInputEvent(KeyCode.A);
        myMessageBus.post(test);

        //Sleeping to assure that the combo works
        Thread.sleep(2000);

        KeyInputEvent test2 = new KeyInputEvent(KeyCode.B);
        myMessageBus.post(test2);
        //KeyInputEvent test3 = new KeyInputEvent(KeyCode.S);
        //myMessageBus.post(test3);
        //KeyInputEvent test4 = new KeyInputEvent(KeyCode.D);
        //myMessageBus.post(test4);
    }


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String gameDir = "/Users/josesanmartin/Desktop/CompSci308/VoogaSalad/voogasalad_yeet/src/main/java/com.yeet.main/resources/examplegame";
        File GameDir = new File(gameDir);
        InputSystem IS = new InputSystem(GameDir);
        InputTester IT = new InputTester();
        AudioSystem AS = new AudioSystem(GameDir);
        EventBusFactory.getEventBus().register(AS);
        EventBusFactory.getEventBus().register(IS);
        EventBusFactory.getEventBus().register(IT);
        IT.doSomething();
    }
}
