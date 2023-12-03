package edu.vassar.cmpu203.app;

import android.os.SystemClock;

import androidx.test.espresso.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;

import java.time.LocalDate;
import edu.vassar.cmpu203.app.controller.ControllerActivity;

public class ViewDayTest {
    //wait time to fetch menu
    final int waitTime = 15000;
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    /*
     * Instrumented test, which will execute on an Android device.
     * Testing for correct menu for multiple dates, proper overwriting of menusDisplay, caching of
     * menu previously searched for
     */
    @org.junit.Test
    public void searchDatesTest(){
        //Check default input
        ViewInteraction dateInput = Espresso.onView(ViewMatchers.withId(R.id.dateInput));
        dateInput.check(ViewAssertions.matches(ViewMatchers.withText(LocalDate.now().toString())));

        //Enter desired date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2022-04-27"));
        Espresso.closeSoftKeyboard();

        //Click search button
        ViewInteraction dateButton = Espresso.onView(ViewMatchers.withId(R.id.dateInputButton));
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website

        //Check that certain meals are in the menu
        // removed this section of the layout
        // ViewInteraction menuTitle = Espresso.onView(ViewMatchers.withId(R.id.menuTitle));
        // menuTitle.check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText("2022-04-27"),ViewMatchers.withId(R.id.menuTitle))));
        ViewInteraction menuView = Espresso.onView(ViewMatchers.withId(R.id.recyclerView));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("@The Farmer")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Vegan White Bean And Chickpea Soup")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Pesto Shrimp Pizza")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Lemon-garlic Pasta Primavera")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Corn And Black Bean Cheese Quesadillas")));

        //Enter new date
        dateInput.perform(ViewActions.clearText());

        dateInput.perform(ViewActions.typeText("2023-11-11"));
        Espresso.closeSoftKeyboard();

        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website

        //Check that certain meals are in the menu
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Stuffed French Toast With Berries And Cream Cheese")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Hawaiian Style Pizza")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Spicy Thai Coconut Chicken Soup With Noodles")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Mozzarella Stix With Marinara")));

        //Check that old menu items are no longer displayed
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Pesto Shrimp Pizza"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Vegan White Bean And Chickpea Soup"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Lemon-garlic Pasta Primavera"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Corn And Black Bean Cheese Quesadillas"))));

        //Try previous date again to check that it is not fetched again
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2022-04-27"));
        Espresso.closeSoftKeyboard();
        dateButton.perform(ViewActions.click());

        //Check that certain meals are in the menu
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Vegan White Bean And Chickpea Soup")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Pesto Shrimp Pizza")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Lemon-garlic Pasta Primavera")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Corn And Black Bean Cheese Quesadillas")));

    }

    @org.junit.Test
    public void restrictionsTest(){
        ViewInteraction dateInput = Espresso.onView(ViewMatchers.withId(R.id.dateInput));

        //Enter desired date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2021-12-12"));
        Espresso.closeSoftKeyboard();

        //Click search button
        ViewInteraction dateButton = Espresso.onView(ViewMatchers.withId(R.id.dateInputButton));
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website

        //Check that certain items are in the menu
        //removed this part of the layout
        // ViewInteraction menuTitle = Espresso.onView(ViewMatchers.withId(R.id.menuTitle));
        //menuTitle.check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText("2021-12-12"),ViewMatchers.withId(R.id.menuTitle))));
        ViewInteraction menuView = Espresso.onView(ViewMatchers.withId(R.id.recyclerView));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Cream Of Rice")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Seasoned Breakfast Potatoes")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Beyond Burger")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Chickpeas, Kale, And Raisins")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Mixed Berries")));

        /*//TODO Select favorites

        //TODO Select view only favorites
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check that all favorites and only favorites are displayed
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Fall Vegetable And Chicken Hash")));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Mac And Cheese"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Cream Of Rice"))));

        //Enter new date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2022-10-03"));
        Espresso.closeSoftKeyboard();
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //TODO Check that no favorites are displayed (none chosen yet)
        //TODO Unclick view only favorites
        //Check updated menu items
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Sweet Potato Ravioli With Sage Infused Olive Oil")));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Fall Vegetable And Chicken Hash"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Mac And Cheese"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Cream Of Rice"))));*/


    }



}
