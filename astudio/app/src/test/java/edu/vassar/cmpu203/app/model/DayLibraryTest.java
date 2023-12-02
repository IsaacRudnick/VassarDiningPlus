package edu.vassar.cmpu203.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DayLibraryTest {

    /**
     * Tests that the day library can set a user and that it returns the correct boolean
     * value when the user is overwritten or not.
     */
    @Test
    void testSetUser() {
        // By default, the user is set to a new user with no restrictions
        DayLibrary dayLibrary = new DayLibrary();
        List<Restriction> emptyRestrictions = new ArrayList<>();
        // Overwriting an empty user with another empty user should return false
        // (no change and cache stays intact)
        assertFalse(dayLibrary.setUserRestrictions(emptyRestrictions));

        // However, overwriting an empty user with a non-empty user should return true
        // (cached days are cleared)
        List<Restriction> restrictionsA = new ArrayList<>(Arrays.asList(Restriction.VEGETARIAN, Restriction.VEGAN));
        assertTrue(dayLibrary.setUserRestrictions(restrictionsA));
    }

    /**
     * Tests that the day library can get a day and does partial testing to ensure that
     * the day is partially correct (has certain station names), though the *entire* day
     * is not checked to avoid a several-hundred-line string comparison test.
     */
    @Test
    void testGetDay() throws Exception {
        // Test to make sure that the day library can get a day
        DayLibrary dayLibrary = new DayLibrary();
        String date = "2023-10-10";
        Day day = dayLibrary.getDay(date);
        String dayString = day.toString();
        // and that the day has certain station names
        assertTrue(dayString.contains("@Oasis"));
        assertTrue(dayString.contains("@Root"));
        assertTrue(dayString.contains("@Brick Oven"));

        // Test that two DayLibrary objects with the same user and date return the same day
        DayLibrary dayLibrary2 = new DayLibrary();
        Day day2 = dayLibrary2.getDay(date);
        assertEquals(day.toString(), day2.toString());
    }
}