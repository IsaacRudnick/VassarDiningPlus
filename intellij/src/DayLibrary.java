import java.util.HashMap;
import java.util.Map;

/*
 * This class is used to store all the days that have been created.
 * It is used to prevent duplicate days from being created.
 * It is important to note that days / menus / dishes stored in this class
 * Are pre-filtered by user restrictions. The data isn't stored in its entirety.
 */
public class DayLibrary {
    Map<String, Day> days;

    public DayLibrary() {
        this.days = new HashMap<String, Day>();
    }

    public Day getDay(String date, User user) throws Exception {
        // If the day doesn't exist, create it and add it to the map
        if (!days.containsKey(date)) {
            days.put(date, new Day(date, user));
        }
        return days.get(date);
    }
}
