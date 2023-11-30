package edu.vassar.cmpu203.app.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.model.Day;
import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.Menu;
import edu.vassar.cmpu203.app.model.User;

public class DayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DISH_VIEW_TYPE = 0;
    private static final int MENU_VIEW_TYPE = 1;

    private final DishViewHolder.Listener listener;

    final List<Object> menusAndDishes;
    final User user;

    public DayAdapter(Day day, User user, DishViewHolder.Listener listener) {
        this.listener = listener;
        this.menusAndDishes = new ArrayList<>();

        // Create list of menus
        List<Menu> menus = new ArrayList<>(day.getMenus().values());

        for (Menu menu : menus) {
            // Add menu to list
            this.menusAndDishes.add(menu);

            List<Dish> dishes = new ArrayList<>(menu.getDishes().values());
            // Add dish to list
            this.menusAndDishes.addAll(dishes);
        }

        // Set user
        this.user = user;

    }

    /**
     * Returns the view type of the item at position for the purposes of view recycling.
     * This allows for us to have different view types for menus and dishes.
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at position.
     */
    @Override
    public int getItemViewType(int position) {
        Object item = menusAndDishes.get(position);
        if (item instanceof Menu) {
            return MENU_VIEW_TYPE;
        } else if (item instanceof Dish) {
            return DISH_VIEW_TYPE;
        }
        // We should never get here since we only have menus and dishes
        return -1;
    }


    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type (MENU_VIEW_TYPE or DISH_VIEW_TYPE)
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == MENU_VIEW_TYPE) {
            view = inflater.inflate(R.layout.menu_name_view, parent, false);
            return new MenuViewHolder(view);
        } else if (viewType == DISH_VIEW_TYPE) {
            view = inflater.inflate(R.layout.dish_view, parent, false);
            return new DishViewHolder(view, this.listener);
        }
        // We should never get here since we only have menus and dishes
        return null;
    }


    /**
     * Called by RecyclerView to display the data at the specified position.
     * This is used to set the contents of the relevant ViewHolder.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int type = getItemViewType(position);
        if (type == MENU_VIEW_TYPE) {
            MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
            Menu menu = (Menu) menusAndDishes.get(position);
            menuViewHolder.bind(menu.getName());
        } else if (type == DISH_VIEW_TYPE) {
            DishViewHolder dishViewHolder = (DishViewHolder) holder;
            Dish dish = (Dish) menusAndDishes.get(position);
            dishViewHolder.bind(dish, this.user.isFavorite(dish));
        }

    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     * This is how the RecyclerView knows how many items are in the list.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return menusAndDishes.size();
    }


}
