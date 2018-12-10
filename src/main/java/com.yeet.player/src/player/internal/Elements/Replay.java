package player.internal.Elements;

import java.io.Serializable;
import java.util.LinkedList;

/** Created and read by the recorder, can be saved to file to reproduce a sequence of events during a combat
 *  @author bpx
 */
public class Replay implements Serializable {

    private LinkedList eventList;

    public Replay(){
        eventList = new LinkedList();

    }

}
