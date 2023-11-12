package edu.vassar.cmpu203.app.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

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

    public static boolean validDate(String date){
        if(date.length() != 10){
            return false;
        }

        // Commented out for now because all parsing is handled by SimpleDateFormat
//        String[] splitDate = date.split("-");
//        if((splitDate.length != 3) || (splitDate[0].length() != 4) || (splitDate[1].length() != 2) || (splitDate[2].length() != 2)){
//            return false;
//        }

        // Try to parse given date. If there's an error (e.g. alpha chars in string), return false
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // Var is unused
            java.util.Date parsedDate = format.parse(date);
        } catch (ParseException e) {
            return false;
        }


        // If all tests pass, return true (valid DATE)
        return true;
    }


}
