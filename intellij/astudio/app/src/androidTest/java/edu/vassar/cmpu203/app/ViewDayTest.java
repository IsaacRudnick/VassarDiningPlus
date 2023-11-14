package edu.vassar.cmpu203.app;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.test.espresso.*;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import java.time.LocalDate;
import edu.vassar.cmpu203.app.controller.ControllerActivity;

public class ViewDayTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule = new ActivityScenarioRule<>(ControllerActivity.class);

    /*
     * Instrumented test, which will execute on an Android device.
     * Testing for correct menu for multiple dates, proper overwriting of menusDisplay, caching of
     * menu previously searched for
     */
    @org.junit.Test
    public void searchDates(){
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
        SystemClock.sleep(30000); //Wait to fetch from website

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
        SystemClock.sleep(30000); //Wait to fetch from website

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



}
