package edu.vassar.cmpu203.app.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.Menu;
import edu.vassar.cmpu203.app.model.User;

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final IManageProfile.Listener listener;

    final List<Dish> dishes;

    public FavoritesAdapter(Menu menu, IManageProfile.Listener listener) {
        this.listener = listener;

        // Create list of dishes
        dishes = new ArrayList<>(menu.getDishes().values());

    }

    /**
     * Creates a new ViewHolder for a dish view.
     * This is called by the RecyclerView to create a new ViewHolder.
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View. We only have one view type, so this is ignored.
     *
     * @return A new FavoritesViewHolder that holds a dish view.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.dish_view, parent, false);
        return new FavoritesViewHolder(view, this.listener);
    }


    /**
     * Binds the dish to the view holder, which will update the text and heart image.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FavoritesViewHolder favoritesViewHolder = (FavoritesViewHolder) holder;
        Dish dish = dishes.get(position);
        boolean favorited = this.listener.isDishFavorited(dish);
        favoritesViewHolder.bind(dish, favorited);
    }



    @Override
    public int getItemCount() {
        return dishes.size();
    }


}
