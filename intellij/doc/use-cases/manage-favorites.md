# Use case: Manage Favorites

## 1. Primary actor and goals
__Reader/User__: wants to be able to manage their favorites en masse without having to find them across the list of all menu items ever offered

## 2. Other stakeholders and their goals

* __CBA__: Want to rate limit robot access to webpage if egregious


## 2. Preconditions

* User is on manage profile page

## 4. Postconditions

* User favorites are updated as desired.
* Changes are synced (e.g. to server if profile syncing is designed)
* User receives confirmation of updates as they are made.

## 4. Workflow

__Fully dressed__
```plantuml
@startuml

skin rose
title Manage Favorites
legend right
    |Color| Type |
    |<#technology>| System |
    |<#implementation>| Reader |
endlegend
start
while (Open?) is (\nYes)
#technology:Display all favorites for user;
#implementation:Select item;
If (Delete favorite) then (yes)
    #implementation: Select delete favorite;
    #technology:Remove item from favorites list;
else (Add favorite)
     #implementation: Select add favorite;
     #technology:Add item to favorites list;
endif
#technology:Display confirmation of process;
endwhile (No)
#technology:Exit to __Manage Profile__;
stop
@enduml
```