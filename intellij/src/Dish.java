public class Dish{

    String name;
    String description;
    int avgRating;
    List<String> restrictions;
    List<Review> dishReviews;

    public Dish(String name, String description, List<String> restrictions){
        this.name = name;
        this.description = description;
        this.avgRating = Null;
        this.restrictions = restrictions;
        this.dishReviews = new LinkedList();
    }

    public int averageRating(){
        return 5;
    }
}