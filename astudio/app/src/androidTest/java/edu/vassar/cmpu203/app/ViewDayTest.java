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
        ViewInteraction menuTitle = Espresso.onView(ViewMatchers.withId(R.id.menuTitle));
        menuTitle.check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText("2022-04-27"),ViewMatchers.withId(R.id.menuTitle))));
        ViewInteraction menuView = Espresso.onView(ViewMatchers.withId(R.id.menusDisplay));
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

        //Select restrictions (gluten and vegan)
        ViewInteraction vegeButton = Espresso.onView(ViewMatchers.withId(R.id.vegetarianButton));
        ViewInteraction veganButton = Espresso.onView(ViewMatchers.withId(R.id.veganButton));
        ViewInteraction halalButton = Espresso.onView(ViewMatchers.withId(R.id.halalButton));
        ViewInteraction kosherButton = Espresso.onView(ViewMatchers.withId(R.id.kosherButton));
        ViewInteraction glutenButton = Espresso.onView(ViewMatchers.withId(R.id.lowGlutenButton));
        ViewInteraction balanceButton = Espresso.onView(ViewMatchers.withId(R.id.inBalanceButton));
        veganButton.perform(ViewActions.click());
        glutenButton.perform(ViewActions.click());

        //Click search button
        ViewInteraction dateButton = Espresso.onView(ViewMatchers.withId(R.id.dateInputButton));
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website

        //Check that certain items are in the menu
        ViewInteraction menuTitle = Espresso.onView(ViewMatchers.withId(R.id.menuTitle));
        menuTitle.check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText("2021-12-12"),ViewMatchers.withId(R.id.menuTitle))));
        ViewInteraction menuView = Espresso.onView(ViewMatchers.withId(R.id.menusDisplay));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Cream Of Rice")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Seasoned Breakfast Potatoes")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Beyond Burger")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Chickpeas, Kale, And Raisins")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Mixed Berries")));

        //Check that items that do not fit the restrictions are not displayed
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Mac And Cheese"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Cajun Shrimp Pizza"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Monte Cristo"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Oatmeal With Cranberries"))));

        //Set new restrictions (gluten and halal)
        veganButton.perform(ViewActions.click());
        halalButton.perform(ViewActions.click());
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check updated menu items
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Fall Vegetable And Chicken Hash")));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Mac And Cheese"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Cream Of Rice"))));

        //Set new restrictions (in balance)
        glutenButton.perform(ViewActions.click());
        halalButton.perform(ViewActions.click());
        balanceButton.perform(ViewActions.click());
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check updated menu items
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Oatmeal")));
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Chickpeas, Kale, And Raisins")));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Mac And Cheese"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Fall Vegetable And Chicken Hash"))));


        //Enter new date
        dateInput.perform(ViewActions.clearText());
        dateInput.perform(ViewActions.typeText("2022-10-03"));
        Espresso.closeSoftKeyboard();
        //Set new restrictions (kosher and vegetarian)
        balanceButton.perform(ViewActions.click());
        vegeButton.perform(ViewActions.click());
        kosherButton.perform(ViewActions.click());
        //Click search button
        dateButton.perform(ViewActions.click());
        SystemClock.sleep(waitTime); //Wait to fetch from website
        //Check updated menu items
        menuView.check(ViewAssertions.matches(ViewMatchers.withSubstring("Sweet Potato Ravioli With Sage Infused Olive Oil")));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Fall Vegetable And Chicken Hash"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Mac And Cheese"))));
        menuView.check(ViewAssertions.matches(Matchers.not(ViewMatchers.withSubstring("Cream Of Rice"))));


    }



}
