# Phase 2 Submission

## Functionality

This iteration added:

 - Android Studio migration
 - Dynamic Restrictions Changing (Change Restrictions w/o Restarting App)

## How to Run

- Android Studio abstracts running, but the controller is in `intellij/astudio/app/src/main/java/.../controller/ControllerActivity.java`
- Opening the app should show:
  - Checkboxes for restrictions
  - Search box / button for entering date
- Entering malformed date will show error (e.g. "Tomorrow" is not valid)
- Depending on how 'happy' the AVD is, the network request for the menu may take some time. Please wait after searching (~30s)
- The result will be cached but changing the restrictions in between searches will clear the cache
- 