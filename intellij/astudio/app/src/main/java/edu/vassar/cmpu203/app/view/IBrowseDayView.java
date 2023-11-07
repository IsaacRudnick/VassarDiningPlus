package edu.vassar.cmpu203.app.view;

import android.view.View;

import edu.vassar.cmpu203.app.model.Day;

public interface IBrowseDayView {
    public void updateDayDisplay(Day day);

    interface Listener {
        void onDayRequested(String date, IBrowseDayView browseDayView);
    }
}
