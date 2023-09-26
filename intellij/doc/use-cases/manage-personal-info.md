# Use case: Manage Personal Info

## 1. Primary actor and goals
__Reader/User__: wants to be able to update their reviews/favorites

## 2. Other stakeholders and their goals

N/A

## 2. Preconditions

## 4. Postconditions

* User is redirected to desired sub-menu

## 4. Workflow

__Brief__ overview


```plantuml
@startuml

skin rose
title Manage Personal Info

start
while (Open?) is (\nYes)
#technology:Display ratings and favorites buttons;
If (Choose Option?) then (Manage Reviews)
    #implementation: Display Reviews Page;
else (Manage Favorites)
     #implementation: Display Manage Favorites Page;
endif
endwhile (No)
#implementation:Exit item;
stop
@enduml
```