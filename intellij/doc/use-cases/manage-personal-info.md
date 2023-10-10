# Use case: Manage Personal Info

## 1. Primary actor and goals
__Reader/Reviewer__: wants to be able to update their reviews/favorites

## 2. Other stakeholders and their goals

N/A

## 2. Preconditions

## 4. Postconditions

* User is redirected to desired sub-menu

## 4. Workflow

__Brief__ overview


```plantuml
@startuml
legend right
    |Color| Type |
    |<#technology>| System |
    |<#implementation>| User |
endlegend
skin rose
title Manage Personal Info

start
while (Open?) is (\nYes)
#technology:Display checkbox of dietary restrictions;
#implementation: Update dietary restrictions;
#technology: Update listing of user restrictions to filter on main menu;
endwhile (No)
#implementation:Exit to Main Page;
stop
@enduml
```