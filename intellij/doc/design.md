# Design Class Diagram

Classes with attributes
* User: Favorites (list of Dishes), Reviews (list of Reviews), Dietary restrictions (list of Strings)
* Review: rating (integer 0<=x<=5), Dish
* Dish: name (String), Dietary restrictions (list of Strings), Average rating (int), Reviews (list of Reviews)
* Day: date, list of Menus
* Menu: station name (String), list of Dishes

## Class diagram
Note that the DishLibrary class is not currently implemented, but we want to include it in the 
structure of our classes because we know we want to implement it in the next iteration. Reviews 
have also not been implemented but are included in the diagram for future iterations.
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
class DayLibrary{
    --
    + getDay(date : String, user : User) : Day
}
class Day{
    - date : String
    - user : User
    --
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
    + addDish(dish : Dish) : void
    + getName() : String
    + toString() : String
    
}
class Dish{
    - id : String
    - name : String
    - description : String
    - restrictions : List<String>
    --
    + hasRestriction(restriction : String) : boolean
    + getId() : String
    + getName() : String
    + getDescription : String
    + getRestrictions : List<String>
    + toString() : String
}
class DishLibrary{
    __
    + dishExists?(dish : Dish) : boolean
}
class Review{
    - Rating : int {range=[0,5]}
}
class Controller{
    --
    {static} + areValidRestrictions(restrictionIDs : List<String>) : boolean
    {static} - restrictionsFromIDs(restrictionIDs : List<String>) : List<String>
    + createUser(restrictionIDs : List<String>) : void
    + getUserRestrictions() : List<String>
    + getDayAsString(date : String) : String
    {static} + getPossibleRestrictions() : Restrictions
}
class Restrictions{
    {static} - restrictions : Map<String,String>
    --
    + getRestrictionName(id : String) : String
    {static} + isValid(id : String) : boolean
    + toString(): String
}
class View{
    {static} + main(args : String[]) : void
}

' associations
User -right-> "*\nuserReviews\n{List}" Review : \t\t
Dish -up-> "*\ndishReviews\n{List}" Review
User -right-> "*\nfavorites\n{List}" Dish : \t\t
Day -down-> "*\nMenus\n{Map<String,Menu>}" Menu
Menu -right-> "*\ndishes\n{Map<String,Dish>}" Dish : \t\t
Controller -down-> "1\nuser" User
Controller -left-> "1\ndays" DayLibrary : \t
View .down.> Controller
Day -> "1\nuser" User : \t
DayLibrary -down-> "*\ndays\n{Map<String,Day}" Day
DishLibrary -up-> "*\nallDishes\n{Set<Dish>}" Dish
Controller .> Restrictions
Controller .down.> Day
Dish .up.> Restrictions

@enduml
```

## Sequence diagrams
### Browse menu
```plantuml

@startuml
skin rose
hide footbox
mainframe sd BrowseMenu

actor "Human user" as human
participant " : UI" as ui
participant "curController : Controller" as controller
participant "curUser : User" as user
participant "curRestrictions : Restrictions" as restriction
participant "days : DayLibrary" as days

ui -->> human : Display date and restriction input
human -> ui : Enter desired date and restrictions
ui -->> controller : restrictionIDs : List<String>
controller -> user **: new User(restrictionIDs : List<String>)
ui -> controller : dayRequested(date : String)
controller -> controller : validDate?(date : String)
alt validDate
    controller -> days : getDay(date : String, user : User)
    alt !dateExists
        ref over days
        CreateDay
        end ref
    end
    days -->> controller : curDay : Day
    controller -> ui : displayDay(curDay : Day)
    ui -->> human : Display date's menus
else !validDate
    controller -> ui : invalidDate()
end
@enduml
```

### Scrape website menu
```plantuml
@startuml
skin rose
hide footbox
mainframe sd CreateDay

participant "days : DayLibrary" as days
participant "curDay : Day" as day
participant " : Menu" as menu
participant "dishes[i] : Dish" as dish
participant "curUser : User" as user

days -> day **: new Day(date : String, user : User)
    day -> day : createMenus(date : String, user : User)
    day -> day : dishes : JSONObject = GetMenuJSON()
    loop i in 0..dishes.size-1
        day -> dish **: new Dish(id : String, name : String, description : String, restrictions : String<List>)
        day -> user : canEat(dish)
        user -->> day : canEat : boolean
        alt canEat
            day -> day : addDish(menuName : String, dish : Dish)
            alt !menuExists
                day -> menu **: new Menu(menuName : String)
            end
            day -> menu : addDish(dish Dish)
        end
    end
@enduml
```

### Navigate app
```plantuml
@startuml
skin rose
hide footbox

actor "Human user" as human
participant "curController : Controller" as controller
participant " : UI" as ui
participant "curUser : User" as user

human -> controller : open app
controller -> days **: new DayLibrary()
controller -> user **: new User(restrictions : List<String>)
controller -> ui **: new UI
controller -> controller : getPossibleRestrictions()
controller -> restriction **: new Restrictions()
controller -> ui : displayBrowseMenu
ui --> human : display browse menu screen
alt ManageProfileClicked
    ui -> controller : onClick(ManageProfile)
    ref over human,controller,ui,user
    ManageProfile
    end ref
else !ManageProfileClicked
    ref over human,controller,ui,user
    BrowseMenu
    end ref
end


```