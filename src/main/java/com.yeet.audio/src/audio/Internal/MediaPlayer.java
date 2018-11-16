package audio.Internal;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class MediaPlayer {

    public void playMedia(String path){
        URL resource = getClass().getResource(path);
        AudioClip clip = new AudioClip(resource.toString());
        clip.play(5.0);
        System.out.println("hey");

    }
}
