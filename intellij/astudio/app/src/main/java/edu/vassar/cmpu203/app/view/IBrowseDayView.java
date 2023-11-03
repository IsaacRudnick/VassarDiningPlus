package edu.vassar.cmpu203.app.view;

import edu.vassar.cmpu203.app.model.*;
import android.view.View;

/**
 * Interface that defines the methods for a view that allows a user to add items to a sale.
 */
public interface IBrowseDayView {

    /**
     * Interface that classes interested in being notified of events happening
     * to the view should implement.
     */
    interface Listener {
        /**
         * Called when an item is to be added onto the sale.
         *
         * @param date the date for which the menus should be pulled
         */
        void onDayRequested(String date) throws Exception;
    }

    /**
     * Returns the screen's top-level view.
     * @return The screen's top-level view.
     */
    View getRootView();

    /**
     * Update display to reflect menu.
     * @param day the string of the day to be displayed
     */
    void updateMenuDisplay(Day day);
}
