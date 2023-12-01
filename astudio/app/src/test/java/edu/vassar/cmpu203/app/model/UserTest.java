package edu.vassar.cmpu203.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class UserTest {
    /**
     * Tests differences between users to ensure that the User Class properly reports
     * whether or not a user can eat a dish.
     * <p>
     * Also check whether the .equals() method works properly.
     */
    @Test
    void testCanEat() {
        // Two users: one with no restrictions, one with vegetarian and vegan restrictions
        User anyFoodUser = new User();
        List<String> restrictions = new ArrayList<>(Arrays.asList("Vegetarian", "Vegan"));
        User pickyUser = new User(restrictions);

        // Safe dish can be eaten by both users
        Dish safeDish = new Dish("Test Dish", "N/A", restrictions);
        assertTrue(anyFoodUser.canEat(safeDish));
        assertTrue(pickyUser.canEat(safeDish));

        // Unsafe dish can only be eaten by the anyFoodUser
        Dish unsafeDish = new Dish("Poison Sandwich", "N/A", new ArrayList<>());
        assertTrue(anyFoodUser.canEat(unsafeDish));
        assertFalse(pickyUser.canEat(unsafeDish));


        // .equals() testing
        // Two users with the same restrictions should be equal
        User pickyUser2 = new User(restrictions);
        assertTrue(pickyUser.equals(pickyUser2));
        assertFalse(pickyUser.equals(anyFoodUser));
        assertTrue(anyFoodUser.equals(new User()));
    }

    @Test
    void testEquals() {
        User user1 = new User();
        assertTrue(user1.equals(new User()));

        List<String> restrictions = new ArrayList<>(Arrays.asList("Vegetarian", "Vegan"));
        User user2 = new User(restrictions);
        assertFalse(user1.equals(user2));
    }
}