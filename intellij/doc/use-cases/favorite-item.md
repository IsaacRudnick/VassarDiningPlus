# Use case: Favorite item

## 1. Primary actor and goals
__Reader__: Wants to add chosen item to their list of favorites to later be notified
if the item becomes available. Wants confirmation that the correct item was added to
the favorites list successfully.

## 2. Other stakeholders and their goals
* None

## 3. Precondition
* Reader has selected an item.

## 4. Postconditions
* System database of favorites includes chosen item.
* Reader has seen confirmation that item was successfully added.

## 5. Workflow
__Fully dressed__: All scenarios and variations in detail.

Color code: pink for Reader, green for System.

```plantuml
@startuml

skin rose
title Favorite item
legend right
    |Color| Type |
    |<#technology>| System |
    |<#implementation>| Reader |
endlegend
start
#technology:Display item;
if (Confirm add item to favorites?) then (Yes)
#implementation:Choose to add item to favorites;
#technology:Add item to favorites;
if (Add similar items?) then (Yes)
#technology:Add similar items to favorites;
else (No)
endif
#technology:Display confirmation of all items added;
else (No)
#implementation:Choose not to add item to favorites;
#technology:Display confirmation that item was not added;
endif
#implementation:Exit confirmation;
stop
@enduml
```