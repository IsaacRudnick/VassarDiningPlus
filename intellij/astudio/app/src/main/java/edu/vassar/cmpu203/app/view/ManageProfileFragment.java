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
import edu.vassar.cmpu203.app.model.Restriction;
import edu.vassar.cmpu203.app.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageProfileFragment())} factory method to
 * create an instance of this fragment.
 */
public class ManageProfileFragment extends Fragment implements IManageProfile{

    private FragmentManageProfileBinding binding;
    private final IManageProfile.Listener listener;
    private final Menu favoritesAsMenu;

    /**
     * Constructor for ManageProfileFragment class that takes in a listener and a saved user
     *
     * @param listener - the listener to set (used to communicate with the controller)
     */
    public ManageProfileFragment(Listener listener, Menu favoritesAsMenu) {
        this.listener = listener;
        this.favoritesAsMenu = favoritesAsMenu;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentManageProfileBinding.inflate(inflater, container, false);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        // Load the saved user and check the restrictions that were previously checked
        listener.checkSavedRestrictions(this);

        // Let the controller know that the view has been created
        this.listener.onFavoritesRequested(this);

        View.OnClickListener userUpdateListener = view1 -> {
            List<Restriction> restrictions = getCheckedRestrictions();
            ManageProfileFragment.this.listener.onUpdateRestrictions(restrictions);
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
        favoritesRecyclerView.setAdapter(new FavoritesAdapter(this.favoritesAsMenu, this.listener));
        // Set the favorites recycler view
        this.binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    /**
     * Sets the checked restrictions on the view to match the user's saved restrictions
     */
    @Override
    public void setUserRestrictions(List<Restriction> restrictions) {
        for (Restriction restriction : restrictions) {
            switch (restriction) {
                case VEGETARIAN:
                    this.binding.vegetarianButton.setChecked(true);
                    break;
                case VEGAN:
                    this.binding.veganButton.setChecked(true);
                    break;
                case HALAL:
                    this.binding.halalButton.setChecked(true);
                    break;
                case IN_BALANCE:
                    this.binding.inBalanceButton.setChecked(true);
                    break;
                case KOSHER:
                    this.binding.kosherButton.setChecked(true);
                    break;
                case LOW_GLUTEN:
                    this.binding.lowGlutenButton.setChecked(true);
                    break;
            }
        }
    }

    /**
     * Gets the restrictions that the user has checked
     * @return the list of restrictions that the user has checked
     */
    public List<Restriction> getCheckedRestrictions() {
        boolean vegetarianChecked = this.binding.vegetarianButton.isChecked();
        boolean veganChecked = this.binding.veganButton.isChecked();
        boolean halalChecked = this.binding.halalButton.isChecked();
        boolean inBalanceChecked = this.binding.inBalanceButton.isChecked();
        boolean kosherChecked = this.binding.kosherButton.isChecked();
        boolean lowGlutenChecked = this.binding.lowGlutenButton.isChecked();

        List<Restriction> restrictions = new ArrayList<>();
        if (vegetarianChecked) {
            restrictions.add(Restriction.VEGETARIAN);
        }
        if (veganChecked) {
            restrictions.add(Restriction.VEGAN);
        }
        if (halalChecked) {
            restrictions.add(Restriction.HALAL);
        }
        if (inBalanceChecked) {
            restrictions.add(Restriction.IN_BALANCE);
        }
        if (kosherChecked) {
            restrictions.add(Restriction.KOSHER);
        }
        if (lowGlutenChecked) {
            restrictions.add(Restriction.LOW_GLUTEN);
        }
        return restrictions;
    }

}