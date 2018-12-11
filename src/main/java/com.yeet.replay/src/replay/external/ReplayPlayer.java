package replay.external;

import com.google.common.eventbus.EventBus;
import messenger.external.Event;
import replay.internal.Frame;
import replay.internal.Replay;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Timer;
import java.util.TimerTask;

/** Tool for loading serialized {@code Replay} objects and playing them back
 *  @author bpx
 */
public class ReplayPlayer {

    private EventBus myEventBus;

    private Replay loadedReplay;

    private Timer myTimer;

    private int currentFrame;
    private Event[] eventList;

    public ReplayPlayer(EventBus eventBus){
        myEventBus = eventBus;
        myTimer = new Timer();
        currentFrame = 0;
        try{
            myTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run(){
                   advanceReplay();
                }
            }, (long) 0, (long) 1);
            myTimer.wait();
        }
        catch(InterruptedException e){

        }

    }

    private void advanceReplay(){
        try{
            myEventBus.post(eventList[currentFrame]);
            currentFrame++;
        }
        catch(IndexOutOfBoundsException e){
            try {
                myTimer.wait();
            } catch (InterruptedException e1) {
                myTimer.cancel();
            }
        }

    }

    /** Loads a replay from a {@code File}, throws {@code replay.external.InvalidReplayFileException} if an error occurs */
    public void load(File replayFile) throws InvalidReplayFileException {
        Replay replay;
        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(replayFile);
            ObjectInputStream in = new ObjectInputStream(file);
            // Method for deserialization of object
            replay = (Replay)in.readObject();
            in.close();
            file.close();
            loadedReplay = replay;
        }
        catch(IOException | ClassNotFoundException | ClassCastException ex)
        {
            throw new InvalidReplayFileException(ex.getMessage());
        }
        //convert replay file into usable
        Long length = loadedReplay.getFrameList().get(loadedReplay.getFrameList().size()-1).getTime();
        eventList = new Event[length.intValue()];
        for(Frame frame : loadedReplay.getFrameList()){
            Long time = frame.getTime();
            eventList[time.intValue()] = frame.getEvent();
        }
    }

    public void play(){

    }
}
