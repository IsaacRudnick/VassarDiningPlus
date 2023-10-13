import java.util.*;

public class Menu {
    private final String name;
    private final Map<String, Dish> dishes;

    public Menu(String name) {
        this.name = name;
        this.dishes = new HashMap<String, Dish>();
    }

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

    public String getName() {
        return name;
    }

    public void addDish(Dish dish) {
        dishes.put(dish.getId(), dish);
    }

    public Map<String, Dish> getDishes() {
        return dishes;
    }
}
