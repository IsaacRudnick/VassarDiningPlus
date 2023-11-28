//package edu.vassar.cmpu203.app.view;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import edu.vassar.cmpu203.app.databinding.DishViewBinding;
//
//public class MyViewHolder extends RecyclerView.ViewHolder {
//
//    TextView dishName;
//    ImageView dishHeartImage;
//
//    private static final int DISH_VIEW_TYPE = 0;
//    private static final int MENU_VIEW_TYPE = 1;
//
//    public MyViewHolder(NonNull ViewGroup parent, int viewType) {
//        super(parent);
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view;
//
//        if (viewType == DISH_VIEW_TYPE) {
//            view = inflater.inflate(R.layout.dish_view, parent, false);
//            binding = DishViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        }
//        else if (viewType == MENU_VIEW_TYPE) {
//            binding = DishViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        }
//    }
//
//    public class DishViewHolder extends RecyclerView.ViewHolder {
//        DishViewBinding binding;
//        public TextView dishName;
//        public ImageView dishHeartImage;
//
//        public DishViewHolder(@NonNull DishViewBinding binding) {
//            super(binding.getRoot());
//
//            this.binding = binding;
//
//            dishName = binding.dishTextView;
//            dishHeartImage = binding.dishImageView;
//
//        }
//    }
//
//    public class MenuViewHolder extends RecyclerView.ViewHolder {
//        DishViewBinding binding;
//        public TextView menuName;
//
//        public MenuViewHolder(@NonNull DishViewBinding binding) {
//            super(binding.getRoot());
//
//            this.binding = binding;
//
//            menuName = binding.menuNameTextView;
//        }
//    }
//}
