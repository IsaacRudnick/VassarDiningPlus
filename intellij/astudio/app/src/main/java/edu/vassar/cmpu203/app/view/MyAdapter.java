package edu.vassar.cmpu203.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.model.Day;
import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.Menu;
import edu.vassar.cmpu203.app.model.User;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DISH_VIEW_TYPE = 0;
    private static final int MENU_VIEW_TYPE = 1;

    private DishViewHolder.Listener listener;

    Context context;
    List<Object> menusAndDishes;
    User user;

    public MyAdapter(Context context, Day day, User user, DishViewHolder.Listener listener) {
        this.context = context;
        this.listener = listener;
        this.menusAndDishes = new ArrayList<>();

        // Create list of menus
        List<Menu> menus = new ArrayList(day.getMenus().values());

        for (Menu menu : menus) {
            // Add menu to list
            this.menusAndDishes.add(menu);

            List<Dish> dishes = new ArrayList(menu.getDishes().values());
            for (Dish dish : dishes) {
                // Add dish to list
                this.menusAndDishes.add(dish);
            }
        }

        // Set user
        this.user = user;

    }

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
        // We should never get here
        return null;
    }


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



    @Override
    public int getItemCount() {
        return menusAndDishes.size();
    }


}
