package edu.vassar.cmpu203.app.model;

import androidx.annotation.NonNull;

import java.util.*;

public class Menu implements Iterable<Dish>{
    private final String name;
    private final Map<String, Dish> dishes;

    public Menu(String name) {
        this.name = name;
        this.dishes = new HashMap<>();
    }

    /*
     * @return A string representation of the menu
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Menu: ").append(name).append("\n\t");
        for (String key : dishes.keySet()) {
            Dish dish = dishes.get(key);
            // Indent the entire dish by one tab (line by line)
            output.append(dish.toString().replaceAll("\n", "\n\t"));
        }
        output.append("\n");
        return output.toString();
    }

    /*
     * Adds a dish to the menu with the key as the dish's ID
     * 
     * @param dish The dish to add
     */
    public void addDish(Dish dish) {
        dishes.put(dish.getName(), dish);
    }

    public String getName() {
        return name;
    }

    public Map<String, Dish> getDishes() {
        return dishes;
    }

    @NonNull
    @Override
    public Iterator<Dish> iterator() {
        return new MenuIterator<>(this);
    }
    public static class MenuIterator<Dish> implements Iterator<Dish> {
        private final Menu menu;
        private final List<Dish> dishes;
        private int index;

        public MenuIterator(Menu menu) {
            this.menu = menu;
            this.dishes = new ArrayList(menu.getDishes().values());
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < menu.getDishes().size();
        }

        @Override
        public Dish next() {
            return dishes.get(index++);
        }
    }

}

