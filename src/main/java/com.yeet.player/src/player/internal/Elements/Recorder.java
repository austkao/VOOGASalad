package player.internal.Elements;

import com.google.common.eventbus.Subscribe;
import messenger.external.Event;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/** Creates and plays back {@code Replay} objects, and can write them to file
 *  @author bpx
 */
public class Recorder {

    private Replay myActiveReplay;
    private boolean isRecording;

    public Recorder(){
        myActiveReplay = new Replay();
        isRecording = false;
    }

    /** Starts a new recording of events */
    public void record(){
        myActiveReplay = new Replay();
        isRecording = true;
    }

    /** Stops recording events */
    public void stop(){
        isRecording = false;
    }

    /** Saves the active replay */
    public void save(){
        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("replay.yeet");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(myActiveReplay);

            out.close();
            file.close();

        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
    }

    @Subscribe
    public void getEvent(Event event){
        if(isRecording){
            myActiveReplay.add(event);
        }
    }




}
