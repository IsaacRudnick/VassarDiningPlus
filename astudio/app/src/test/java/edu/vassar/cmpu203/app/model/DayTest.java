package edu.vassar.cmpu203.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DayTest {


    @Test
    void testToString() {
        User blankUser = new User();
        Day day = new Day("2023-12-02", blankUser);
        String dayAsString = day.toString();

        assertTrue(dayAsString.contains("@Late Night"));
        assertTrue(dayAsString.contains("Waffle Fries"));
        assertTrue(dayAsString.contains("Roasted Vegetable Clubs"));

        assertTrue(dayAsString.contains("@Oasis"));
        assertTrue(dayAsString.contains("Spicy Agave Braised Pork"));
        assertTrue(dayAsString.contains("Quinoa Wth Fresh Herbs"));
    }

    /**
     * Test that the withOnlyFavoritesOf method works as expected
     * This is similar to the above test, but checks that only the favorites of the user are included
     */
    @Test
    void withOnlyFavoritesOf() {
        User user = new User();
        user.addFavorite(new Dish("Waffle Fries", "", new ArrayList<>()));
        user.addFavorite(new Dish("Spicy Agave Braised Pork", "", new ArrayList<>()));

        // Create a day with only the favorites of the user
        Day dayWithOnlyFavorites = new Day("2023-12-02", user).withOnlyFavoritesOf(user);

        String dayAsString = dayWithOnlyFavorites.toString();

        assertTrue(dayAsString.contains("@Late Night"));
        assertTrue(dayAsString.contains("Waffle Fries"));
        // Only the favorited item(s) from each menu should be included, so the above but not below
        assertFalse(dayAsString.contains("Roasted Vegetable Clubs"));

        assertTrue(dayAsString.contains("@Oasis"));
        assertTrue(dayAsString.contains("Spicy Agave Braised Pork"));
        // Only the favorited item(s) from each menu should be included, so the above but not below
        assertFalse(dayAsString.contains("Quinoa Wth Fresh Herbs"));
    }
}