package audio.Internal;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Player {

    public void playMedia(String path)  {
        File file = new File( path);
        //URL resource = getClass().getResource(path);
        //AudioClip clip = new AudioClip(resource.toString());
        //System.out.println(file.canRead());
        //AudioClip clip = new AudioClip(file.toURI().toString());
        //clip.play(10);

        Media sound = new Media( file.toURI().toString());
        Media sound2 = new Media("file://" + path);
        MediaPlayer mediaPlayer = new MediaPlayer(sound2);
        mediaPlayer.setVolume(10);
        mediaPlayer.play();

    }
}
