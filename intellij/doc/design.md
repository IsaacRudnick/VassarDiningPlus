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

@enduml
```

## Sequence diagrams
### Browse menu
```plantuml

@startuml
skin rose

hide footbox
actor "Human user" as human
participant " : UI" as ui
participant "curController : Controller" as controller
participant "days : DayLibrary" as days
participant "dishes[i] : Dish" as dish
participant "curUser : User" as user
participant "curRestrictions : Restrictions" as restriction
participant "curDay : Day" as day

human -> ui : Start application
ui -> controller **: curController : Controller = new Controller()
controller -> days **: days : DayLibrary = new DayLibrary()
ui -> controller : curRestrictions : Restrictions = getRestrictions()
controller -> restriction **: curRestrictions : Restrictions = new Restrictions()
ui -> human : Show available restrictions and request input
human -> ui : Enter desired restrictions
ui -> controller : createUser(restrictions : List<String>)
controller -> user **: curUser : User = new User(restrictions : List<String>)
ui -> human : Request date input
human -> ui : Enter date of desired menu
ui -> controller : todayMenus : Day = getDay(date : String)
controller -> days : todayMenus: Day = getDay(date : String, user : User)
days -> day **: curDay = new Day(date : String, user : User)
day -> day : createMenus(date : String, user : User)
day -> day : dishes : JSONObject = GetMenuJSON()
loop i in 0..dishes.size-1
    day -> dish **: dish : Dish = new Dish(id : String, name : String, description : String, restrictions : String<List>)
    day -> user : canEat(dish) : boolean
    alt user.canEat(dish)
        day -> day : addDish(menuName : String, dish : Dish)
        
    end
end
controller -> ui : display(dishes)
ui -->> human : Display date's menus
@enduml
```

### Scrape website menu
```plantuml
@startuml
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
@enduml
```

### Favorite item (not implementing now, only doing browse menu)
```plantuml
@startuml
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
@enduml
```