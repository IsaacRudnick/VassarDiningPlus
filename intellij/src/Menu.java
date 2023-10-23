import java.util.*;

public class Menu {
    private String name;
    private Map<String, Dish> dishes;

    public Menu(String name) {
        this.name = name;
        this.dishes = new HashMap<String, Dish>();
    }

    /*
     * @return A string representation of the menu
     */
    @Override
    public String toString() {
        String output = "";
        output += "Menu: " + name + "\n\t";
        for (String key : dishes.keySet()) {
            Dish dish = dishes.get(key);
            // Indent the entire dish by one tab (line by line)
            output += dish.toString().replaceAll("\n", "\n\t");
        }
        output += "\n";
        return output;
    }

    /*
     * Adds a dish to the menu with the key as the dish's ID
     * 
     * @param dish The dish to add
     */
    public void addDish(Dish dish) {
        dishes.put(dish.getId(), dish);
    }

    public String getName() {
        return name;
    }

    public Map<String, Dish> getDishes() {
        return dishes;
    }

}
