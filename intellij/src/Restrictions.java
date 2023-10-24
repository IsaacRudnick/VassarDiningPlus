
// This file exists to create a static map of restriction IDs and names
// It is essentially a map but with a few helper features (e.g. isValid) to check if a given restriction ID is valid
import java.util.HashMap;
import java.util.Map;

public class Restrictions {
    static final Map<String, String> restrictions;
    static {
        restrictions = new HashMap<String, String>();
        restrictions.put("1", "Vegetarian");
        restrictions.put("3", "Seafood Watch");
        restrictions.put("4", "Vegan");
        restrictions.put("6", "Farm to Fork");
        restrictions.put("7", "In Balance");
        restrictions.put("8", "Organic");
        restrictions.put("9", "Made without Gluten-Containing Ingredients");
        restrictions.put("10", "Halal");
        restrictions.put("11", "Kosher");
        restrictions.put("18", "Humane");
    }

    public static String getRestrictionName(String id) {
        return restrictions.get(id);
    }

    /*
     * @return A string representation of the restrictions
     */
    @Override
    public String toString() {
        String output = "";
        for (String id : restrictions.keySet()) {
            output += id + ": " + restrictions.get(id) + "\n";
        }
        return output;
    }

    // Check if a given string is a valid restriction ID
    // E.g. "1" is valid, "12" is not, since it is not in the map's keys
    public static boolean isValid(String id) {
        return restrictions.containsKey(id);
    }

}