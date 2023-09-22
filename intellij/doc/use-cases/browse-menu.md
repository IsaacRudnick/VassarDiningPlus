# Use case: Browse menu

```plantuml
@startuml

skin rose
title Browse menu

start
while (Enter date or exit) is (\nEnter date)
#technology:Display date input;
#implementation:Select date of menu to view;
#technology:Fetch menu information of selected date;
#technology:Display menu;
while (View menu) is (Select item)
if (Item action?) then (Favorite)
    #implementation:Execute __Favorite item__;
else ( Review)
    #LightCyan:Execute __Review item__;
endif
#technology:Exit item;
endwhile (Exit menu)
endwhile (Return to main menu)
stop
@enduml
```