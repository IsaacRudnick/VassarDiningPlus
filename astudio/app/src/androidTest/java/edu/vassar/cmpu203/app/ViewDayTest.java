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
        ViewInteraction menuView = Espresso.onView(ViewMatchers.withId(R.id.recyclerView));
        ViewMatchers.withSubstring("@The Farmer");
        ViewMatchers.withSubstring("Vegan White Bean And Chickpea Soup");
        ViewMatchers.withSubstring("Pesto Shrimp Pizza");
        ViewMatchers.withSubstring("Lemon-garlic Pasta Primavera");
        ViewMatchers.withSubstring("Corn And Black Bean Cheese Quesadillas");

        //Enter new date
        dateInput.perform(ViewActions.clearText());

        dateInput.perform(ViewActions.typeText("2023-11-11"));
        Espresso.closeSoftKeyboard();

        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website

        //Check that certain meals are in the menu
        ViewMatchers.withSubstring("Stuffed French Toast With Berries And Cream Cheese");
        ViewMatchers.withSubstring("Hawaiian Style Pizza");
        ViewMatchers.withSubstring("Spicy Thai Coconut Chicken Soup With Noodles");
        ViewMatchers.withSubstring("Mozzarella Stix With Marinara");

        //Check that old menu items are no longer displayed
        Espresso.onView(ViewMatchers.withSubstring("Pesto Shrimp Pizza")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Vegan White Bean And Chickpea Soup")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Lemon-garlic Pasta Primavera")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Corn And Black Bean Cheese Quesadillas")).check(ViewAssertions.doesNotExist());


        //Try previous date again to check that it is not scraped again
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2022-04-27"));
        Espresso.closeSoftKeyboard();
        dateButton.perform(ViewActions.click());

        //Check that certain meals are in the menu
        ViewMatchers.withSubstring("Vegan White Bean And Chickpea Soup");
        ViewMatchers.withSubstring("Pesto Shrimp Pizza");
        ViewMatchers.withSubstring("Lemon-garlic Pasta Primavera");
        ViewMatchers.withSubstring("Corn And Black Bean Cheese Quesadillas");

        //Check that past items are no longer there
        Espresso.onView(ViewMatchers.withSubstring("Stuffed French Toast With Berries And Cream Cheese")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Hawaiian Style Pizza")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Spicy Thai Coconut Chicken Soup With Noodles")).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withSubstring("Mozzarella Stix With Marinara")).check(ViewAssertions.doesNotExist());

    }

    @org.junit.Test
    public void favoritesTest(){
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
        ViewInteraction menuView = Espresso.onView(ViewMatchers.withId(R.id.recyclerView));
        ViewInteraction dish1 = Espresso.onView(ViewMatchers.withSubstring("Cream Of Rice"));
        ViewInteraction dish2 = Espresso.onView(ViewMatchers.withSubstring("Seasoned Breakfast Potatoes"));
        ViewInteraction dish3 = Espresso.onView(ViewMatchers.withSubstring("Beyond Burger"));
        ViewInteraction dish4 = Espresso.onView(ViewMatchers.withSubstring("Chickpeas, Kale, And Raisins"));
        ViewInteraction dish5 = Espresso.onView(ViewMatchers.withSubstring("Mixed Berries"));

        //Select favorites
        // dish1.perform(ViewActions.scrollTo());
        /*ViewInteraction favorite1 = Espresso.onView(ViewMatchers.withTagValue(Matchers.is("cream of rice")));
        favorite1.perform(ViewActions.scrollTo());
        favorite1.perform(ViewActions.click());
        dish4.perform(ViewActions.scrollTo());
        ViewInteraction favorite4 = Espresso.onView(ViewMatchers.withTagValue(Matchers.is("chickpeas, kale, and raisins")));
        favorite4.perform(ViewActions.scrollTo());
        favorite4.perform(ViewActions.click());
        dish2.perform(ViewActions.scrollTo());
        ViewInteraction favorite2 = Espresso.onView(ViewMatchers.withTagValue(Matchers.is("seasoned breakfast potatoes")));
        favorite2.perform(ViewActions.scrollTo());
        favorite2.perform(ViewActions.click());

        //Select view only favorites
        ViewInteraction favoritesButton = Espresso.onView(ViewMatchers.withId(R.id.favoritesFilterCheckbox));
        favoritesButton.perform(ViewActions.click());
        //Check that all favorites and only favorites are displayed
        ViewMatchers.withSubstring("Cream Of Rice");
        ViewMatchers.withSubstring("Seasoned Breakfast Potatoes");
        Matchers.not(ViewMatchers.withSubstring("Beyond Burger"));
        Matchers.not(ViewMatchers.withSubstring("Mixed Berries"));*/

        /*//Enter new date
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
