package edu.vassar.cmpu203.app.view;

import java.util.List;

import edu.vassar.cmpu203.app.model.Day;

public interface IBrowseDayView {
    void updateDayDisplay(Day day, DishViewHolder.Listener listener);

    List<String> getCheckedRestrictions();

    interface Listener {
        /**
         * Called when the user requests a day to be displayed.
         * @param date The date of the day to be displayed.
         * @param browseDayView The view that called this method.
         */
         void onDayRequested(String date, IBrowseDayView browseDayView);

        /**
         * Called when the user updates the restrictions.
         * @param restrictions The list of restrictions that the user has checked.
         */
        void onUserUpdate(List<String> restrictions);
    }
}
