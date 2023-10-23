import java.util.List;

// This class represents a user of the app
// It is used to filter dishes by restrictions
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

    public List<String> getRestrictions() {
        return restrictions;
    }
}
