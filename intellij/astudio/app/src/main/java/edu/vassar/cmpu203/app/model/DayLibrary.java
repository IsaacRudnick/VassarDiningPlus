package edu.vassar.cmpu203.app.model;

import java.io.Serializable;
import java.util.HashMap;
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
     * Update user from restrictions of new user.
     * Delete cached days if new user is different (diff restrictions)
     * Returns true if new user had different restrictions from old user
     * (therefore clearing cached days)
     * @param newUser - the new user to set.
     * @return - boolean of whether cache was cleared
     */
    public boolean setUser(User newUser){

        // Check if the old and new users are the same. If they aren't, reset the cached days
        if (this.user.equals(newUser)) {
            return false;
        }
        else {
            this.user.setRestrictions(newUser.getRestrictions());
            this.days = new HashMap<>();
            return true;
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
