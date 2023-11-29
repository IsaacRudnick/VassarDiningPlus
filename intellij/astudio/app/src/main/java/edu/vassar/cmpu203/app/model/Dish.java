package edu.vassar.cmpu203.app.model;

import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable {
    // Whether or not to show the description and restrictions when representing the dish to a string
    private boolean SHOW_DESCRIPTION = false;
    private boolean SHOW_RESTRICTIONS = false;
    private String id;
    private String name;
    private String description;
    private List<String> restrictions;


    public Dish(String id, String name, String description, List<String> restrictions) {
        // Currently, ID is untested as consistent across days
        // this.id = id;
        this.name = name;
        this.description = description;
        this.restrictions = restrictions;
    }

    /**
     * @return A string representation of the dish
     */
    @Override
    public String toString() {
        String output = "";
        output += name + "\n";
        // Only add the description if it exists (not empty)
        if (description.length() > 0 && SHOW_DESCRIPTION) {
            output += "\t-Description: " + description + "\n";
        }
        if (SHOW_RESTRICTIONS) {
            output += "\t-Restrictions: " + restrictions + "\n";
        }
        return output;
    }

    /*
     * Checks if the dish has a given restriction
     * 
     * @param restriction The restriction to check for (e.g. "Vegan")
     * 
     * @return Whether or not the dish has the given restriction
     */
    public boolean hasRestriction(String restriction) {
        return restrictions.contains(restriction);
    }

    // Below are getter methods for the private variables that may be useful

//    public String getId() {
//        return id;
//    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }
}