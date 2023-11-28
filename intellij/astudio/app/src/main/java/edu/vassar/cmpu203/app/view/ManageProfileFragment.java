package edu.vassar.cmpu203.app.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.databinding.FragmentManageProfileBinding;
import edu.vassar.cmpu203.app.databinding.FragmentViewDayBinding;
import edu.vassar.cmpu203.app.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageProfileFragment())} factory method to
 * create an instance of this fragment.
 */
public class ManageProfileFragment extends Fragment implements IManageProfile{

    private FragmentManageProfileBinding binding;
    private final Listener listener;
    private final User savedUser;

    /**
     * Constructor for ManageProfileFragment class that takes in a listener and a saved user
     *
     * @param listener - the listener to set (used to communicate with the controller)
     * @param savedUser - the user to load (used to set the restrictions on the view)
     */
    public ManageProfileFragment(Listener listener, User savedUser) {
        this.listener = listener;
        this.savedUser = savedUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentManageProfileBinding.inflate(inflater);
        this.setUserRestrictions();
        return this.binding.getRoot();
    }

    /**
     * Sets the checked restrictions on the view
     *
     */
    @Override
    public void setUserRestrictions() {
        List<String> restrictions = this.savedUser.getRestrictions();
        for (String restriction : restrictions) {
            switch (restriction) {
                case "Vegetarian":
                    this.binding.vegetarianButton.setChecked(true);
                    break;
                case "Vegan":
                    this.binding.veganButton.setChecked(true);
                    break;
                case "Halal":
                    this.binding.halalButton.setChecked(true);
                    break;
                case "In Balance":
                    this.binding.inBalanceButton.setChecked(true);
                    break;
                case "Kosher":
                    this.binding.kosherButton.setChecked(true);
                    break;
                case "Made without Gluten-Containing Ingredients":
                    this.binding.lowGlutenButton.setChecked(true);
                    break;
            }
        }
    }

}