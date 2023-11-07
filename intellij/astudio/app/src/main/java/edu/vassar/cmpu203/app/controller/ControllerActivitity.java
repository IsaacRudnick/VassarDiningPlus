package edu.vassar.cmpu203.app.controller;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.vassar.cmpu203.app.model.Day;
import edu.vassar.cmpu203.app.model.DayLibrary;
import edu.vassar.cmpu203.app.model.User;
import edu.vassar.cmpu203.app.view.IBrowseDayView;
import edu.vassar.cmpu203.app.view.IMainView;
import edu.vassar.cmpu203.app.view.MainView;
import edu.vassar.cmpu203.app.view.ViewDayFragment;

public class ControllerActivitity extends AppCompatActivity implements IBrowseDayView.Listener {
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
        //TODO currently the app "runs" but searching does nothing
        // This log outputs an A, meaning we get here. Go through iterative process to
        // figure out where the issue is.
        // Handle input processing here
        try {
            Day day = this.days.getDay(date, emptyUser);
            browseDayView.updateDayDisplay(day);
        } catch (Exception e) {
            Log.e("Error", "Error getting day (MainActivity -> onDayRequested)", e);
        }
    }


}
