package edu.vassar.cmpu203.app.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.app.model.Day;
import edu.vassar.cmpu203.app.view.IBrowseDayView.Listener;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.databinding.FragmentViewDayBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDayFragment()}  method to
 * create an instance of this fragment.
 */
public class ViewDayFragment extends Fragment implements IBrowseDayView {

    private FragmentViewDayBinding binding;
    private Listener listener;
    public ViewDayFragment(Listener listener){ this.listener = listener; }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentViewDayBinding.inflate(inflater);
        return this.binding.getRoot();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // set up add item handler
        this.binding.dateInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = binding.dateInput.getText().toString();
                ViewDayFragment.this.listener.onDayRequested(date, ViewDayFragment.this); // let controller know!
            }
        });
    }
    public void updateDayDisplay(Day day){
        binding.menuTitle.setText(day.getDate());
        binding.menusDisplay.setText(day.toString());
    }

}