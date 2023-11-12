# Use case: Manage Reviews

## 1. Primary actor and goals
__Reviewer__: wants to be able to update their reviews en masse

## 2. Other stakeholders and their goals

* Other readers: want to be able to view accurate reviews

## 2. Preconditions

* User is on manage profile page

## 4. Postconditions

* User reviews are updated as desired.
* Changes are synced (e.g. to server if profile syncing is designed)
* User receives confirmation of updates as they are made.

## 4. Workflow

__Fully dressed__
```plantuml
@startuml

skin rose
title Manage Reviews
legend right
    |Color| Type |
    |<#technology>| System |
    |<#LightCyan>| Reviewer |
    
endlegend
start
while (Exit?) is (\nNo)
#technology:Display all Reviews for user;
#LightCyan:Select action;
If (Delete Review?) then (\nYes)
    #LightCyan:Select review to delete;
    #technology:Erase selected review;
elseif (Add Review?) then (\nYes)
    #LightCyan:Enter dish to rate;
    #LightCyan: Enter rating;
    #technology:Add review of dish with given rating;
elseif (Change Review?) then (\nYes)
     #LightCyan:Select review to change;
     #LightCyan:Enter new rating;
     #technology:Update review with new rating;
endif
endwhile (Yes)
#technology:Exit to __Manage Profile__;
stop
@enduml
```