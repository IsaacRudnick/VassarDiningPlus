package edu.vassar.cmpu203.app;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;

import edu.vassar.cmpu203.app.controller.ControllerActivity;

/*
 * This class is instrumented testing for all functionalities in the ManageProfileFragment,
 * as well as the navigation bar.
 * Both tests reset the memory of the app for the purposes of testing favorites, and restrictions.
 * Note that this will entirely reset the memory for the app, so any saved information from
 * running it on a particular device in the past will no longer be available.
 */

public class ManageProfileTest {
    final int waitTime = 15000;
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    /*
     * Instrumented test, which will execute on an Android device.
     * Testing for correct dishes for all restrictions, as well as restrictions
     * remaining over multiple searches.
     */
    @org.junit.Test
    public void restrictionsTest(){
        //Reset memory to remove any previously set restrictions or favorites
        this.activityRule.getScenario().onActivity(
                new ActivityScenario.ActivityAction<ControllerActivity>(){
                    @Override
                    public void perform(ControllerActivity activity) {activity.memoryReset();}
                } );
        SystemClock.sleep(waitTime); //Wait for memory reset to complete

        //Switch to manage profile screen
        ViewInteraction profileButton = Espresso.onView(ViewMatchers.withId(R.id.profileButton));
        ViewInteraction menuButton = Espresso.onView(ViewMatchers.withId(R.id.browseMenuButton));
        profileButton.perform(ViewActions.click());

        //Clear restrictions
        ViewInteraction vegeButton = Espresso.onView(ViewMatchers.withId(R.id.vegetarianButton));
        ViewInteraction veganButton = Espresso.onView(ViewMatchers.withId(R.id.veganButton));
        ViewInteraction halalButton = Espresso.onView(ViewMatchers.withId(R.id.halalButton));
        ViewInteraction kosherButton = Espresso.onView(ViewMatchers.withId(R.id.kosherButton));
        ViewInteraction glutenButton = Espresso.onView(ViewMatchers.withId(R.id.lowGlutenButton));
        ViewInteraction balanceButton = Espresso.onView(ViewMatchers.withId(R.id.inBalanceButton));

        //Select restrictions (gluten and vegan)
        veganButton.perform(ViewActions.click());
        glutenButton.perform(ViewActions.click());

        //Return to browse menu screen
        menuButton.perform(ViewActions.click());

        //Enter desired date
        ViewInteraction dateInput = Espresso.onView(ViewMatchers.withId(R.id.dateInput));
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2021-12-12"));
        Espresso.closeSoftKeyboard();
        //Click search button
        ViewInteraction dateButton = Espresso.onView(ViewMatchers.withId(R.id.dateInputButton));
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check that certain items are in the menu
        Espresso.onView(ViewMatchers.withSubstring("Mesclun")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Fanta Orange")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Italian Dressing")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Mushroom")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Black Coffee")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //Check that items that do not fit the restrictions are not displayed
        Espresso.onView(ViewMatchers.withSubstring("Pesto Shrimp Pizza")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mac And Cheese")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Fall Vegetable And Chicken Hash")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Blackened Cajun Chicken")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Oatmeal With Cranberries")).check(ViewAssertions.doesNotExist());

        //Switch back to profile view
        profileButton.perform(ViewActions.click());
        //Set new restrictions (in balance)
        veganButton.perform(ViewActions.click());
        glutenButton.perform(ViewActions.click());
        balanceButton.perform(ViewActions.click());
        //Return to browse menu screen
        menuButton.perform(ViewActions.click());
        //Enter same date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2021-12-12"));
        Espresso.closeSoftKeyboard();
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check updated menu items
        Espresso.onView(ViewMatchers.withSubstring("Oatmeal")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Chickpeas, Kale, And Raisins")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //Check that items that do not fit the restrictions are not displayed
        Espresso.onView(ViewMatchers.withSubstring("Black Coffee")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mac And Cheese")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mesclun")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Fanta Orange")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mushroom")).check(ViewAssertions.doesNotExist());

        //Switch back to profile view
        profileButton.perform(ViewActions.click());
        //Set new restrictions (gluten and halal)
        glutenButton.perform(ViewActions.click());
        halalButton.perform(ViewActions.click());
        balanceButton.perform(ViewActions.click());
        //Return to browse menu screen
        menuButton.perform(ViewActions.click());
        //Enter same date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2021-12-12"));
        Espresso.closeSoftKeyboard();
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check updated menu items
        Espresso.onView(ViewMatchers.withSubstring("Fall Vegetable And Chicken Hash")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Blackened Cajun Chicken")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Grilled Chicken Breast")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //Check that items that do not fit the restrictions are not displayed
        Espresso.onView(ViewMatchers.withSubstring("Pesto Shrimp Pizza")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mac And Cheese")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Chickpeas, Kale, And Raisins")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mesclun")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Fanta Orange")).check(ViewAssertions.doesNotExist());

        //Enter new date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2022-10-03"));
        Espresso.closeSoftKeyboard();
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check updated menu items
        Espresso.onView(ViewMatchers.withSubstring("Herb Seasoned Chicken")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Grilled Chicken Breast")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withSubstring("Marinated Chicken And Vegetables")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //Check that items that do not fit the restrictions are not displayed
        Espresso.onView(ViewMatchers.withSubstring("Agave Glazed Carrots")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mac And Cheese")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Chickpeas, Kale, And Raisins")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Cream Of Wheat")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Baked Ziti With Beef")).check(ViewAssertions.doesNotExist());

        //Switch back to profile view
        profileButton.perform(ViewActions.click());
        //Set new restrictions (kosher and vegetarian)
        glutenButton.perform(ViewActions.click());
        halalButton.perform(ViewActions.click());
        vegeButton.perform(ViewActions.click());
        kosherButton.perform(ViewActions.click());
        //Return to browse menu screen
        menuButton.perform(ViewActions.click());
        //Enter same date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2022-10-03"));
        Espresso.closeSoftKeyboard();
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check updated menu items
        Espresso.onView(ViewMatchers.withSubstring("Sweet Potato Ravioli With Sage Infused Olive Oil")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //Check that items that do not fit the restrictions are not displayed
        Espresso.onView(ViewMatchers.withSubstring("Agave Glazed Carrots")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Grilled Chicken Breast")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Marinated Chicken And Vegetables")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Cream Of Wheat")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Baked Ziti With Beef")).check(ViewAssertions.doesNotExist());

        //Unclick all restrictions for future tests
        profileButton.perform(ViewActions.click());
        vegeButton.perform(ViewActions.click());
        kosherButton.perform(ViewActions.click());
    }

    /*
     * Instrumented test, which will execute on an Android device.
     * Testing for favorites being displayed correctly in the ManageProfileFragment
     * and removal of favorites from the ManageProfileFragment displaying properly
     * in the ViewDayFragment.
     */
    @org.junit.Test
    public void profileFavoritesTest() {
        //Reset memory to remove any previously set restrictions or favorites
        this.activityRule.getScenario().onActivity(
                new ActivityScenario.ActivityAction<ControllerActivity>(){
                    @Override
                    public void perform(ControllerActivity activity) {activity.memoryReset();}
                } );
        SystemClock.sleep(waitTime); //Wait for memory reset to complete

        //Enter desired date
        ViewInteraction dateInput = Espresso.onView(ViewMatchers.withId(R.id.dateInput));
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2023-10-09"));
        Espresso.closeSoftKeyboard();

        //Click search button
        ViewInteraction dateButton = Espresso.onView(ViewMatchers.withId(R.id.dateInputButton));
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website

        //Check that certain items are in the menu
        ViewInteraction dish1 = Espresso.onView(ViewMatchers.withSubstring("Plain Greek Yogurt"));
        ViewInteraction dish2 = Espresso.onView(ViewMatchers.withSubstring("Italian Dressing"));
        ViewInteraction dish3 = Espresso.onView(ViewMatchers.withSubstring("Feta Cheese"));
        ViewInteraction dish4 = Espresso.onView(ViewMatchers.withSubstring("Pumpkin Seeds"));
        ViewInteraction dish5 = Espresso.onView(ViewMatchers.withSubstring("Thousand Island Dressing"));

        //Select favorites
        ViewInteraction favorite1 = Espresso.onView(ViewMatchers.withTagValue(Matchers.is("plain greek yogurt")));
        favorite1.perform(ViewActions.click());
        ViewInteraction favorite2 = Espresso.onView(ViewMatchers.withTagValue(Matchers.is("italian dressing")));
        favorite2.perform(ViewActions.click());
        ViewInteraction favorite5 = Espresso.onView(ViewMatchers.withTagValue(Matchers.is("thousand island dressing")));
        favorite5.perform(ViewActions.click());

        //Select view only favorites
        ViewInteraction favoritesButton = Espresso.onView(ViewMatchers.withId(R.id.favoritesFilterCheckbox));
        favoritesButton.perform(ViewActions.click());
        //Check that all favorites and only favorites are displayed
        dish1.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish2.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish5.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish4.check(ViewAssertions.doesNotExist());
        dish3.check(ViewAssertions.doesNotExist());

        //Switch  to profile view
        ViewInteraction profileButton = Espresso.onView(ViewMatchers.withId(R.id.profileButton));
        ViewInteraction menuButton = Espresso.onView(ViewMatchers.withId(R.id.browseMenuButton));
        profileButton.perform(ViewActions.click());

        //Check that all favorites and only favorites are displayed
        dish1.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish2.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish5.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish4.check(ViewAssertions.doesNotExist());
        dish3.check(ViewAssertions.doesNotExist());

        //Unfavorite one item
        favorite1.perform(ViewActions.click());

        //Switch to menu view and view only favorites
        menuButton.perform(ViewActions.click());
        favoritesButton.perform(ViewActions.click());

        //Check that new favorites are displayed
        dish1.check(ViewAssertions.doesNotExist());
        dish2.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish5.check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        dish4.check(ViewAssertions.doesNotExist());
        dish3.check(ViewAssertions.doesNotExist());
    }
}
