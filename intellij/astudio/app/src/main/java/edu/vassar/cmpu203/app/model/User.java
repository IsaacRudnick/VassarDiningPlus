package edu.vassar.cmpu203.app.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// This class represents a user of the app
// It is used to filter dishes by restrictions
public class User {
    // These are things that items must meet to be shown to the user
    private final List<String> restrictions;

    public User(List<String> restrictions) {
        this.restrictions = restrictions;
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
}
