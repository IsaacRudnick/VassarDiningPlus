# Test Report

```console
Welcome to the CBA Menu App!
These are the allowed restrictions:
11: Kosher
1: Vegetarian
3: Seafood Watch
4: Vegan
6: Farm to Fork
7: In Balance
18: Humane
8: Organic
9: Made without Gluten-Containing Ingredients
10: Halal

Enter your restrictions separated by commas (e.g. "1, 9") or press enter to skip:
INPUT -> 1, 3, *!AAA
Invalid restrictions. Please try again.
INPUT -> 3
restrictions: [Seafood Watch]

Enter a date in the format YYYY-MM-DD or type "quit" to exit:
2023-10-11
Fetching meals for 2023-10-11...
Menu: @The Farmer's Table
        Tuna
                -Restrictions: [Seafood Watch, Made without Gluten-Containing Ingredients]

Menu: @Home
        Sweet And Spicy Shrimp
                -Restrictions: [Seafood Watch, Made without Gluten-Containing Ingredients, Halal]

Menu: @The Deli
        Tuna Salad
                -Description: 1/2 cup
                -Restrictions: [Seafood Watch, Made without Gluten-Containing Ingredients]


Enter a date in the format YYYY-MM-DD or type "quit" to exit:
INPUT ->  quit
```

SECOND INTERACTION:

<sub>Note there are too many restrictions. This is because there is little accomodation in the menu for people who have many restrictions</sub>

```console
Welcome to the CBA Menu App!
These are the allowed restrictions:
11: Kosher
1: Vegetarian
3: Seafood Watch
4: Vegan
6: Farm to Fork
7: In Balance
18: Humane
8: Organic
9: Made without Gluten-Containing Ingredients
10: Halal

Enter your restrictions separated by commas (e.g. "1, 9") or press enter to skip:
INPUT -> 1, 4, 6
restrictions: [Vegetarian, Vegan, Farm to Fork]
Enter a date in the format YYYY-MM-DD or type "quit" to exit:
INPUT -> 2023-09-15
Fetching meals for 2023-09-15...
No meals found for 2023-09-15 with your restrictions.
Enter a date in the format YYYY-MM-DD or type "quit" to exit:
INPUT -> 2023-10-10
Fetching meals for 2023-10-10...
No meals found for 2023-10-10 with your restrictions.
Enter a date in the format YYYY-MM-DD or type "quit" to exit:
INPUT -> quit
```
