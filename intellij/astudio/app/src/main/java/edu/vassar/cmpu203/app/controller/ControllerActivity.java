package edu.vassar.cmpu203.app.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import edu.vassar.cmpu203.app.model.Day;
import edu.vassar.cmpu203.app.model.DayLibrary;
import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.User;
import edu.vassar.cmpu203.app.persistence.IPersistenceFacade;
import edu.vassar.cmpu203.app.persistence.LocalStorageFacade;
import edu.vassar.cmpu203.app.view.DishViewHolder;
import edu.vassar.cmpu203.app.view.IBrowseDayView;
import edu.vassar.cmpu203.app.view.IMainView;
import edu.vassar.cmpu203.app.view.IManageProfile;
import edu.vassar.cmpu203.app.view.MainView;
import edu.vassar.cmpu203.app.view.ManageProfileFragment;
import edu.vassar.cmpu203.app.view.ViewDayFragment;

public class ControllerActivity extends AppCompatActivity implements IBrowseDayView.Listener, IMainView.Listener, IManageProfile.Listener, DishViewHolder.Listener {
    private DayLibrary days;
    private IMainView mainview;
    /* keep track of the screen we are on so we don't reload if user clicks button to navigate to
     current screen */
    private String curScreen;

    private User saveduser;

    private IPersistenceFacade persistenceFacade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.persistenceFacade = new LocalStorageFacade(this.getFilesDir());
        User saveduser = this.persistenceFacade.loadUser();
        if(saveduser == null){
            saveduser = new User();
        }
        this.saveduser = saveduser;


        this.days = new DayLibrary();
        this.mainview = new MainView(this, this);
        setContentView(this.mainview.getRootView());
        this.mainview.displayFragment(new ViewDayFragment(this, saveduser), false, "viewDay");
        this.curScreen = "browse";
    }

    @Override
    public void onBrowseClick() {
        if(this.curScreen != "browse") {
            // Set up the view day fragment
            // Pass in the saved user so that the restrictions are set as they were before the app was closed
            ViewDayFragment viewDayFragment = new ViewDayFragment(this, this.persistenceFacade.loadUser());
            this.mainview.displayFragment(viewDayFragment, false, "viewDay");
            this.curScreen = "browse";
        }
    }

    @Override
    public void onProfileClick() {
        if(this.curScreen != "profile") {
            ManageProfileFragment manageProfileFragment = new ManageProfileFragment(this, this.persistenceFacade.loadUser());
            this.mainview.displayFragment(manageProfileFragment, true, "manageProfile");
            this.curScreen = "profile";
        }
    }

    @Override
    public void onDayRequested(String date, IBrowseDayView browseDayView){
        List<String> checkedRestrictions = browseDayView.getCheckedRestrictions();
        this.days.setUser(new User(checkedRestrictions));
        this.persistenceFacade.saveUser(new User(checkedRestrictions));


        // Handle input processing here
        if(validDate(date)) {
            try {
                Day day = this.days.getDay(date);
                browseDayView.updateDayDisplay(day, this);
            //    Log.e("Vassar",String.valueOf(day.toString().contains("Vegan White Bean And Chickpea Soup")));
            } catch (Exception e) {
                Log.e("Error", "Error getting day (MainActivity -> onDayRequested)", e);
            }
        }
        else{
            Snackbar.make(this.mainview.getRootView(), "Invalid date! Use the format YYYY-MM-DD", Snackbar.LENGTH_LONG).show();
        }
    }

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

    @Override
    public void onDishToggle(Dish dish) {
        if (this.saveduser.isFavorite(dish)) {
            this.saveduser.removeFavorite(dish);
        } else {
            this.saveduser.addFavorite(dish);
        }
        this.persistenceFacade.saveUser(this.saveduser);
    }

    @Override
    public void onRemoveFavorite(Dish dish) {

    }

    @Override
    public void onAddFavorite(Dish dish) {

    }

    @Override
    public void onRestrictionCheck(String restriction) {

    }

    @Override
    public void onUserUpdate(List<String> restrictions) {
        this.saveduser.setRestrictions(restrictions);
        this.persistenceFacade.saveUser(this.saveduser);
    }
}
