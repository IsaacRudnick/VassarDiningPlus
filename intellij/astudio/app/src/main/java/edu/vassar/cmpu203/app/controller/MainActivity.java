package edu.vassar.cmpu203.app.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import edu.vassar.cmpu203.app.view.BrowseDayView;
import edu.vassar.cmpu203.app.view.IBrowseDayView;
import edu.vassar.cmpu203.app.model.*;

public class MainActivity extends AppCompatActivity implements IBrowseDayView.Listener{

    DayLibrary dayLibrary;
    User user;
    IBrowseDayView browseDayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create dayLibrary and empty user
        dayLibrary = new DayLibrary();
        user = new User(new ArrayList<>());

        // create view object
        BrowseDayView browsedayView = new BrowseDayView(this, this);
        // set screen contents
        setContentView(browsedayView.getRootView());
    }
    /* IBrowseDayView.Listener implementation start */

    /**
     * Update display to reflect menu.
     * @param date the string of the day to be displayed
     */
    @Override
    public void onDayRequested(String date) throws Exception{
        Day day = this.dayLibrary.getDay(date, this.user);

    }

    /* IBrowseDayView.Listener implementation end */


}