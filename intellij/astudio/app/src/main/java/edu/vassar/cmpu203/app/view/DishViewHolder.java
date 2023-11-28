package edu.vassar.cmpu203.app.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.databinding.DishViewBinding;

public class DishViewHolder extends RecyclerView.ViewHolder {
    DishViewBinding binding;
    public TextView dishText;
    public ImageView dishHeartImage;

    public DishViewHolder(View view) {
        super(view);

        this.binding = DishViewBinding.bind(view);

        dishText = binding.dishTextView;
        dishHeartImage = binding.dishImageView;
    }

    public void bind(String dishName, boolean favorited) {
        this.dishText.setText(dishName);
        if (favorited) {
            this.dishHeartImage.setImageResource(R.drawable.red_heart);
        } else {
            this.dishHeartImage.setImageResource(R.drawable.gray_heart);
        }
    }
}
