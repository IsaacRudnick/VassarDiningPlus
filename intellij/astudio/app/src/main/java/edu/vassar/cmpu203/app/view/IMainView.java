package edu.vassar.cmpu203.app.view;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface IMainView {
    interface Listener{
        /**
         * Called when the user clicks the browse button (tab).
         */
        void onBrowseClick();

        /**
         * Called when the user clicks the profile button (tab).
         */
        void onProfileClick();
    }
    /**
     * Returns the root view of the main view.
     * @return The root view of the main view.
     */
    View getRootView();

    /**
     * Displays the given fragment.
     * @param fragment The fragment to be displayed.
     * @param addToStack Whether or not to add the fragment to the back stack.
     * @param name The name of the fragment.
     */
    void displayFragment(Fragment fragment, boolean addToStack, String name);
}
