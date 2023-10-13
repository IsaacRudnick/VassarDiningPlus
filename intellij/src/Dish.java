import java.util.List;

public class Dish {
    private final String id;
    private final String name;
    private final String description;
    private final List<String> restrictions;

    public Dish(String id, String name, String description, List<String> restrictions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.restrictions = restrictions;
    }

    public float getRating() {
        return -1; // Not yet implemented
    }

    public String toString() {
        String output = "";
        output += name + "\n";
        if (description.length() > 0) {
            output += "\t-Description: " + description + "\n";
        }
        output += "\t-Restrictions: " + restrictions + "\n";
        return output;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }
}