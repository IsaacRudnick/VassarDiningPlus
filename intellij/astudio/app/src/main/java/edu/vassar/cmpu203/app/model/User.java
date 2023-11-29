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

    public User(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public void addFavorite(Dish dish) {
        favorites.add(dish);
    }

    public void removeFavorite(Dish dish) {
        for (Dish favoritedDish : favorites) {
            if (favoritedDish.getName().equals(dish.getName())) {
                favorites.remove(favoritedDish);
                return;
            }
        }
    }

    public boolean isFavorite(Dish dish) {
        for (Dish favoritedDish : favorites) {
            if (favoritedDish.getName().equals(dish.getName())) {
                return true;
            }
        }
        return false;
    }

    public User() {
        this.restrictions = new ArrayList<>();
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

    public Menu getFavoritesAsMenu() {
        Menu favoritesMenu = new Menu("Favorites");
        for (Dish dish : favorites) {
            favoritesMenu.addDish(dish);
        }
        return favoritesMenu;
    }
}
