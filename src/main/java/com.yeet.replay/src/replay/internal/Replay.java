package replay.internal;

import messenger.external.Event;

import java.io.Serializable;
import java.util.ArrayList;

/** Created and read by the recorder, can be saved to file to reproduce a sequence of events during a combat
 *  @author bpx
 */
public class Replay implements Serializable {

    private int index;
    private ArrayList<Event> eventList;
    private int length;

    public Replay(){
        index = -1;
        length = 0;
        eventList = new ArrayList<>();
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
