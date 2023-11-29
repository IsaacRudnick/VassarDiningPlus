package edu.vassar.cmpu203.app.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.databinding.DishViewBinding;
import edu.vassar.cmpu203.app.model.Dish;

public class FavoritesViewHolder extends RecyclerView.ViewHolder {

    public interface Listener {
        void onDishToggle(Dish dish);
    }

    private final IManageProfile.Listener listener;

    DishViewBinding binding;
    public TextView dishText;
    public ImageView dishHeartImage;

    public Dish dish;

    public FavoritesViewHolder(View view, IManageProfile.Listener listener) {
        super(view);

        this.listener = listener;

        this.binding = DishViewBinding.bind(view);

        dishText = binding.dishTextView;
        dishHeartImage = binding.dishImageView;


    }

    public void bind(Dish dish, boolean favorited) {
        this.dish = dish;

        String dishName = dish.getName();

        this.dishText.setText(dishName);
        if (favorited) {
            this.dishHeartImage.setImageResource(R.drawable.red_heart);
        } else {
            this.dishHeartImage.setImageResource(R.drawable.gray_heart);
        }

        this.dishHeartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritesViewHolder.this.bind(dish, !favorited);
                listener.onDishToggle(dish);
            }
        });
    }
}
