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
        // NB: AndroidStudio says that this method is not implemented, but it is implemented in ControllerActivity.
        // The reason for this is that the FavoritesViewHolder.Listener interface has the same method signature
        // as the DishViewHolder.Listener interface. The ControllerActivity class uses the same method to implement both.
        // To avoid relying on IBrowseDayView.Listener, we specify the method here to avoid confusion and ensure best practices.
        void onDishToggle(Dish dish);
    }

    private final IManageProfile.Listener listener;

    final DishViewBinding binding;
    public final TextView dishText;
    public final ImageView dishHeartImage;

    public FavoritesViewHolder(View view, IManageProfile.Listener listener) {
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

        this.dishHeartImage.setOnClickListener(v -> {
            FavoritesViewHolder.this.bind(dish, !favorited);
            listener.onDishToggle(dish);
        });
    }
}
