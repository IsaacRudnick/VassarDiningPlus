import java.util.List;

public class User {
    // These are things that items must meet to be shown to the user
    private List<String> restrictions;

    public User(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public boolean canEat(Dish dish) {
        for (String restriction : restrictions) {
            if (!dish.hasRestriction(restriction)) {
                return false;
            }
        }
        return true;
    }
}
