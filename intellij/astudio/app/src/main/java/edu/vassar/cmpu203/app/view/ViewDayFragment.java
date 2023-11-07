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
 * Use the {@link ViewDayFragment#newInstance} factory method to
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
                String date = binding.dateInputButton.getText().toString();
                listener.onDayRequested(date, ViewDayFragment.this); // let controller know!
            }
        });
    }
    public void updateDayDisplay(Day day){
        binding.menusDisplay.setText(day.toString());
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewDayFragment newInstance(String param1, String param2) {
        ViewDayFragment fragment = new ViewDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}