// This handles the logic of the app

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private User user;
    private DayLibrary days;

    public Controller() {
        this.days = new DayLibrary();
    }

    public static Restrictions getPossibleRestrictions() {
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

    public String getDayAsString(String date) throws Exception {
        Day day = this.days.getDay(date, this.user);
        String dayString = day.toString();
        if (dayString.toString().equals("")) {
            return "No meals found for " + date + " with your restrictions.";
        }
        return dayString.toString();
    }
}
