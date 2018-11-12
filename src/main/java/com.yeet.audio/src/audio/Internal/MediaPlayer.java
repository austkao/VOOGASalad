package audio.Internal;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class MediaPlayer {

    public void playMedia(String path){
        URL resource = getClass().getResource(path);
        System.out.println(resource);
        AudioClip clip = new AudioClip(resource.toString());
        clip.play(1.0);

    }
}
