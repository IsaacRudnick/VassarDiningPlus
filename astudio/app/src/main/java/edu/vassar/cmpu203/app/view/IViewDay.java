package edu.vassar.cmpu203.app.view;

import android.view.View;

import edu.vassar.cmpu203.app.model.Day;

public interface IViewDay {
    void updateDayDisplay(Day day, DishViewHolder.Listener listener);

    void onInvalidDate(View rootView);

    interface Listener {

        /**
         * Called when the user requests a day to be displayed.
         * @param date The date of the day to be displayed.
         * @param favoritesOnly Whether or not to only display favorites.
         * @param browseDayView The view that called this method.
         */
         void onDayRequested(String date, boolean favoritesOnly, IViewDay browseDayView);
    }
}
