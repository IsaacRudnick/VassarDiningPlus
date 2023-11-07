package edu.vassar.cmpu203.app.view;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.vassar.cmpu203.app.databinding.ActivityMainBinding;


public class MainView implements IMainView{
    FragmentManager fmanager;
    ActivityMainBinding binding;

    public MainView(FragmentActivity activity) {
        this.fmanager = activity.getSupportFragmentManager();
        this.binding = ActivityMainBinding.inflate(activity.getLayoutInflater());
    }

    @Override
    public View getRootView(){
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(Fragment fragment, boolean addToStack, String name) {
        FragmentTransaction ft = this.fmanager.beginTransaction()
                .replace(this.binding.fragmentContainerView.getId(), fragment);

        if (addToStack) {
            ft.addToBackStack(name);
        }

        ft.commit();
    }
}
