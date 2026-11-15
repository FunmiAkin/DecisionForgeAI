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

// Expression Tree Node for Logical Rules
class ExprNode {
    String value;
    ExprNode left, right;

    public ExprNode(String value) {
        this.value = value;
        left = right = null;
    }
}

public class LaptopWise {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        run(input);
    }
    public static void run(Scanner input) {
        System.out.println("Welcome to LaptopWise AI.");
        System.out.println("Scenario: Budget-Aware Laptop Selector");
        System.out.println();

        // Sample Catalog
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

        // Truth-Table View
        System.out.println("--- Truth-Table View (Conditions & Eligibility) ---");
        System.out.println("B (Budget) | M (RAM) | L (Weight) | W (Warranty) | Eligible");
        System.out.println("---------------------------------------------------------");
        int[] boolVals = {0, 1};
        for (int b : boolVals) {
            for (int m : boolVals) {
                for (int l : boolVals) {
                    for (int w : boolVals) {
                        int eligible = (b == 1 && m == 1 && l == 1 && (!requireWarranty || w == 1)) ? 1 : 0;
                        System.out.printf("    %d      |    %d    |     %d      |      %d       |    %d    \n", b, m, l, w, eligible);
                    }
                }
            }
        }
        System.out.println();

        // Expression-Tree View & Traversals
        System.out.println("--- Expression-Tree View & Traversals ---");
        // Constructing Expression Tree: AND(AND(AND(B, M), L), W)
        ExprNode root = new ExprNode("AND");
        root.left = new ExprNode("AND");
        root.left.left = new ExprNode("AND");
        root.left.left.left = new ExprNode("B");
        root.left.left.right = new ExprNode("M");
        root.left.right = new ExprNode("L");
        root.right = new ExprNode("W");

        System.out.print("Preorder Traversal  : ");
        printPreorder(root);
        System.out.println();

        System.out.print("Inorder Traversal   : ");
        printInorder(root);
        System.out.println();

        System.out.print("Postorder Traversal : ");
        printPostorder(root);
        System.out.println("\n");

        // 3. Decision-Tree View
        System.out.println("--- Decision-Tree View ---");
        System.out.println("Root: Is Price <= Max Budget?");
        System.out.println(" |-- No  --> REJECT");
        System.out.println(" |-- Yes --> Is RAM >= Min RAM?");
        System.out.println("      |-- No  --> REJECT");
        System.out.println("      |-- Yes --> Is Weight <= Max Weight?");
        System.out.println("           |-- No  --> REJECT");
        System.out.println("           |-- Yes --> Is Warranty >= 2 yrs (if required)?");
        System.out.println("                |-- No  --> REJECT");
        System.out.println("                |-- Yes --> QUALIFIED ✅\n");

        System.out.println("Evaluating laptops against constraints...");
        System.out.println();

        ArrayList<Laptop> eligibleList = new ArrayList<>();

        // Boolean logic evaluation per laptop
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
            System.out.println("  [W: Warranty] = " + W + " (" + l.warranty + "yrs)");

            if (eligible) {
                System.out.println("  Status: QUALIFIED");
                eligibleList.add(l);
            } else {
                System.out.println("  Status: REJECTED (Failed constraints)");
            }
            System.out.println();
        }

        // Knowledge Graph Adjacency List
        System.out.println("Knowledge Graph Adjacency List:");
        for (Laptop l : catalog) {
            System.out.println(l.name + " -> [Price: $" + l.price + ", RAM: " + l.ram + "GB, Weight: " + l.weight + "lbs, Warranty: " + l.warranty + "yrs]");
        }
        System.out.println();

        // Ranking and Explanation Panel
        System.out.println("Ranking eligible models by price...");
        if (eligibleList.isEmpty()) {
            System.out.println("Result: NO MATCHING LAPTOPS");
            System.out.println("Explanation Panel: All models failed one or more conditions (such as weight limits or budget bounds). Try relaxing your constraints.");
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
            System.out.println("Explanation Panel: Meets all user-defined constraints ($" + top.price + " <= $" + maxBudget + ", " + top.ram + "GB RAM, " + top.weight + "lbs) and offers the lowest price among eligible candidates.");
        }
    }

    // Helper methods for Expression Tree Traversals
    public static void printPreorder(ExprNode node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        printPreorder(node.left);
        printPreorder(node.right);
    }

    public static void printInorder(ExprNode node) {
        if (node == null) return;
        printInorder(node.left);
        System.out.print(node.value + " ");
        printInorder(node.right);
    }

    public static void printPostorder(ExprNode node) {
        if (node == null) return;
        printPostorder(node.left);
        printPostorder(node.right);
        System.out.print(node.value + " ");
    }
}