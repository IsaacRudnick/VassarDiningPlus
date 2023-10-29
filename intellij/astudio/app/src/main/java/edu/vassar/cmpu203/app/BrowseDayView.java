package edu.vassar.cmpu203.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import edu.vassar.cmpu203.app.databinding.ActivityMainBinding;

public class BrowseDayView {
    ActivityMainBinding binding;

    public BrowseDayView(Context context) {
        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));
    }

    public View getRootView() {return binding.getRoot();}
}