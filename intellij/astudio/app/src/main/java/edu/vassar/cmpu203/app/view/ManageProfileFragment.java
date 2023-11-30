package edu.vassar.cmpu203.app.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.vassar.cmpu203.app.databinding.FragmentManageProfileBinding;
import edu.vassar.cmpu203.app.model.Menu;
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

    private final Menu favoritesAsMenu;

    /**
     * Constructor for ManageProfileFragment class that takes in a listener and a saved user
     *
     * @param listener - the listener to set (used to communicate with the controller)
     * @param savedUser - the user to load (used to set the restrictions and favorites on the view)
     */
    public ManageProfileFragment(Listener listener, User savedUser) {
        this.listener = listener;
        this.savedUser = savedUser;
        this.favoritesAsMenu = savedUser.getFavoritesAsMenu();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentManageProfileBinding.inflate(inflater, container, false);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


        // Load the saved user and check the restrictions that were previously checked
        if (savedUser != null) {
            this.setUserRestrictions();
        }

        // Let the controller know that the view has been created
        this.listener.onFavoritesRequested(this);

        View.OnClickListener userUpdateListener = view1 -> {
            List<String> restrictions = getCheckedRestrictions();
            ManageProfileFragment.this.listener.onUserUpdate(restrictions);
        };
        this.binding.vegetarianButton.setOnClickListener(userUpdateListener);
        this.binding.veganButton.setOnClickListener(userUpdateListener);
        this.binding.halalButton.setOnClickListener(userUpdateListener);
        this.binding.inBalanceButton.setOnClickListener(userUpdateListener);
        this.binding.kosherButton.setOnClickListener(userUpdateListener);
        this.binding.lowGlutenButton.setOnClickListener(userUpdateListener);
    }

    /**
     * Updates the favorites display to show the user's favorites
     */
    @Override
    public void updateFavoritesDisplay() {
        RecyclerView favoritesRecyclerView = this.binding.favoritesRecyclerView;
        favoritesRecyclerView.setAdapter(new FavoritesAdapter(this.favoritesAsMenu, this.savedUser, this.listener));
        // Set the favorites recycler view
        this.binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    /**
     * Sets the checked restrictions on the view to match the user's saved restrictions
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

    /**
     * Gets the restrictions that the user has checked
     * @return the list of restrictions that the user has checked
     */
    public List<String> getCheckedRestrictions() {
        boolean vegetarianChecked = this.binding.vegetarianButton.isChecked();
        boolean veganChecked = this.binding.veganButton.isChecked();
        boolean halalChecked = this.binding.halalButton.isChecked();
        boolean inBalanceChecked = this.binding.inBalanceButton.isChecked();
        boolean kosherChecked = this.binding.kosherButton.isChecked();
        boolean lowGlutenChecked = this.binding.lowGlutenButton.isChecked();

        List<String> restrictions = new ArrayList<>();
        if (vegetarianChecked) {
            restrictions.add("Vegetarian");
        }
        if (veganChecked) {
            restrictions.add("Vegan");
        }
        if (halalChecked) {
            restrictions.add("Halal");
        }
        if (inBalanceChecked) {
            restrictions.add("In Balance");
        }
        if (kosherChecked) {
            restrictions.add("Kosher");
        }
        if (lowGlutenChecked) {
            restrictions.add("Made without Gluten-Containing Ingredients");
        }
        return restrictions;
    }

}