//package edu.vassar.cmpu203.app.controller;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.snackbar.Snackbar;
//
//import java.util.ArrayList;
//
//import edu.vassar.cmpu203.app.model.Day;
//import edu.vassar.cmpu203.app.model.DayLibrary;
//import edu.vassar.cmpu203.app.model.User;
//import edu.vassar.cmpu203.app.view.BrowseDayView;
//import edu.vassar.cmpu203.app.view.IBrowseDayView;
//
//public class MainActivity extends AppCompatActivity implements IBrowseDayView.Listener {
//
//    IBrowseDayView browseDayView;
//    DayLibrary days;
//    User emptyUser; // empty user for now; will add profile later.
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        this.days = new DayLibrary();
//        this.emptyUser = new User(new ArrayList<String>());
//        super.onCreate(savedInstanceState);
//        browseDayView = new BrowseDayView(this, this);
//        setContentView(browseDayView.getRootView());
//    }
//
//    @Override
//    public void onDayRequested(String date){
//        // Handle input processing here
//        try {
//            Day day = this.days.getDay(date, emptyUser);
//            browseDayView.updateDayDisplay(day);
//        } catch (Exception e) {
//            Log.e("Error", "Error getting day (MainActivity -> onDayRequested)", e);
//        }
//    }
//}
