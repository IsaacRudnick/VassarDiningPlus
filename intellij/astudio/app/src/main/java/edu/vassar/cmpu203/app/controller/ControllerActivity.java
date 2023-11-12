package edu.vassar.cmpu203.app.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import edu.vassar.cmpu203.app.model.Day;
import edu.vassar.cmpu203.app.model.DayLibrary;
import edu.vassar.cmpu203.app.model.User;
import edu.vassar.cmpu203.app.view.IBrowseDayView;
import edu.vassar.cmpu203.app.view.IMainView;
import edu.vassar.cmpu203.app.view.MainView;
import edu.vassar.cmpu203.app.view.ViewDayFragment;

public class ControllerActivity extends AppCompatActivity implements IBrowseDayView.Listener {
    private DayLibrary days;
    private IMainView mainview;
    private User emptyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.days = new DayLibrary();
        this.emptyUser = new User(new ArrayList<String>());
        this.mainview = new MainView(this);
        setContentView(this.mainview.getRootView());
        this.mainview.displayFragment(new ViewDayFragment(this), false, "viewDay");
    }

    @Override
    public void onDayRequested(String date, IBrowseDayView browseDayView){
        // Handle input processing here
        if(validDate(date)) {
            try {
                Day day = this.days.getDay(date, emptyUser);
                browseDayView.updateDayDisplay(day);
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

        // Commented out for now because all parsing is handled by SimpleDateFormat
//        String[] splitDate = date.split("-");
//        if((splitDate.length != 3) || (splitDate[0].length() != 4) || (splitDate[1].length() != 2) || (splitDate[2].length() != 2)){
//            return false;
//        }

        // Try to parse given date. If there's an error (e.g. alpha chars in string), return false
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsed = LocalDate.parse(date, formatter);
            Log.d("Debug", parsed.toString());

            // Now check that the day and month are the same. In some instances (e.g. 2023-09-31 -> 2023-09-30 upon parsing) dates that are sometimes valid but not for that month are let through
            String day = Integer.toString(parsed.getDayOfMonth());
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
