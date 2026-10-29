import java.util.Scanner;

public class DecisionForgeApp {
    public static void start() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to DecisionForge AI!");
        System.out.println("1. CoursePath");
        System.out.println("2. LaptopWise");
        System.out.print("Choose an option: ");

        int choice = input.nextInt();

        if (choice == 1) {
            System.out.println("You selected CoursePath.");
        } else if (choice == 2) {
            System.out.println("You selected LaptopWise.");
        } else {
            System.out.println("Invalid option.");
        }
    }
}

