# Design Class Diagram

Classes with attributes
* User: Favorites (list of Dishes), Reviews (list of Reviews), Dietary restrictions (list of Strings)
* Review: rating (integer 0<=x<=5), Dish
* Dish: name (String), Dietary restrictions (list of Strings), Average rating (int), Reviews (list of Reviews)
* Day: date, list of Menus
* Menu: station name (String), list of Dishes


## Package diagram
```plantuml
@startuml
skin rose

package "User interface" as ui{
folder TextUI
folder AndroidUI
}
TextUI -[hidden]> AndroidUI
package "Domain" as domain{
folder Controller
folder Model
folder Persistence
}
Controller -[hidden]> Model
package "Persistence" as persistence{
folder LocalStorage
ui .down.> domain
domain .down.> persistence
@enduml
```

## AndroidUI class diagram
```plantuml
skin rose
'classes
interface IMainView{
    --
    + getRootView() : View
    + displayFragment(fragment : Fragment, addToStack : boolean, name : String) : void
}
interface IMainView.Listener{
    --
    ~ onBrowseClick() : void
    ~ onProfileClick() : void
}
interface IViewDay{
    --
    ~ updateDayDisplay(day : Day, listener : DishViewHolder.Listener) : void
    ~ onInvalidDate(rootView : View) : void
}
interface IViewDay.Listener{
    --
    ~ onDayRequested(date : String, favoritesOnly : boolean, browseDayView : IViewDay) : void
}
interface IManageProfile{
    --
    ~ updateFavoritesDisplay( ): void
    ~ setUserRestrictions(List<Restriction> restrictions) : void
}
interface IManageProfile.Listener{
    --
    ~ onUpdateRestrictions(restrictions : List<Restriction>) : void
    ~ toggleDishFavorited(dish : Dish) : void
    ~ isDishFavorited(dish : Dish) : boolean
    ~ onFavoritesRequested(manageProfile : IManageProfile) : void
    ~ checkSavedrestrictions(manageProfile : IManageProfile) : void
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
    + {static} validDate(date : String) : boolean
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

'associations
ControllerActivity -up-> MainView
ControllerActivity .down.> ViewDayFragment
ControllerActivity .right.> ManageProfileFragment

MainView -> IMainView.Listener
ViewDayFragment -> IViewDay.Listener
ManageProfileFragment -> IManageProfile.Listener
ManageProfileFragment -> "1\nfavoritesAsMenu\n{final}" Menu

IMainView <|.. MainView
IViewDay <|.. ViewDayFragment
IManageProfile <|.. ManageProfileFragment
IMainView.Listener <|.. ControllerActivity
IViewDay.Listener <|.. ControllerActivity
IManageProfile.Listener <|.. ControllerActivity

```

## Model class diagram
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

' associations
ControllerActivity -down-> "1\nsaveduser" User
ControllerActivity -up-> "1\ndays" DayLibrary : \t
ControllerActivity .right.> Restriction
ControllerActivity .left.> Day
ControllerActivity .down.> Dish

DayLibrary -down-> "1\nuser\n{final}" User : \t
DayLibrary -left-> "*\ndays\n{Map<String,Day}" Day

User -left-> "*\nfavorites\n{Set,final}" Dish\n : \t\t
User -down-> "*\n restrictions\n{List,final}" Restriction

Day -right-> "*\nmenus\n{Map<String,Menu>}" Menu

Menu -up-> "*\ndishes\n{Map<String,Dish>}" Dish : \t\t


DishLibrary -down-> "*\nallDishes\n{Set<Dish>}" Dish
Dish -down-> "*\nrestrictions\n{List,final}" Restriction

@enduml
```

### Persistence class diagram
```plantuml
@startuml

skin rose

' classes
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
    + {static} validDate(date : String) : boolean
}
interface IPersistenceFacade{
   --
    + saveUser(user : User) : void
    + loadUser() : User
    + saveDayLibrary(DayLibrary days) : void
    + loadDayLibrary() : DayLibrary
}
class LocalStorageFacade{
    - directory : File {final}
    - {static} USER_FILENAME : String {final}
    - {static} DAYLIBRARY_FILENAME : String {final}
}

'associations
ControllerActivity -up-> "1\npersistenceFacade" IPersistenceFacade
ControllerActivity .up.> LocalStorageFacade
IPersistenceFacade <|.. LocalStorageFacade

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
participant "ViewDayFragment : IViewDay" as ui
participant "curController : ControllerActivity" as controller
participant "curUser : User" as user
participant "days : DayLibrary" as days
participant " : Day" as day
participant "favorites : Menu" as faves
participant "LocalStorageFacade : IPersistenceFacade" as persistence

