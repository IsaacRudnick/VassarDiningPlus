package edu.vassar.cmpu203.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    BrowseDayView browseDayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create view object
        BrowseDayView browsedayView = new BrowseDayView(this);
        // set screen contents
        setContentView(browsedayView.getRootView());
    }
}