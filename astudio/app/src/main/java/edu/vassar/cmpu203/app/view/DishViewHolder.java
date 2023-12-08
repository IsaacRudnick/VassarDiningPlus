package edu.vassar.cmpu203.app.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.databinding.DishViewBinding;
import edu.vassar.cmpu203.app.model.Dish;

public class DishViewHolder extends RecyclerView.ViewHolder {

    public interface Listener {
        /**
         * Returns whether the dish is favorited
         * @param dish
         * @return true if the dish is favorited, false otherwise
         */
        boolean isDishFavorited(Dish dish);

        /**
         * Toggles whether the dish is favorited
         * @param dish The dish to toggle
         */
        void toggleDishFavorited(Dish dish);
    }

    private final DishViewHolder.Listener listener;

    final DishViewBinding binding;
    public final TextView dishText;
    public final ImageView dishHeartImage;

    public DishViewHolder(View view, DishViewHolder.Listener listener) {
        super(view);

        this.listener = listener;

        this.binding = DishViewBinding.bind(view);

        dishText = binding.dishTextView;
        dishHeartImage = binding.dishImageView;


    }

    /**
     * Binds the dish to the view holder, which will update the text and heart image.
     * @param dish The dish to represent
     * @param favorited Whether the dish is favorited
     */
    public void bind(Dish dish, boolean favorited) {

        String dishName = dish.getName();

        this.dishText.setText(dishName);
        if (favorited) {
            this.dishHeartImage.setImageResource(R.drawable.red_heart);
        } else {
            this.dishHeartImage.setImageResource(R.drawable.gray_heart);
        }

        this.dishHeartImage.setTag(dishName.toLowerCase());

        this.dishHeartImage.setOnClickListener(v -> {
            DishViewHolder.this.bind(dish, !favorited);
            listener.toggleDishFavorited(dish);
        });
    }
}