controller -> ui **: new ViewDayFragment(listener : Listener)
controller -> persistence **: new LocalStorageFacade(dir : File directory)
ui -->> human : Display date input and favorites only checkbox
human -> ui : Enter desired date
ui -> ui : validDate?(date : String)
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
controller -> persistence : saveDayLibrary(days : DayLibrary)
human -> ui : Select favorites only checkbox
ui -> controller : onDayRequested(today : Date, fav : boolean, view : IViewDay)
ref over human,ui,controller,user,persistence
    DayFavorites
end ref
@enduml
```

### Get favorites for a day
```plantuml
@startuml
skin rose
hide footbox
mainframe sd DayFavorites

actor "Human user" as human
participant "curController : ControllerActivity" as controller
participant " : Day" as day
participant " : Menu" as menu
participant "favorites : Menu" as faves
participant "curUser : User" as user


controller -> day : onlyFavorites(curUser : User)
loop for all menus
    day -> menu : getAllDishes()
    menu -->> day : allDishes : List<Dishes>
    day -> faves **: newMenu(nameFavorites : String)
    loop i in 0..allDishes.size-1
        day -> user : isFavorite(dish : Dish)
        alt isFavorite
            day -> faves : addDish(dish : Dish)
        end alt
    end
    alt menu.length > 0
        day -> day : addMenu(faves : Menu)
    end alt
end
day -->> controller : Day(today : Date, favesMenus : Map<String,Menu>

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
participant " : IMainView" as ui

participant "curController : ControllerActivity" as controller
participant "curUser : User" as user
participant "days : DayLibrary" as days
participant "LocalStorageFacade : IPersistenceFacade" as persistence

human -> controller : open app
controller -> days **: new DayLibrary()
controller -> user **: new User(restrictions : List<String>)
controller -> ui **: new MainView(listener : Listener)
controller -> persistence **: new LocalStorageFacade(dir : File directory)
controller -> persistence : loadUser()
controller -> persistence : loadDayLibrary()
controller -> ui : displayFragment(viewDayFrag : ViewDayFragment)
ui -->> human : Browse menu screen
alt ManageProfileClicked
    ui -> controller : onClick(ManageProfile)
    controller -> ui : displayFragment(manageProfileFrag : ViewDayFragment)
    ref over human,controller,ui,user,persistence
    ManageProfile
    end ref
else BrowseMenuClicked
    ui -> controller : onClick(BrowseMenu)
    controller -> ui : displayFragment(viewDayFrag : ViewDayFragment)
    ref over human,controller,ui,user,persistence
    BrowseMenu
    end ref
end
```

### Favorite item
```plantuml
@startuml
skin rose
hide footbox
mainframe sd favoriteItem
actor "Human user" as human
participant " : IViewDay" as ui
participant " : ControllerActivity" as controller
participant " : User" as user
participant "favorites : Menu" as favorites
participant "LocalStorageFacade : IPersistenceFacade" as persistence

human -> ui : Click favorite button
ui -> ui : reverseFavorite(dish : Dish)
ui -> controller : favorite(dish : Dish)
controller -> user : isFavorite(dish : Dish)
alt isFavorite
    controller -> user : removeFavorite(dish : Dish)
    user -> favorites : remove(dish : Dish)
    controller -> ui : favoriteRemoved()
else !isFavorite
    controller -> user : addFavorite(dish : Dish)
    user -> favorites : add(dish : Dish)
    controller -> ui : favoriteAdded()
end
controller -> persistence : saveUser(curUser : User)
ui -->> human : Display confirmation

@enduml
```

### Manage profile
```plantuml
@startuml
skin rose
hide footbox
actor "Human user" as human
participant "ManageProfileFragment : IManageProfile" as ui
participant "curController : ControllerActivity" as controller
participant "curUser : User" as user
participant "LocalStorageFacade : IPersistenceFacade" as persistence

controller -> ui **: new ManageProfileFragment(listener : Listener)
controller -> user : getRestrictions()
user -->> controller : curRestrictions : List<Restriction>
controller -> ui : displayRestrictions(curRestrictions : List<Restriction>)
ui -->> human : Show currently selected restrictions
controller -> user : getFavorites()
user -->> controller : favorites : Menu
controller -> ui : displayFavorites(favorites : Menu)
ui -->> human : Show current favorites
human -> ui : Select restriction(s)
ui -> controller : updateUserRestrictions(restrictions : List<Restriction>)
controller -> user : setRestrictions(restrictions : List<Restriction>)
controller -> ui : showUpdatedRestrictions()
ui -->> human : Check/uncheck restriction boxes
controller -> persistence : saveUser(curUser : User)
human -> ui : Click favorite button
ref over human,ui,user,controller,persistence
    favoriteItem
end ref
```
