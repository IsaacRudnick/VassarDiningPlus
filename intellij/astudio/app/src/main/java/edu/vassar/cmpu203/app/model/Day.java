package edu.vassar.cmpu203.app.model;

import java.net.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

public class Day {
    // Map of menu names (e.g. "@Oasis") to their menus
    private final Map<String, Menu> menus;

    private final String date;

    /**
     * Creates a new Day object
     * 
     * @param date The date of the day to create (format "YYYY-MM-DD")
     * @param user The user to create the day for (used for restrictions)
     */
    public Day(String date, User user) {
        // Currently not in use but will be used in the future for caching
        this.date = date;
        // Currently not in use but will be used in the future for caching

        // Create the menus hashmap
        menus = new HashMap<>();
        // Scrape the menu and add all the dishes to the Menus map
        try {
            createMenus(user);
        }
        catch (Exception e) {
//            Log.e("Error", "Error creating menus (Day -> constructor)", e);
        }

    }

    public Map<String, Menu> getMenus() {
        return menus;
    }

    /**
     * @return A string representation of the day, including all menus and dishes
     */

    @NonNull
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (String menuName : menus.keySet()) {
            output.append(menus.get(menuName).toString());
        }
        return output.toString();
    }


    /**
     * Adds a dish to the Menus map. For class-internal use while scraping the menu.
     * 
     * @param menuName the name of the menu to add the dish to
     * @param dish     the dish to add
     */
    private void addDish(String menuName, Dish dish) {
        if (!menus.containsKey(menuName)) {
            menus.put(menuName, new Menu(menuName));
        }
        this.menus.get(menuName).addDish(dish);
    }

    /**
     * Makes a web request to the CBA website and processes the HTML
     * into a JSON object. Returns the JSON object. This is a helper function for
     * createMenus(), which is a helper function for the Day constructor.
     * 
     * @return The JSON object of the menu
     */
    private JSONObject GetMenuJSON() throws Exception {
        // Force android to allow HTTP Request on main thread
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        } catch (Exception e) {
//            Log.e("Error", "Error setting StrictMode policy", e);
        }
        // Make web request to CBAURL
        URL url = new URI(Constants.CBAURL + this.date).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        if (status != 200) {
            Log.e("StatusError", Integer.toString(status));
            return null;
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
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
        matcher.find();
        // Remove final semicolon from JSON String
        String jsonText = matcher.group(1).split(";")[0];

        // Turn JSON String into JSONObject
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonText);

    }

    /**
     * Converts a string to title case (e.g. "hello world" -> "Hello World")
     * This is a helper function for createMenus(), which is a helper function for
     * the Day constructor.
     * 
     * @param input The string to convert
     * @return The converted string
     */
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

    /**
     * Scrapes the CBA website and adds all the dishes to the Menus map
     * Takes no arguments and returns nothing. This is a helper function for the Day
     * constructor.
     * @param user
     * @throws Exception
     */
    private void createMenus(User user) throws Exception {
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
            ArrayList<String> restrictions = new ArrayList<>();
            // If the restrictions are not empty,
            // they will instead be a JSONObject.
            // In this case, add all the values to a list
            if (dishInfo.get("cor_icon") instanceof JSONObject) {
                JSONObject restrictionsJSON = (JSONObject) dishInfo.get("cor_icon");
                for (Object k : restrictionsJSON.keySet()) {
                    String restriction = (String) restrictionsJSON.get((String) k); // cast to String
                    restrictions.add(restriction);
                }
            }
            // Add the dish to the Menus map if the user can eat it
            Dish dish = new Dish(name, description, restrictions);
            if (user.canEat(dish)) {
                addDish(menuName, dish);
            }
        }
    }
}