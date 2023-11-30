package edu.vassar.cmpu203.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This class represents a user of the app
// It is used to filter dishes by restrictions
public class User implements Serializable {
    // These are things that items must meet to be shown to the user
    private final List<String> restrictions;
    private final Set<Dish> favorites = new HashSet<>();

    // Constructor for empty user and user with restrictions
    public User(List<String> restrictions) {
        this.restrictions = restrictions;
    }
    public User() {this.restrictions = new ArrayList<>();}


    /**
     * Adds a dish to the user's favorites
     * @param dish The dish to add
     */
    public void addFavorite(Dish dish) {
        favorites.add(dish);
    }

    /**
     * Removes a dish from the user's favorites
     * @param dish The dish to remove
     */

    public void removeFavorite(Dish dish) {
        for (Dish favoritedDish : favorites) {
            if (favoritedDish.getName().equals(dish.getName())) {
                favorites.remove(favoritedDish);
                return;
            }
        }
    }

    /**
     * Checks if a dish is in the user's favorites (by name)
     * @param dish The dish to check
     * @return Whether or not the dish is in the user's favorites
     */
    public boolean isFavorite(Dish dish) {
        for (Dish favoritedDish : favorites) {
            if (favoritedDish.getName().equals(dish.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a dish can be eaten by the user
     * @param dish The dish to check
     * @return Whether or not the dish can be eaten by the user
     */
    public boolean canEat(Dish dish) {
        for (String restriction : restrictions) {
            if (!dish.hasRestriction(restriction)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the user's restrictions
     * @return The user's restrictions as a list of strings
     */
    public List<String> getRestrictions() {
        return restrictions;
    }

    /**
     * Checks if two users are equal (have the same restrictions)
     * @param user The user to compare to each other
     * @return Whether or not the users are equal
     * Note that this method does not account for favorites since favorite checking does not require
     * day cache invalidation, but different restrictions do
     */
    public boolean equals(User user) {
        List<String> currRestrictions = this.getRestrictions();
        List<String> newRestrictions = user.getRestrictions();
        return new HashSet<>(currRestrictions).equals(new HashSet<>(newRestrictions));
    }

    /**
     * Sets the user's restrictions manually
     * @param restrictions The restrictions to set
     */
    public void setRestrictions(List<String> restrictions) {
        this.restrictions.clear();
        this.restrictions.addAll(restrictions);
    }

    /**
     * Gets the user's favorites as a menu
     * @return The user's favorites as a menu
     * This is used to display the user's favorites in the ManageProfileFragment
     */
    public Menu getFavoritesAsMenu() {
        Menu favoritesMenu = new Menu("Favorites");
        for (Dish dish : favorites) {
            favoritesMenu.addDish(dish);
        }
        return favoritesMenu;
    }
}
