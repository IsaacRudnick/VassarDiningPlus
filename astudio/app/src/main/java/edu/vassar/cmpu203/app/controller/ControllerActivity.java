package edu.vassar.cmpu203.app.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import edu.vassar.cmpu203.app.model.Day;
import edu.vassar.cmpu203.app.model.DayLibrary;
import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.Restriction;
import edu.vassar.cmpu203.app.model.User;
import edu.vassar.cmpu203.app.persistence.IPersistenceFacade;
import edu.vassar.cmpu203.app.persistence.LocalStorageFacade;
import edu.vassar.cmpu203.app.view.DishViewHolder;
import edu.vassar.cmpu203.app.view.IViewDay;
import edu.vassar.cmpu203.app.view.IMainView;
import edu.vassar.cmpu203.app.view.IManageProfile;
import edu.vassar.cmpu203.app.view.MainView;
import edu.vassar.cmpu203.app.view.ManageProfileFragment;
import edu.vassar.cmpu203.app.view.ViewDayFragment;

public class ControllerActivity extends AppCompatActivity implements IViewDay.Listener, IMainView.Listener, IManageProfile.Listener, DishViewHolder.Listener {
    private DayLibrary days;
    private IMainView mainview;
    /* keep track of the screen we are on so we don't reload if user clicks button to navigate to
     current screen */
    private String currScreen;

    private User saveduser;

    private IPersistenceFacade persistenceFacade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set up the view
        super.onCreate(savedInstanceState);

        this.persistenceFacade = new LocalStorageFacade(this.getFilesDir());
        // Load the saved user
        this.saveduser = this.persistenceFacade.loadUser();
        if(this.saveduser == null){
            this.saveduser = new User();
        }
        // Load the saved day library
        this.days = this.persistenceFacade.loadDayLibrary();
        if (this.days == null) {
            this.days = new DayLibrary();
        }
        // Set up the main view and display the ViewDayFragment as the default fragment
        this.mainview = new MainView(this, this);
        setContentView(this.mainview.getRootView());
        this.mainview.displayFragment(new ViewDayFragment(this), false, "viewDay");

        this.currScreen = "browse";
    }

    // METHOD TO RESET MEMORY FOR TESTING PURPOSES
    //=======================================================
    public void memoryReset(){
        this.days = new DayLibrary();
        this.saveduser = new User();
        this.mainview.displayFragment(new ViewDayFragment(this), false, "viewDay");
        this.currScreen = "browse";
        this.persistenceFacade.saveDayLibrary(this.days);
        this.persistenceFacade.saveUser(this.saveduser);
    }

    // BELOW METHODS ARE FOR HANDLING SWAPPING BETWEEN FRAGMENTS
    // ========================================================
    @Override
    public void onBrowseClick() {
        if(!(this.currScreen.equals("browse"))) {
            // Set up the view day fragment
            // Pass in the saved user so that the restrictions are set as they were before the app was closed
            ViewDayFragment viewDayFragment = new ViewDayFragment(this);
            this.mainview.displayFragment(viewDayFragment, false, "viewDay");
            this.currScreen = "browse";
        }
    }

    @Override
    public void onProfileClick() {
        if(!this.currScreen.equals("profile")) {
            ManageProfileFragment manageProfileFragment = new ManageProfileFragment(this, this.saveduser.getFavoritesAsMenu());
            this.mainview.displayFragment(manageProfileFragment, true, "manageProfile");
            this.currScreen = "profile";
        }
    }
    // ========================================================
    // END FRAGMENT HANDLING METHODS

    // BELOW METHODS ARE FOR HANDLING USER INPUT
    // ========================================

    /**
     * Method to handle when a day is requested by user pressing search button
     *
     * @param date - the date to get the day for (format "YYYY-MM-DD")
     * @param browseDayView - the view to update with the day
     */
    @Override
    // From IViewDay.Listener
    public void onDayRequested(String date, boolean onlyFavorites, IViewDay browseDayView){
        // Handle input processing here
        if(validDate(date)) {
            try {
                Day day = this.days.getDay(date);
                // If the user only wants to see their favorites, filter the day to only show favorites
                if (onlyFavorites) {
                    day = day.withOnlyFavoritesOf(this.saveduser);
                }
                this.persistenceFacade.saveDayLibrary(this.days);
                browseDayView.updateDayDisplay(day, this);
            } catch (Exception e) {
                Log.e("Error", "Error getting day (MainActivity -> onDayRequested)", e);
            }
        }
        else{
            browseDayView.onInvalidDate(this.mainview.getRootView());
        }
    }

    /**
     * Method to handle when a dish is toggled as a favorite
     * This is implemented for both the ViewDayFragment and the ManageProfileFragment,
     * since dishes can be toggled as favorites in both
     *
     * @param dish - the dish that was toggled
     */
    @Override
    // From IManageProfile.Listener and DishViewHolder.Listener
    public void toggleDishFavorited(Dish dish) {
        if (this.saveduser.isFavorite(dish)) {
            this.saveduser.removeFavorite(dish);
        } else {
            this.saveduser.addFavorite(dish);
        }
        this.persistenceFacade.saveUser(this.saveduser);
    }

    @Override
    // From IManageProfile.Listener and DishViewHolder.Listener
    public boolean isDishFavorited(Dish dish) {
        return this.saveduser.isFavorite(dish);
    }

    /**
     * Method to handle when the user updates their restrictions
     * @param restrictions - the new restrictions to set
     */
    @Override
    // From IManageProfile.Listener
    public void onUpdateRestrictions(List<Restriction> restrictions) {
        this.saveduser.setRestrictions(restrictions);
        this.days.setUserRestrictions(restrictions);
        this.persistenceFacade.saveUser(this.saveduser);
    }


    @Override
    // From IManageProfile.Listener
    public void checkSavedRestrictions(IManageProfile manageProfile) {
        manageProfile.setUserRestrictions(this.saveduser.getRestrictions());
    }


    /**
     * Method to handle when the favorites are requested
     * This is used by the ManageProfileFragment to update the favorites display
     * and avoids having control architecture inside the fragment by using the listener (this)
     * @param manageProfile - current view fragment
     */
    @Override
    // From IManageProfile.Listener
    public void onFavoritesRequested(IManageProfile manageProfile) {
        manageProfile.updateFavoritesDisplay();
    }

    // ========================================
    // END USER INPUT HANDLING METHODS


    // UTILITY METHODS
    // ========================================
    /**
     * check if an inputted date String is valid (format "YYYY-MM-DD")
     *
     * @param date - the date to check
     * @return - true if valid, false otherwise
     */
    public static boolean validDate(String date) {
        if (date.length() != 10) {
            return false;
        }

        // Try to parse given date. If there's an error (e.g. alpha chars in string), return false
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsed = LocalDate.parse(date, formatter);
            Log.d("Debug", parsed.toString());

            // Now check that the day and month are the same. In some instances (e.g. 2023-09-31 -> 2023-09-30 upon parsing) dates that are sometimes valid but not for that month are let through
            String day = Integer.toString(parsed.getDayOfMonth());
            // Add leading zero to day if necessary. This is to avoid instances where the day of the month is < 10
            // such as 2023-12-09, where date = "9" but splitDate[2] equals "09"
            if (day.length() == 1) {
                day = "0" + day;
            }
            String[] splitDate = date.split("-");
            if (!day.equals(splitDate[2])) {
                return false;
            }
        } catch (DateTimeParseException e) {
            Log.d("Debug", "Error parsing date", e);
            return false;
        }

        // If all tests pass, return true (valid DATE)
        return true;
    }

}
