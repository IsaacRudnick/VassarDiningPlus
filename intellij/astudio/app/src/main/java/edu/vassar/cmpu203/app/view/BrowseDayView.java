//package edu.vassar.cmpu203.app.view;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import edu.vassar.cmpu203.app.databinding.ActivityMainBinding;
//import edu.vassar.cmpu203.app.model.Day;
//
//public class BrowseDayView implements IBrowseDayView{
//    ActivityMainBinding binding;
//    Listener listener;
//
//    public BrowseDayView(Context context, Listener listener) {
//        this.listener = listener;
//        this.binding = ActivityMainBinding.inflate(LayoutInflater.from(context));
//        this.binding.dateInputButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                String date = binding.dateInputButton.getText().toString();
//                listener.onDayRquested(date); // let controller know!
//            }
//        });
//
//    }
//
//    @Override
//    public void updateDayDisplay(Day day){
//        binding.textView.setText(day.toString());
//    }
//
//    @Override
//    public View getRootView() {
//        return binding.getRoot();
//    }
//}
