# Phase 2 Submission

## Functionality

This iteration allows for:

- Scraping from the CBA Menu for any day
- Filtering based on dietary restrictions

## How to Run

- The main method is in `src/TextView.java`
- Ensure the `dependencies/json-simple-1.1.1.jar` file is added as a dependency, or you find another
  way to add [JSON simple](https://code.google.com/archive/p/json-simple/) to your dependencies
- The main method has interaction instructions and an example interaction is included in `doc/test-report.md`
- Note that certain restrictions, used in conjunction, lead to very few (or zero) menu items.
  It is therefore recommended to only use the below restrictions, and to avoid using more than ~2 total.
  - Vegetarian
  - Vegan
  - Made without Gluten-Containing Ingredients
  - Halal or
  - Kosher
