package edu.vassar.cmpu203.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import edu.vassar.cmpu203.app.R;
import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.app.databinding.ActivityMainBinding;
import android.text.Editable;

import edu.vassar.cmpu203.app.model.*;

public class BrowseDayView implements IBrowseDayView{
    ActivityMainBinding binding; // gives us access to the graphical elements defined in res/layout/activity_main.xml
    Listener listener; // object to be notified of events of interest that happen in the UI

    /**
     * Initializes the add items view.
     *
     * @param context the context under which the UI will run
     * @param listener the object to be notified when an event of interest occurs
     */
    public BrowseDayView(Context context, Listener listener){

        this.listener = listener;

        // inflate the layout
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));

        // register add button click listener
        this.binding.dateInputButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when associated button is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                // retrieves date inputted
                final Editable dateInputEditable = BrowseDayView.this.binding.dateInput.getText();
                final String dateStr = dateInputEditable.toString();


                // confirm we have valid date. Otherwise toast an error message
                if (dateStr.length() == 0) {
                    Snackbar.make(v, v.getContext().getString(R.string.invalid_date),
                            Snackbar.LENGTH_LONG).show();
                    return;
                }

                // clear the input fields to ready them for the next item
                dateInputEditable.clear();

                try {
                    // notify listener (controller)
                    BrowseDayView.this.listener.onDayRequested(dateStr);
                } catch (Exception e) {
                    System.out.println("Error" + e);
                }
            }
        });
    }

    /**
     * Returns the screen's top-level view.
     * @return The screen's top-level view.
     */
    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    /**
     * Update display to reflect contents of the sale passed as an argument.
     * @param day the day of menus to be displayed
     */
    @Override
    public void updateMenuDisplay(Day day) {
        this.binding.menuDisplay.setText(day.toString());
    }

}