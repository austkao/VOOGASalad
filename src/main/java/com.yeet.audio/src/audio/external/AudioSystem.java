package audio.external;

import audio.Internal.Player;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.ActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.GameOverEvent;
import messenger.external.SuccessfulEvent;

import java.io.File;

public class AudioSystem {

    private EventBus myMessageBus;
    private String path;
    private Player myPlayer;
    private File GameDir;

    public AudioSystem(File GameDirectory){
        GameDir = GameDirectory;
        setRootPath();
        myMessageBus = EventBusFactory.getEventBus();
        myPlayer= new Player();
    }

    /**
     / This is the subscription to the ActionEvents that are posted by the inputsystem.
     */
    @Subscribe
    public void playAction(ActionEvent event) {
        //String newPath = path + event.getType()+"/"+event.getName()+".mp3";
        //String newPath = path + "/characters/Lucina1/sounds/" + event.getName() +".mp3";
        String newPath = path + "/characters/Lucina1/sounds/" + "JAB.mp3";

        System.out.println(newPath);
        myPlayer.playMedia(newPath);
    }

    /**
     / Listens for the combat system to play the sound
     */
    @Subscribe
    public void playAction(SuccessfulEvent event){
        String newPath = path + "/characters/Lucina1/sounds/" + "JAB.mp3";

        System.out.println(newPath);
        myPlayer.playMedia(newPath);
    }

    //@Subscribe
    //public void playGameMusic(GameStartEvent event){
    //    String newPath = path + "stages/example_stage_1/bgm/BGM.mp3";
    //}

    /**
     / This is the subscription to the ActionEvents that are posted by the inputsystem.
     */
    public void gameOver(GameOverEvent gameOver){

    }

    private void setRootPath(){
        path = GameDir.getPath();
    }
}
