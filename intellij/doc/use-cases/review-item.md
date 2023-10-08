# Use case: Review item

## 1. Primary actor and goals
__Reviewer__: Wants to add a review to the chosen item. Wants the review to accurately
reflect their perception of/feelings about the dish. Wants their review to be visible
to others.

## 2. Other stakeholders and their goals
* __CBA__: Wants reviews accurately recorded to see students' opinions
on their meals offered.

## 3. Preconditions
* Reviewer has chosen an item.

## 4. Postconditions
* Review is added to the system's database and is connected to the Reviewer and the
reviewed item.
* Reviewer has seen confirmation that review was successfully recorded.

## 5. Workflow
__Fully dressed__: All scenarios and variations in detail.

Color code: blue for Reviewer, green for System.

```plantuml
@startuml

skin rose
title Favorite item

start
#technology:Display item;
if (Confirm review this item?) then (Yes)
repeat
#technology:Display rating input system;
#LightCyan:Input rating;
repeat while (Valid rating?) is (No)
-> Yes;
#technology:Add rating to database;
#technology:Display confirmation of rating logged;
#LightCyan:Exit confirmation;
else (No)
#technology:Display confirmation that no review was saved;
endif
stop
@enduml
```