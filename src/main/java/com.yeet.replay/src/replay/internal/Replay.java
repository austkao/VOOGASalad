package replay.internal;

import messenger.external.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/** Created and read by the recorder, can be saved to file to reproduce a sequence of events during a combat
 *  @author bpx
 */
public class Replay implements Serializable {

    //meta data
    private String stageName;
    private HashMap<Integer,String> characterMap;
    private String date;
    private String time;

    private int index;
    private ArrayList<Event> eventList;
    private int length;

    public Replay(){
        stageName = "";
        characterMap = new HashMap<>();
        date = "";
        index = -1;
        length = 0;
        eventList = new ArrayList<>();
    }

    public void setStageName(String stageName){
        this.stageName = stageName;
    }

    public void setCharacterMap(HashMap<Integer,String> characterMap){
        this.characterMap = characterMap;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    /** Add an {@code Event} to the replay
     *  @param frame Any kind of {@code Event}
     */
    public void add(Event frame){
        eventList.add(frame);
        length++;
    }

    /** Returns the next {@code Event} in the {@code replay.internal.Replay}, throws {@code replay.internal.EndOfReplayException} if at end*/
    public Event next() throws EndOfReplayException {
        if(index<length){
            index++;
            return eventList.get(index);
        }
        else{
            throw new EndOfReplayException();
        }
    }

    public int getIndex(){
        return index;
    }

    public int getLength(){
        return length;
    }

}
