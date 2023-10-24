import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// This is the text-based interface for the app which 
// allows the user to enter restrictions and dates
// and prints the meals for the given date
public class TextView {
    public static void main(String[] args) throws Exception {
        Controller controller = new Controller();
        System.out.println("Welcome to the CBA Menu App!");

        Scanner scanner = new Scanner(System.in);
        // Get restrictions
        System.out.println("These are the allowed restrictions:");
        System.out.println(Controller.getPossibleRestrictions());
        System.out.println("Enter your restrictions separated by commas (e.g. \"1, 9\") or press enter to skip:");

        List<String> restrictionIDs = new ArrayList<String>();
        // Keep asking for restrictions until the user enters valid restrictions
        do {
            // Assume the user will enter valid restrictions
            String str = scanner.nextLine();
            // If the user presses enter without entering any restrictions,
            // break out of the loop
            if (str.equals("")) {
                restrictionIDs = new ArrayList<String>();
                break;
            }
            // Split the string into a list of numbers (still strings) separated by commas
            // This also ends up effectively resetting restrictionIDs every loop
            // Which means previous incorrect inputs are not saved / used
            restrictionIDs = Arrays.asList(str.split("\\s*,\\s*"));
        } while (!Controller.areValidRestrictions(restrictionIDs));

        // Create a user with the provided restrictions
        controller.createUser(restrictionIDs);
        // Print the restrictions as confirmation + for debugging
        System.out.println("restrictions: " + controller.getUserRestrictions());

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
            System.out.println(controller.getDayAsString(input));
        }
        scanner.close();
    }

}
