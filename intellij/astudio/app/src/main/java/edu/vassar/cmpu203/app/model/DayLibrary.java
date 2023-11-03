package edu.vassar.cmpu203.app.model;

import java.util.HashMap;
import java.util.Map;

/*
 * This class is used to store all the days that have been created.
 * It is used to prevent duplicate days from being created.
 * It is important to note that days / menus / dishes stored in this class
 * Are pre-filtered by user restrictions. The data isn't stored in its entirety.
 */
public class DayLibrary {
    private Map<String, Day> days;

    public DayLibrary() {
        this.days = new HashMap<String, Day>();
    }

    /*
     * Gets a day from the library. If the day doesn't exist, it is created.
     * This allows caching of days.
     * 
     * @param date The date of the day to get (format "YYYY-MM-DD")
     * The date is interpreted by the CBA servers.
     * Invalid dates and formats not checked for
     * 
     * @param user The user to get the day for (used for restrictions)
     * 
     * @return The day object
     */
    public Day getDay(String date, User user) throws Exception {
        // If the day doesn't exist, create it and add it to the map
        if (!days.containsKey(date)) {
            days.put(date, new Day(date, user));
        }
        return days.get(date);
    }
}
