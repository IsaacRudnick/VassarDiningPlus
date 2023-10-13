public class Menu{

    String station;
    List<Dish> menuDishes;

    public Menu(String station, List<Dish> dishes){
        this.station = station;
        menuDishes = dishes;
    }

    public List<Dish> getDishes(List<String> restrictions){
        List<Dish> goodDishes = new LinkedList();
        for(dish : menuDishes) {
            if(dish.validDish?(restrictions)){
                goodDishes.add(dish);
            }
        }
        return goodDishes;
    }

    private boolean validDish?(List<String> restrictions){
        for(rest : restrictions){
            if(!this.restrictions.contains(rest))
                return false;
        }
        return true;
    }
}