# Design Class Diagram

Classes with attributes
* User: Favorites (list of Dishes), Reviews (list of Reviews), Dietary restrictions (list of Strings)
* Review: rating (integer 0<=x<=5), Dish
* Dish: name (String), Dietary restrictions (list of Strings), Average rating (int), Reviews (list of Reviews)
* Day: date, list of Menus
* Menu: station name (String), list of Dishes

## Class diagram
DishLibrary class has not been implemented, it is planned to be implemented in the next
iteration when favorites are implemented. Reviews likely will not be implemented in the 
future but are included in the diagram for reference.
```plantuml
@startuml

skin rose

' classes
class User{
    --
    + getRestrictions() : List<Restriction>
    + canEat(dish : Dish) : boolean
    + addFavorite(dish : Dish) : void
    + removeFavorite(dish : Dish) : void
    + isFavorite(dish : Dish) : boolean
}
class DayLibrary{
    - {static} MAX_DAYS : int {final}
    --
    + setUserRestrictions(newRestrictions : List<Restriction>) : boolean
    + getDay(date : String) : Day
}
class Day{
    - date : String {final}
    --
    + getMenus() : Map<String, Menu>
    + toString() : String
    - addDish(menuName : String, dish : Dish) : void
    - GetMenuJSON() : JSONObject
    {static} - toTitleCase(input : String) : String
    - createMenus(user : User) : void
}
class Menu{
    - name : String {final}
    --
    + getDishes() : Map<String, Dish>
    + addDish(dish : Dish) : void
    + getName() : String
    + toString() : String
    + iterator( ): Iterator<Dish>
}
class MenuIterator<Dish>{
    'TODO add this
}
class Dish{
    - name : String
    - description : String
    --
    + hasRestriction(restriction : Restriction) : boolean
    + getName() : String
    + toString() : String
}
class DishLibrary{
    __
    + dishExists?(dish : Dish) : boolean
}
class ControllerActivity{
    - currScreen : String
    --
    # onCreate(savedInstanceState : Bundle) : void
    + onBrowseClick() : void
    + onProfileClick() : void
    + onDayRequested(date : String, browseDayView : IViewDay) : void
    + toggleDishFavorited(dish : Dish) : void
    + isDishFavorited(dish : Dish) : boolean
    + onUpdateRestrictions(restrictions : List<Restriction>) : void
    + checkSavedRestrictions(manageProfile : IManageProfile) : void
    + onFavoritesRequested(manageProfile : IManageProfile) : void
    'TODO move this to ViewDay?
    + {static} validDate(date : String) : boolean
}
enum Restriction{
    VEGETARIAN
    VEGAN
    HALAL
    IN_BALANCE
    KOSHER
    LOW_GLUTEN
}
interface IMainView{
    'TODO need to add Listener interface inside?
    --
    + getRootView() : View
    + displayFragment(fragment : Fragment, addToStack : boolean, name : String) : void
}
interface IViewDay{
    --
    ~ updateDayDisplay(day : Day, listener : DishViewHolder.Listener) : void
    ~ onInvalidDate(rootView : View) : void
    'TODO need to add Listener interface inside?
}
interface IManageProfile{
    --
    ~ updateFavoritesDisplay( ): void
    ~ setUserRestrictions(List<Restriction> restrictions) : void
    'TODO need to add Listener interface inside?
}
class MainView{
    - fmanager : FragmentManager {final}
    - binding : ActivityMainBinding {final}
    - listener : Listener {final}
    --
    + getRootView() : View
    + displayFragment(fragment : Fragment, addToStack : boolean, name : String) : void
}
class ViewDayFragment{
    - binding : FragmentViewDayBinding
    - listener : Listener {final}
    --
    + onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
    + onViewCreated(view : View, savedInstanceState : Bundle) : void
    + updateDateDisplay(day : Day, listener : DishViewHolder.Listener) : void
    + onInvalidDate(rootView : View) : void
}
class ManageProfileFragment{
    - binding : FragmentManageProfileBinding
    - listener : IManageProfile.Listener {final}
    --
    + onCreateView(inflater : LayoutInflater, container : ViewGroup, savedInstanceState : Bundle) : View
    + onViewCreated(view : View, savedInstanceState : Bundle) : void
    + updateFavoritesDisplay() : void
    + setUserRestrictions(restrictions : List<Restriction>) : void
    + getCheckedRestrictions() : List<Restriction>
}
interface IPersistenceFacade{
}
class LocalStorageFacade{
    - directory : File {final}
    - {static} USER_FILENAME : String {final}
    - {static} DAYLIBRARY_FILENAME : String {final}
    --
    + saveUser(user : User) : void
    + loadUser() : User
    + saveDayLibrary(DayLibrary days) : void
    + loadDayLibrary() : DayLibrary
}
'TODO need to add holders and adapters?

' associations
ControllerActivity -down-> "1\nsaveduser" User
ControllerActivity -left-> "1\ndays" DayLibrary : \t
ControllerActivity -up-> MainView
ControllerActivity -up-> "1\npersistenceFacade" IPersistenceFacade
ControllerActivity .up.> LocalStorageFacade
ControllerActivity .up.> ViewDayFragment
ControllerActivity .right.> ManageProfileFragment
ControllerActivity .down.> Restriction
ControllerActivity .down.> Day
ControllerActivity .right.> Dish

DayLibrary -right-> "1\nuser\n{final}" User : \t
DayLibrary -right-> "*\ndays\n{Map<String,Day}" Day

User -right-> "*\nfavorites\n{Set,final}" Dish\n : \t\t
User -down-> "*\n restrictions\n{List,final}" Restriction

Day -down-> "*\nmenus\n{Map<String,Menu>}" Menu

Menu -left-> "*\ndishes\n{Map<String,Dish>}" Dish : \t\t


DishLibrary -down-> "*\nallDishes\n{Set<Dish>}" Dish
Dish -down-> "*\nrestrictions\n{List,final}" Restriction

IMainView <|.. MainView
IViewDay <|.. ViewDayFragment
IManageProfile <|.. ManageProfileFragment
IPersistenceFacade <|.. LocalStorageFacade
ManageProfileFragment -left-> "1\nfavoritesAsMenu\n{final}" Menu


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
participant " : IViewDay" as ui
participant "curController : ControllerActivity" as controller
participant "curUser : User" as user
participant "curRestrictions : Restrictions" as restriction
participant "days : DayLibrary" as days

ui -->> human : Display date and restriction input
human -> ui : Enter desired date and restrictions
ui -->> controller : restrictionIDs : List<String>
controller -> user **: new User(restrictionIDs : List<String>)
ui -> ui : validDate?(date : String)
alt validDate
    ui -> controller : dayRequested(date : String)
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
participant "curController : ControllerActivity" as controller
participant " : IMainView" as ui
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