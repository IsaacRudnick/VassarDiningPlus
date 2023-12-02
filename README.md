# Phase 4 Submission

## Functionality

This iteration added:

* Favoriting of dishes 

* Dedicated profile tab for management of restrictions & favorites

* favorites-only filter option to main tab (to only view the favorites offered on a day)

* Added persistence for user restrictions, user favorites, and cached days

* Automatic fetching of current day on app launch

* New tests + full javadoc (except for some overridden android methods)

## How to Run

Android Studio abstracts running, but the controller is in `astudio/app/src/main/java/.../controller/ControllerActivity.java`

Launch may take a few seconds. \
On launch, you will see an input box for a date and a list of menus and dishes. \
You can click the heart icon next to a dish to toggle whether or not it is a favorite. \
You can click the favorites-only checkbox to only show your favorites for that day, \
or go to the profile management tab to manage all of your favorites. \
That tab will also allow you to set your dietary restrictions, which will filter what the main search tab shows you. 
All of your restrictions and favorites will be stored between uses of the app. Further, relaunching the app will be faster than the first time, since fetched days are cached!
