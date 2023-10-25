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
User -right-> "\n*\nfavorites\n{List}" Dish
Day -right-> "*\nMenus\n{Map<String,Menu>}" Menu : \t\t\t
Menu -up-> "*\ndishes\n{Map<String,Dish>}" Dish : \t\t
Controller -down-> "1\nuser" User
Controller -left-> "1\ndays" DayLibrary : \t
View .down.> Controller
Day -> "1\nuser" User
DayLibrary -down-> "*\ndays\n{Map<String,Day}" Day
Controller .> Restrictions
Controller .> Day

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
participant "curUser : User" as user
participant "curRestrictions : Restrictions" as restriction
participant "days : DayLibrary" as days

human -> ui : Start application
ui -> controller **: new Controller()
controller -> days **: new DayLibrary()
ui -> controller : getPossibleRestrictions()
controller -> restriction **: new Restrictions()
controller -->> ui : restrictions : String
ui -->> human : Display available restrictions
loop do-while !validRestrictions
    ui -> human : Request input
    human -> ui : Enter desired restrictions
    ui -->> ui : restrictionIDs : List<String>
    ui -> controller : areValidRestrictions(restrictionIDs : List<String>)
    controller -> restriction : isValid(restriction : String)
    restriction -->> controller : validRestriction : boolean
    alt !validRestriction
        ui -> human : Display invalid restrictions warning
    end
end
ui -> controller : createUser(restrictions : List<String>)
controller -> user **: new User(restrictions : List<String>)
loop input != "quit"
    ui -> human : Request date input
    human -> ui : Enter date of desired menu
    ui -> controller : getDayAsString(date : String)
    controller -> days : getDay(date : String, user : User)
    alt !dateExists
        ref over days
        CreateDay
        end ref
    end
    days -->> controller : curDay : Day
    controller -->> ui : dayString : String
    ui -->> human : Display date's menus
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