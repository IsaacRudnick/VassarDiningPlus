# Test Report

The main functionality of this prototype is to take a user's restrictions and
get menu items for a particular date that fit those restrictions. Therefore, our
tests should check that the program is returning menu items for the correct date,
which we can check against the Cafe Bon Appetit Website, and that it is returning
all items that fit the given restrictions without returning any items that do not
fit the restrictions. Additionally, we must test the basic functionality of the
prototype, making sure that it can take in input, validate that the input is 
correct, and print output in a readable manner.

## Test 1
The first test is testing that the menu being printed to the terminal is that of
the appropriate date. Thus, there are no restrictions given. A random date was
chosen to ensure that the date being printed is not just today. Then another 
random date was chosen to ensure that multiple dates can be accessed. The first
date was chosen to be in the future while the second was chosen to be in the past,
to ensure that both future and past dates are accessible. Finally, today was 
tested to ensure that it remains accurate. All of the outputs were compared to the
actual Cafe Bon Appetit website and found to be correct for their dates. Some of the 
output has been cut for length, indicated by [...].


## Test 2
This test checks that restrictions are being taken in and applied accurately. It 
also tests our error checking by entering invalid restrictions when asked. The 
prototype handled invalid restrictions appropriately, printing the error and
repeatedly requesting input. When compared to the Cafe Bon Appetit website, the
output was also found to be accurate, returning only the items that fit the given
restriction.
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

## Test 3
This test checked handling of multiple restrictions. The first interaction contained
too many restrictions. This is because there is little accommodation in the Cafe Bon
Appetit menu for people who have many restrictions. However, the output of our 
prototype was correct for an empty menu. The second interaction shows a combination
of restrictions that provides a non-empty menu, and it was confirmed with the Cafe
Bon Appetit website that the items being returned were correct.

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