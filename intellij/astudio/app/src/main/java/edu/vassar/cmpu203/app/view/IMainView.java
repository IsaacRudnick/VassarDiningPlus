package edu.vassar.cmpu203.app.view;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface IMainView {
    interface Listener{
        void onBrowseClick();
        void onProfileClick();
    }
    View getRootView();
    void displayFragment(Fragment fragment, boolean addToStack, String name);
}
