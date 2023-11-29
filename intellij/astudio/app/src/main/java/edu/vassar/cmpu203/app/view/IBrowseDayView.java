package edu.vassar.cmpu203.app.view;

import java.util.List;

import edu.vassar.cmpu203.app.model.Day;

public interface IBrowseDayView {
    void updateDayDisplay(Day day, DishViewHolder.Listener listener);

    List<String> getCheckedRestrictions();

    interface Listener {
        void onDayRequested(String date, IBrowseDayView browseDayView);
    }
}
