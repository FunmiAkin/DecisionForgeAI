import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LaptopWise {
    public static class Laptop {
        private final String name;
        private final double price;
        private final int ram;
        private final double weight;
        private final int warranty;

        public Laptop(String name, double price, int ram, double weight, int warranty) {
            this.name = name;
            this.price = price;
            this.ram = ram;
            this.weight = weight;
            this.warranty = warranty;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getRam() {
            return ram;
        }

        public double getWeight() {
            return weight;
        }

        public int getWarranty() {
            return warranty;
        }
    }

    public static class Evaluation {
        private final Laptop laptop;
        private final boolean B;
        private final boolean M;
        private final boolean L;
        private final boolean W;
        private final boolean eligible;

        public Evaluation(Laptop laptop, boolean B, boolean M, boolean L, boolean W, boolean eligible) {
            this.laptop = laptop;
            this.B = B;
            this.M = M;
            this.L = L;
            this.W = W;
            this.eligible = eligible;
        }

        public Laptop getLaptop() {
            return laptop;
        }

        public boolean isBudgetMet() {
            return B;
        }

        public boolean isRamMet() {
            return M;
        }

        public boolean isWeightMet() {
            return L;
        }

        public boolean isWarrantyMet() {
            return W;
        }

        public boolean isQualified() {
            return eligible;
        }
    }

    public static ArrayList<Laptop> getCatalog() {
        ArrayList<Laptop> catalog = new ArrayList<>();
        catalog.add(new Laptop("DevBook Pro 14", 899.99, 16, 3.1, 2));
        catalog.add(new Laptop("Campus Air 13", 649.50, 8, 2.7, 1));
        catalog.add(new Laptop("Workhorse 15", 799.00, 16, 4.8, 3));
        catalog.add(new Laptop("BudgetLite 14", 499.99, 8, 3.5, 1));
        return catalog;
    }

    public static ArrayList<Evaluation> evaluate(double maxBudget, int minRam, double maxWeight, boolean requireWarranty) {
        ArrayList<Evaluation> results = new ArrayList<>();
        ArrayList<Laptop> catalog = getCatalog();

        for (Laptop l : catalog) {
            boolean B = l.price <= maxBudget;
            boolean M = l.ram >= minRam;
            boolean L = l.weight <= maxWeight;
            boolean W = l.warranty >= 2;

            boolean eligible = B && M && L && (!requireWarranty || W);

            results.add(new Evaluation(l, B, M, L, W, eligible));
        }

        return results;
    }

    public static ArrayList<Laptop> getRankedMatches(List<Evaluation> results) {
        ArrayList<Laptop> eligibleList = new ArrayList<>();

        for (Evaluation result : results) {
            if (result.isQualified()) {
                eligibleList.add(result.getLaptop());
            }
        }

        Collections.sort(eligibleList, Comparator.comparingDouble(l -> l.price));
        return eligibleList;
    }
}
