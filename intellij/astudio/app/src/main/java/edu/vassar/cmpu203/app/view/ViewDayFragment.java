package edu.vassar.cmpu203.app.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import edu.vassar.cmpu203.app.model.Day;

import edu.vassar.cmpu203.app.databinding.FragmentViewDayBinding;
import edu.vassar.cmpu203.app.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDayFragment()}  method to
 * create an instance of this fragment.
 */
public class ViewDayFragment extends Fragment implements IBrowseDayView {

    private FragmentViewDayBinding binding;
    private final Listener listener;

    private final User savedUser;

    /**
     * Constructor for ViewDayFragment class that takes in a listener and a saved user
     *
     * @param listener - the listener to set (used to communicate with the controller)
     * @param savedUser - the user to load (used to set the restrictions on the view)
     */
    public ViewDayFragment(Listener listener, User savedUser){
        this.listener = listener;
        this.savedUser = savedUser;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentViewDayBinding.inflate(inflater, container, false);

        return this.binding.getRoot();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Generate current date as YYYY-MM-DD
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = dateObj.format(formatter);
        // Set the date input to today's date
        this.binding.dateInput.setText(today);

        // set up add item handler so when the search button is clicked, the controller is notified
        this.binding.dateInputButton.setOnClickListener(view1 -> {
            String date = binding.dateInput.getText().toString();
            ViewDayFragment.this.listener.onDayRequested(date, ViewDayFragment.this); // let controller know!
        });

        View.OnClickListener userUpdateListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> restrictions = getCheckedRestrictions();
                ViewDayFragment.this.listener.onUserUpdate(restrictions);
            }
        };
        this.binding.vegetarianButton.setOnClickListener(userUpdateListener);
        this.binding.veganButton.setOnClickListener(userUpdateListener);
        this.binding.halalButton.setOnClickListener(userUpdateListener);
        this.binding.inBalanceButton.setOnClickListener(userUpdateListener);
        this.binding.kosherButton.setOnClickListener(userUpdateListener);
        this.binding.lowGlutenButton.setOnClickListener(userUpdateListener);


        // Load the saved user and check the restrictions that were previously checked
        if (savedUser != null) {
            this.setCheckedRestrictions(savedUser);
        }

        // Let the controller know that the view has been created
        this.listener.onDayRequested(today, this);

    }
    public void updateDayDisplay(Day day, DishViewHolder.Listener listener) {
        RecyclerView recyclerView = this.binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new DayAdapter(this.getContext(), day, this.savedUser, listener));
    }

    public List<String> getCheckedRestrictions() {
        boolean vegetarianChecked = this.binding.vegetarianButton.isChecked();
        boolean veganChecked = this.binding.veganButton.isChecked();
        boolean halalChecked = this.binding.halalButton.isChecked();
        boolean inBalanceChecked = this.binding.inBalanceButton.isChecked();
        boolean kosherChecked = this.binding.kosherButton.isChecked();
        boolean lowGlutenChecked = this.binding.lowGlutenButton.isChecked();

        List<String> restrictions = new ArrayList<String>();
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

    /**
     * Sets the checked restrictions on the view
     *
     * @param user - the user to get the restrictions from
     */
    private void setCheckedRestrictions(User user) {
        List<String> restrictions = user.getRestrictions();
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