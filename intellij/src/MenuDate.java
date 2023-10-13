import java.net.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public class MenuDate {
    // Map of menu names (e.g. "@Oasis") to their menus
    private final Map<String, Menu> Menus;

    public MenuDate() throws Exception {
        Menus = new HashMap<String, Menu>();
        this.scrapeMenu();
    }

    public String toString() {
        String output = "";
        for (String menuName : Menus.keySet()) {
            output += Menus.get(menuName).toString();
        }
        return output;
    }

    public void addDish(String menuName, Dish dish) {
        if (!Menus.containsKey(menuName)) {
            Menus.put(menuName, new Menu(menuName));
        }
        Menus.get(menuName).addDish(dish);
    }

    /*
     * Makes a web request to the CBA website and processes the HTML
     * into a JSON object
     */
    private JSONObject GetMenuJSON() throws Exception {
        // Make web request to CBAURL
        URL url = new URI(Constants.CBAURL).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        if (status != 200) {
            System.out.println("Error: " + status);
            return null;
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            // System.out.println(inputLine);
            content.append(inputLine);
        }
        in.close();
        // This is the HTML of the page
        String pageText = content.toString();

        // Isolate JSON String from HTML
        String pattern = "Bamco.menu_items = (.*);";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = r.matcher(pageText);
        // Search for the JSON in the HTML
        matcher.find();
        // Remove final semicolon from JSON String
        String jsonText = matcher.group(1).split(";")[0];

        // Turn JSON String into JSONObject
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonText);
    }

    private static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    /*
     * Scrapes the CBA website and adds all the dishes to the Menus map
     * Takes no arguments and returns nothing
     */
    private void scrapeMenu() throws Exception {
        JSONObject scrapedDishes = GetMenuJSON();

        // For each dish
        for (Object key : scrapedDishes.keySet()) {
            String id = (String) key;
            // to avoid repeating this .get call, create a helpful dishInfo variable
            JSONObject dishInfo = (JSONObject) scrapedDishes.get(id);

            // Get the station name and remove emphasis tags from the data (e.g. "@Oasis")
            String menuName = ((String) dishInfo.get("station")).replaceAll("<strong>|</strong>", "");

            // Get the dish name (e.g. "Chicken Parmesan")
            String name = (String) dishInfo.get("label");
            name = toTitleCase(name);
            // Get the dish description (e.g. "Chicken Parmesan with Marinara Sauce")
            String description = (String) dishInfo.get("description");
            // Get the restrictions (this is more complicated than above. See below)
            // If the restrictions are an empty list, no restrictions exist
            ArrayList<String> restrictions = new ArrayList<String>();
            // If the restrictions are not empty,
            // they will instead be a JSONObject.
            // In this case, add all the values to a list
            if (dishInfo.get("cor_icon") instanceof JSONObject) {
                JSONObject restrictionsJSON = (JSONObject) dishInfo.get("cor_icon");
                for (Object k : restrictionsJSON.keySet()) {
                    String restriction = (String) restrictionsJSON.get(k); // cast to String
                    restrictions.add(restriction);
                }
            }

            this.addDish(menuName, new Dish(id, name, description, restrictions));
        }
    }
}