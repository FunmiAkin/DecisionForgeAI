import java.util.Scanner;
public class DecisionForgeApp {
    public static void start() {
        Scanner input = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n===== Welcome to DecisionForge AI =====");
            System.out.println("Please select a scenario:");
            System.out.println("1. CoursePath");
            System.out.println("2. LaptopWise");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextLine();
            if (choice.equals("1")) {
                CoursePath.run(input);
            } else if (choice.equals("2")) {
                LaptopWise.run(input);
            } else if (choice.equals("3")) {
                System.out.println("Thank you for using DecisionForge AI!");
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        } while (!choice.equals("3"));
    }
}
