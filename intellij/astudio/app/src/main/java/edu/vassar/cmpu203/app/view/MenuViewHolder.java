package edu.vassar.cmpu203.app.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.vassar.cmpu203.app.databinding.MenuNameViewBinding;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    final MenuNameViewBinding binding;
    public final TextView menuName;

    public MenuViewHolder(View view) {
        super(view);

        this.binding = MenuNameViewBinding.bind(view);

        menuName = binding.menuNameTextView;
    }

    /**
     * Binds the given menu name to the view.
     * @param menuString The menu name to be shown.
     */
    public void bind(String menuString) {
        this.menuName.setText(menuString);
    }
}
