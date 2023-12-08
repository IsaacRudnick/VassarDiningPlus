package edu.vassar.cmpu203.app;

import android.os.SystemClock;

import androidx.test.espresso.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import edu.vassar.cmpu203.app.controller.ControllerActivity;

//TODO figure out clearing memory before running tests

public class ManageProfileTest {
    final int waitTime = 15000;
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    @org.junit.Test
    public void restrictionsTest(){
        ViewInteraction dateInput = Espresso.onView(ViewMatchers.withId(R.id.dateInput));

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
    }
}
