# Use case: Manage Personal Info

## 1. Primary actor and goals
__Reader/Reviewer__: wants to be able to update their reviews/favorites

## 2. Other stakeholders and their goals

N/A

## 2. Preconditions

## 4. Postconditions

* User is redirected to desired sub-menu

## 4. Workflow

__Fully dressed__

```plantuml
@startuml
legend right
    |Color| Type |
    |<#technology>| System |
    |<#implementation>| Reader |
endlegend
skin rose
title Manage Personal Info

start
while (Exit?) is (\nNo)
#technology:Display checkbox of dietary restrictions;
#implementation: Select desired dietary restrictions;
#technology: Update listing of user restrictions to filter on main menu;
#technology:Display confirmation of updated restrictions;
endwhile (Yes)
#technology:Exit to __Navigate__;
stop
@enduml
```