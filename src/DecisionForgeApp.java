import java.util.Scanner;

public class DecisionForgeApp {
    public static void start(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to DecisionForge AI!");
        System.out.println("1. CoursePath");
        System.out.println("2. LaptopWise");
        System.out.print("Choose an option: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter 1 or 2.");
            input.close();
            return;
        }
        int choice = input.nextInt();
        System.out.println();
        if (choice == 1) {
            System.out.println("--- Launching CoursePath ---");
            CoursePath.main(args);
        } else if (choice == 2) {
            System.out.println("--- Launching LaptopWise ---");
            LaptopWise.main(args);
        } else {
            System.out.println("Invalid option selected.");
        }
        input.close();
    }
}