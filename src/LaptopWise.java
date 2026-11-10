import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Laptop {
    String name;
    double price;
    int ram;
    double weight;
    int warranty;

    public Laptop(String name, double price, int ram, double weight, int warranty) {
        this.name = name;
        this.price = price;
        this.ram = ram;
        this.weight = weight;
        this.warranty = warranty;
    }
}

public class LaptopWise {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to LaptopWise AI.");
        System.out.println("Scenario: Budget-Aware Laptop Selector");
        System.out.println();

        //Sample Catalog
        ArrayList<Laptop> catalog = new ArrayList<>();
        catalog.add(new Laptop("DevBook Pro 14", 899.99, 16, 3.1, 2));
        catalog.add(new Laptop("Campus Air 13", 649.50, 8, 2.7, 1));
        catalog.add(new Laptop("Workhorse 15", 799.00, 16, 4.8, 3));
        catalog.add(new Laptop("BudgetLite 14", 499.99, 8, 3.5, 1));

        System.out.println("Sample catalog loaded (" + catalog.size() + " models available).");
        System.out.println();

        // Get user constraints
        System.out.print("Enter maximum budget ($): ");
        double maxBudget = input.nextDouble();

        System.out.print("Enter minimum RAM (GB): ");
        int minRam = input.nextInt();

        System.out.print("Enter maximum weight (lbs): ");
        double maxWeight = input.nextDouble();

        input.nextLine();
        System.out.print("Require 2+ year warranty? (yes/no): ");
        String warrantyChoice = input.nextLine();

        if (!warrantyChoice.equalsIgnoreCase("yes") && !warrantyChoice.equalsIgnoreCase("no")) {
            System.out.println("Invalid input. Defaulting to 'no'.");
            warrantyChoice = "no";
        }
        boolean requireWarranty = warrantyChoice.equalsIgnoreCase("yes");

        System.out.println();
        System.out.println("Evaluating laptops against constraints...");
        System.out.println();

        ArrayList<Laptop> eligibleList = new ArrayList<>();

        //Boolean logic evaluation per laptop
        for (Laptop l : catalog) {
            boolean B = l.price <= maxBudget;
            boolean M = l.ram >= minRam;
            boolean L = l.weight <= maxWeight;
            boolean W = l.warranty >= 2;

            boolean eligible = B && M && L && (!requireWarranty || W);

            System.out.println("Model: " + l.name);
            System.out.println("  [B: Budget]   = " + B + " ($" + l.price + ")");
            System.out.println("  [M: RAM]      = " + M + " (" + l.ram + "GB)");
            System.out.println("  [L: Weight]   = " + L + " (" + l.weight + "lbs)");
            if (requireWarranty) {
                System.out.println("  [W: Warranty] = " + W + " (" + l.warranty + "yrs)");
            }
            System.out.println("  Status: " + (eligible ? "QUALIFIED" : "REJECTED"));
            System.out.println();

            if (eligible) {
                eligibleList.add(l);
            }
        }

        //Knowledge Graph Adjacency List
        System.out.println("Knowledge Graph Adjacency List:");
        for (Laptop l : catalog) {
            System.out.println(l.name + " -> [Price: $" + l.price + ", RAM: " + l.ram + "GB, Weight: " + l.weight + "lbs, Warranty: " + l.warranty + "yrs]");
        }
        System.out.println();

        //Ranking and Explanation
        System.out.println("Ranking eligible models by price...");
        if (eligibleList.isEmpty()) {
            System.out.println("Result: NO MATCHING LAPTOPS");
            System.out.println("Try relaxing your budget or weight constraints.");
        } else {
            Collections.sort(eligibleList, Comparator.comparingDouble(l -> l.price));
            System.out.println("Result: " + eligibleList.size() + " MODEL(S) QUALIFIED");
            for (int i = 0; i < eligibleList.size(); i++) {
                Laptop l = eligibleList.get(i);
                System.out.println((i + 1) + ". " + l.name + " ($" + l.price + ")");
            }

            Laptop top = eligibleList.get(0);
            System.out.println();
            System.out.println("Top Recommendation: " + top.name);
            System.out.println("Why: Offers the best price among models meeting all constraints.");
        }

        input.close();
    }
}