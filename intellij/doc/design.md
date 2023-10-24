# Design Class Diagram

Classes with attributes
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
    - restrictions: List<String>
    --
    + getRestrictions() : List<String>
    + canEat(dish : Dish) : boolean
}
class Review{
    - Rating : int {range=[0,5]}
}
class DayLibrary{
    --
    + getDay(date : String, user : User) : Day
}
class Day{
    - date : String
    --
    scrapeMenu(day : date) : void
    + getMenus(date : Date) : Map<String, Menu>
    + toString() : String
    - addDish(menuName : String, dish : Dish) : void
    - GetMenuJSON() : JSONObject
    {static} - toTitleCase(input : String) : String
    - createMenus(date : String, user : User) : void
}
class Menu{
    - name : String
    --
    + getDishes() : Map<String, Dish>
    + addDish(dish : dish) : void
    + getName() : String
    + toString() : String
    
}
class Dish{
    - id : String
    - name : String
    - description : String
    - avgRating : float
    - restrictions : List<String>
    --
    + hasRestriction(String restriction) : boolean
    + getId() : String
    + getName() : String
    + getDescription : String
    + getRestrictions : List<String>
    + toString() : String
    + getRating() : float
    - averageRating() : float
}
class DishLibrary{
    - allDishes : Set<Dish>
    __
    + dishExists?(dish : Dish) : boolean
}
class Controller{
    - todayDate : date
    --
    {static} + areValidRestrictions(restrictionIDs : List<String>) : boolean
    {static} - restrictionsFromIDs(restrictionIDs : List<String>) : List<String>
    + createUser(restrictionIDs : List<String>) : void
    + getUserRestrictions() : List<String>
    + getDay(date : String) : Day
    {static} + getRestrictions() : Restrictions
    + getUser() : User
    getMenu(day : MenuDate) : List<Dish>
}
class Restrictions{
    {static} - Restrictions : Map<String,String>
    --
    + getRestrictionName(id : String) : String
    + isValid(id : String) : boolean
    + toString(): String
}
class UI{
    display(dishes : List<Dish>)
}

' associations
User -down-> "\t*\n \tuserReviews\n\t{List}" Review : \t\t\t
User -> "\n*\nfavorites\n{List}" Dish
Dish -down-> "*\ndishReviews\n{List}\n" Review
Day -> "*\nMenus\n{Map<String,Menu>}" Menu : \t\t\t
Menu -> "*\ndishes\n{Map<String,Dish>}" Dish : \t\t
Controller -down-> "1\nuser" User
Controller -left-> "1\ndays" DayLibrary
Controller .> Dish
UI .down.> Dish
DishLibrary -up-> Dish
Day -> "1\nuser" User
DayLibrary -down-> "*\ndays\n{Map<String,Day}" Day
Controller .up.> Restrictions
```

## Sequence diagrams
### Browse menu
```plantuml
skin rose

hide footbox
actor "Human user" as human
participant " : UI" as ui
participant " : Controller" as controller
participant " : MenuLibrary" as menulib
participant "menus[i] : Menu" as menu
participant " : User" as user

human -> ui : Enter date of desired menu
ui -> controller : getMenu(date)
controller -> menulib: getMenus()
menulib -->> controller : menus : Set<Menu>
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
participant " : MenuLibrary" as menulib
participant " : Menu" as menu
participant " : Dish" as dish
participant " : Website" as web

controller -> menulib : scrapeMenu(day : Date)
menulib -> web : http request
web --> menulib : html : String
loop String != ""
menulib -> dish **: new Dish( = create(name, description, avgRating, restrictions)
end
menulib -> menu **: menu = new Menu()
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