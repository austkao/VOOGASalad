package replay.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** Utility class for functionality related to the recording system
 *  @author bpx
 */
public class ReplayUtilities {

    private ReplayUtilities(){}

    public static String getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
        String formattedDate=dateFormatter.format(date).replace('/','.');
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormatter.format(date).replace(':','_');
        return formattedDate+"@"+formattedTime;
    }
}
