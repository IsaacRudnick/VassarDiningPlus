# Design Class Diagram

Classes
* User: Favorites (list of Dishes), Reviews (list of Reviews), Dietary restrictions (list of Strings)
* Review: rating (integer 0<=x<=5), Dish
* Dish: name (String), Dietary restrictions (list of Strings), Average rating (int), Reviews (list of Reviews)
* Day: date, list of Menus
* Menu: station name (String), list of Dishes

## Class diagram
```plantuml
@startuml

skin rose

' classes
class User{
    restrictions: List<String>
    --
    getRestrictions() : List<String>
}
class Review{
    Rating : int
}
class MenuLibrary{
    menuMap: Map<Date,Set<Menu>>
    --
    scrapeMenu(day : date) : void
    getMenus(date : Date) : Set<Menu>
}
class Menu{
    station : String
    --
    getDishes(restrictions : List<String>) : List<Dish>
}
class Dish{
    name : String
    description : String
    avgRating : int
    restrictions : List<String>
    --
    averageRating() : int
}
class DishLibrary{
    allDishes : Set<Dish>
    __
    dishExists?(dish : Dish) : boolean
}
class Controller{
    todayDate : date
    --
    getMenu(day : MenuDate) : List<Dish>
}
class UI{
    display(dishes : List<Dish>)
}

' associations
User --right-> "\t*\n \tuserReviews\n\t{List}" Review : \t\t\t
User -up-> "\n*\nfavorites\n{List}" Dish
Dish --> "\t*\n\tdishReviews\n\t{List}\n" Review
MenuLibrary -right-> "*\ndayMenus\n{ordered, List}" Menu : \t\t\t
Menu -down-> "*\nmenuDishes\n{List}" Dish : \t\t
Controller -> "1\ncurUser" User
Controller .up.> MenuLibrary
Controller .> Dish
UI .left.> Dish
DishLibrary --> Dish
```

## Sequence diagrams
### Browse menu
```plantuml
skin rose

hide footbox
actor "Human user" as human
participant " : UI" as ui
participant " : Controller" as controller
participant "curDate : MenuDate" as date
participant "menus[i] : Menu" as menu
participant " : User" as user

human -> ui : Enter date of desired menu
ui -> controller : getMenu(date)
controller -> date: getMenus()
date -->> controller : menus : List<Menu>
controller -> user : getRestrictions()
user -->> controller : restrictions : List<String>
loop i in 0..menus.size-1
    controller -> menu : getDishes(restrictions : List<String>)
    menu -->> controller : dishes : List<Dish>
end
controller -> ui : display(dishes)
ui -->> human : Display date's menus
```

### Scrape website menu
```plantuml
skin rose
hide footbox
participant " : Controller" as controller
participant "curDate : MenuDate" as date
participant " : Menu" as menu
participant " : Dish" as dish
participant " : Website" as web

controller -> date : scrapeMenu(day : Date)
date -> web : fetch()
web --> date : html : String
loop String != ""
date -> dish **: dish = create(name, description, avgRating, restrictions)
end

date -> menu **: menu = create()
```

### Favorite item (not implementing now, only doing browse menu)
```plantuml
skin rose

hide footbox
actor "Human user" as human
participant " : UI" as ui
participant " : Controller" as controller
participant " : User" as user
human -> ui : Select item
ui -> controller : info(dish)
controller -> dish : details()
dish -->> controller : Name, Average Rating, Dietary restrictions, Description
controller -> ui : displaySingle(dish)
human -> ui : Favorite item
ui -> controller : favorite(dish : Dish)
controller -> user : addFavorite(dish : Dish)
user -->> controller : success : bool
alt success
    controller -> ui : favoriteAdded()
    ui -->> human : Display confirmation
else !success
    controller -> ui : favoriteFailure()
    ui -->> human : Display error
end
```