package replay.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.Event;
import replay.internal.Replay;
import replay.internal.ReplayUtilities;

import java.io.*;

/** Creates and plays back {@code replay.internal.Replay} objects, and can write them to file
 *  @author bpx
 */
public class Recorder {
    private EventBus myEventBus;

    private File myDirectory;
    private Replay myActiveReplay;
    private boolean isRecording;

    /** Create a new {@code replay.external.Recorder} to record {@code Event} objects
     *  @param recordTarget The target {@code EventBus} to record
     */
    public Recorder(EventBus recordTarget){
        myEventBus = recordTarget;
        myActiveReplay = new Replay();
        isRecording = false;
        myEventBus.register(this);
    }

    /** Create a new {@code replay.external.Recorder} with a recording directory */
    public Recorder(EventBus recordTarget, File directory) throws InvalidDirectoryException {
        this(recordTarget);
        setRecordingDirectory(directory);
    }

    public void setRecordingDirectory(File directory) throws InvalidDirectoryException {
        if(directory.isDirectory()){
            myDirectory = directory;
        }
        else{
            throw new InvalidDirectoryException();
        }
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

    /** Saves the active replay to disk using serialization. Saves to project directory if recording directory is null,
     *  or specified directory if recording directory has been set.
     */
    public void save() throws SaveReplayFailedException {
        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file;
            File replayDirectory;
            if(myDirectory==null){
                replayDirectory = new File("replays");
                replayDirectory.mkdir();
            }
            else{
                replayDirectory = new File(myDirectory,"replays");
            }
            //Save file using automatically generated name, add (1), (2), etc. if file name taken
            File targetFile = new File(replayDirectory,createFileName());
            int copy = 1;
            while(!targetFile.createNewFile()){
                targetFile = new File(myDirectory,targetFile.getName()+"("+copy+")");
                copy++;
            }
            file = new FileOutputStream(targetFile);
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Method for serialization of object
            out.writeObject(myActiveReplay);
            out.close();
            file.close();
        }
        catch(Exception ex)
        {
            throw new SaveReplayFailedException();
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
            myActiveReplay = replay;
        }
        catch(IOException | ClassNotFoundException | ClassCastException ex)
        {
            throw new InvalidReplayFileException(ex.getMessage());
        }
    }

    /** Creates a file name for the replay file based on current system date and time */
    private String createFileName(){
        String timestamp = ReplayUtilities.getCurrentTimeUsingCalendar();
        return("replay_"+timestamp+".yeet");
    }

    /** Promiscuously listens for all {@code Event} objects passing through the target {@code EventBus},
     *  but only records them if in recording mode.
     */
    @Subscribe
    public void getEvent(Event event){
        if(isRecording){
            myActiveReplay.add(event);
        }
    }




}
