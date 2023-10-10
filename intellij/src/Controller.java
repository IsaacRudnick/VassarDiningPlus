public class Controller {

    public List<Menu> getMenu(MenuDate day){
        List<Menu> menus = day.getMenus();
        List<String> restrictions = user.getRestrictions();
        List<Dish> dishes = new LinkedList();
        for(int i = 0; i < menus.size(); i++){
            List<Dish> menuDishes = menus[i].getDishes(restrictions);
            dishes.add(menuDishes);
        }
        return menuDishes;
    }

}