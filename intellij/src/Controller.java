import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Controller {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the CBA Menu App!");

        Scanner scanner = new Scanner(System.in);
        // Get restrictions
        System.out.println("These are the allowed restrictions:");
        System.out.println(new Restrictions());
        System.out.println("Enter your restrictions separated by commas (e.g. \"1, 9\") or press enter to skip:");

        // Keep asking for restrictions until the user enters valid restrictions
        Boolean validRestrictions;
        List<String> restrictionIDs = new ArrayList<String>();
        do {
            // Assume the user will enter valid restrictions
            validRestrictions = true;
            String str = scanner.nextLine();
            // If the user presses enter without entering any restrictions,
            // break out of the loop
            if (str.equals("")) {
                break;
            }
            // Split the string into a list of numbers (still strings) separated by commas
            restrictionIDs = Arrays.asList(str.split("\\s*,\\s*"));
            // Check if each restriction is valid
            for (String restriction : restrictionIDs) {
                // If any restriction is invalid,
                // print an error message and ask the user to try again
                if (!Restrictions.isValid(restriction)) {
                    System.out.println("Invalid restriction: " + restriction);
                    // This makes sure loop runs again
                    validRestrictions = false;
                    break;
                }
            }
        } while (!validRestrictions);

        // Convert restriction IDs to restriction names (e.g. [1] -> ["Vegetarian"])
        // This is done so that the user can enter restriction IDs instead of names to
        // speed up input
        List<String> restrictions = new ArrayList<String>();
        for (String restriction : restrictionIDs) {
            restrictions.add(Restrictions.getRestrictionName(restriction));
        }
        // Print the restrictions as confirmation + for debugging
        System.out.println("restrictions: " + restrictions);

        // Create a user with the provided restrictions
        User user = new User(restrictions);

        // Create a DayLibrary to store days so that
        // if the user enters the same date twice,
        // it will not have to be scraped again
        DayLibrary days = new DayLibrary();

        // Begin main loop. This will run until the user enters "quit".
        // Each iteration of the loop will ask the user for a date and print the meals
        // for that date (with the user's restrictions applied)
        while (true) {
            // Get user input of date in format YYYY-MM-DD
            System.out.println("Enter a date in the format YYYY-MM-DD or type \"quit\" to exit:");
            String input = scanner.nextLine();
            // If the user enters "quit", break out of the loop
            if (input.equals("quit")) {
                break;
            }
            System.out.println("Fetching meals for " + input + "...");
            // Get the day (assortment of menus) for the given date
            Day todayMenus = days.getDay(input, user);
            // If no dishes were found for the given date, print an error message
            if (todayMenus.toString().equals("")) {
                System.out.println("No meals found for " + input + " with your restrictions.");
                continue;
            }
            // No need to put this in an else statement because it is empty
            System.out.println(todayMenus);
        }
        scanner.close();
    }

}
