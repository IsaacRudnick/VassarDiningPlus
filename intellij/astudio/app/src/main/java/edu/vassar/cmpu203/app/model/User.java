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
    private final Set<String> favorites = new HashSet<>();

    public User(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public void addFavorite(Dish dish) {
        favorites.add(dish.getName());
    }

    public void removeFavorite(Dish dish) {
        favorites.remove(dish.getName());
    }

    public boolean isFavorite(Dish dish) {
        return favorites.contains(dish.getName());
    }

    public User() {
        this.restrictions = new ArrayList<String>();
    }

    public boolean canEat(Dish dish) {
        for (String restriction : restrictions) {
            if (!dish.hasRestriction(restriction)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public boolean equals(User user) {
        List<String> currRestrictions = this.getRestrictions();
        List<String> newRestrictions = user.getRestrictions();
        return new HashSet<>(currRestrictions).equals(new HashSet<>(newRestrictions));
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions.clear();
        this.restrictions.addAll(restrictions);
    }
}
