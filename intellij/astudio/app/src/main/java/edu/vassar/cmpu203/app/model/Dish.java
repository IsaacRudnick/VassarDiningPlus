package edu.vassar.cmpu203.app.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable {
    private final String name;
    private final String description;
    private final List<Restriction> restrictions;


    public Dish(String name, String description, List<Restriction> restrictions) {
        this.name = name;
        this.description = description;
        this.restrictions = restrictions;
    }

    /**
     * Note that this method has boolean flags to show or hide the description and restrictions of the dish.
     * @return A string representation of the dish
     *
     */
    @NonNull
    @Override
    public String toString() {
        String output = "";
        output += name + "\n";

        boolean SHOW_DESCRIPTION = false;
        boolean SHOW_RESTRICTIONS = false;

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
    public boolean hasRestriction(Restriction restriction) {
        return restrictions.contains(restriction);
    }

    // Below are getter methods for the private variables that may be useful

    public String getName() {
        return name;
    }

}