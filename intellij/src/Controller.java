// This handles the logic of the app

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private User user;
    private DayLibrary days;

    public Controller() {
        this.days = new DayLibrary();
    }

    public static Restrictions getRestrictions() {
        return new Restrictions();
    }

    /*
     * This is used to ensure that the user enters valid restrictions
     * 
     * @param restrictionIDs A list of restriction IDs
     * 
     * @return True if all restriction IDs are valid, false otherwise
     */
    public static Boolean areValidRestrictions(List<String> restrictionIDs) {
        // Loop through each restriction ID and check if it is valid
        for (String restriction : restrictionIDs) {
            if (!Restrictions.isValid(restriction)) {
                System.out.println("Invalid restriction: " + restriction);
                // If any restriction is invalid, return false
                return false;
            }
        }
        // If all restrictions are valid, return true
        return true;
    }

    /*
     * This is used to convert restriction IDs to restriction names
     * It is used internally in the createUser method
     * 
     * @param restrictionIDs A list of restriction IDs
     * 
     * @return A list of restriction names
     */
    private static List<String> restrictionsFromIDs(List<String> restrictionIDs) {
        // Convert restriction IDs to restriction names (e.g. [1] -> ["Vegetarian"])
        // This is done so that the user can enter restriction IDs instead of names to
        // speed up input
        List<String> restrictions = new ArrayList<String>();
        for (String restriction : restrictionIDs) {
            restrictions.add(Restrictions.getRestrictionName(restriction));
        }
        return restrictions;
    }

    /*
     * This is used to create and store a user with the given restrictions
     * 
     * @param restrictions A list of restriction names
     */
    public void createUser(List<String> restrictionIDs) {
        this.user = new User(restrictionsFromIDs(restrictionIDs));
    }

    public List<String> getUserRestrictions() {
        return this.user.getRestrictions();
    }

    // Using provided date and stored user, get the day for that date
    public Day getDay(String date) throws Exception {
        return this.days.getDay(date, this.user);
    }
}
