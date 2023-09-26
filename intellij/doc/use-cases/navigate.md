# Use case: Navigate App

## 1. Primary actor and goals
* __Reader/User__: wants to be able to browse CBA menu items and see reviews
* __Reviewer__: wants to be able to contribute their opinion (in the form of a rating) on menu items 

## 2. Other stakeholders and their goals

* __CBA__: Want to rate limit robot access to webpage if egregious


## 2. Preconditions

* App is open to main page

## 4. Postconditions

* User found desired item and reviewed / read reviews.
* User was able to update profile items as needed
* Updates were synced to server

## 4. Workflow

__Fully dressed__ overview

```plantuml
@startuml

skin rose
title Navigate

start
while (Open?) is (\nyes)
#technology:Fetch favorites available on today's menu;
#technology:Display navigation menu and today's favorites;
switch (Option?)
    case ( View food)
        while (View food) is ( )
            #implementation:Execute __Browse menu__;
            #implementation:Select item;
            if (Item action?) then (Favorite)
                #implementation:Execute __Favorite item__;
            else ( Review)
                #LightCyan:Execute __Review item__;
            endif
        endwhile (Main Menu )
    case ( Manage profile)
        switch (Manage what?)
            case ( Reviews)
                #LightCyan:Execute __Manage reviews__;
            case (\n Favorites)
            
                #implementation:Execute __Manage favorites__;
            case ( Personal information)
                #LightCyan:Execute __Manage personal info__;
        endswitch
endswitch
endwhile (Quit )
stop
@enduml
```