package edu.vassar.cmpu203.app.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class is used to store all the days that have been created.
 * It is used to prevent duplicate days from being created.
 * It is important to note that days / menus / dishes stored in this class
 * Are pre-filtered by user restrictions. The data isn't stored in its entirety.
 */
public class DayLibrary implements Serializable {
    private Map<String, Day> days;
    private final User user;

    private static final int MAX_DAYS = 20;

    public DayLibrary() {
        this.user = new User();
        this.days = new HashMap<>();
    }


    /**
     * Update stored user restrictions
     * and clear the cache if the restrictions have changed
     *
     * @param newUserRestrictions - the new restrictions to set.
     * @return - boolean of whether cache was cleared
     */
    public boolean setUserRestrictions(List<Restriction> newUserRestrictions){
        boolean restrictionsChanged = this.user.setRestrictions(newUserRestrictions);
        if(restrictionsChanged){
            this.days = new HashMap<>();
            return true;
        }
        else {
            return false;
        }
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

    /**
     * Gets a day from the library. If the day doesn't exist, it is created.
     * This allows caching of days.
     *
     * @param date - The date of the day to get (format "YYYY-MM-DD")
     * @return
     */
    public Day getDay(String date) {
        // If the day library is too big, clear it
        if (days.size() > MAX_DAYS) {
            days = new HashMap<>();
        }

        // If the day doesn't exist, create it and add it to the map
        if (!days.containsKey(date)) {
            days.put(date, new Day(date, this.user));
        }
        return days.get(date);
    }

}
