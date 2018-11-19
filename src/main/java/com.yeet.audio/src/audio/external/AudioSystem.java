package audio.external;

import audio.Internal.MediaPlayer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.ActionEvent;
import messenger.external.EventBusFactory;

public class AudioSystem {

    private EventBus myMessageBus;
    private String path = "/example_character_1/";
    private MediaPlayer myPlayer;

    public AudioSystem(){
        myMessageBus = EventBusFactory.getEventBus();
        myPlayer= new MediaPlayer();
    }

    /**
     / This is the subscription to the ActionEvents that are posted by the inputsystem.
     */
    @Subscribe
    public void playAction(ActionEvent event){
        String newPath = path + event.getType()+"/"+event.getName()+".mp3";
        System.out.println(newPath);
        myPlayer.playMedia(newPath);
    }

    /**
     / Listens for the combat system to play the sound
     */
    public void playAction(SuccessfulEvent event){

    }

    /**
     / This is the subscription to the ActionEvents that are posted by the inputsystem.
     */
    public void gameOver(GameOverEvent gameOver){

    }
}
