
public class CoursePathTruthTable {
    public static void displayTable() {
        System.out.println("\nCoursePath Truth Table");
        System.out.println("P  S  H  D  | Eligible");
        System.out.println("----------------------");
        for (int p = 1; p >= 0; p--) {
            for (int s = 1; s >= 0; s--) {
                for (int h = 1; h >= 0; h--) {
                    for (int d = 1; d >= 0; d--) {
                        boolean eligible =
                                p == 1 && s == 1 &&
                                        h == 0 && d == 1;
                        System.out.printf(
                                "%s  %s  %s  %s  | %s%n",
                                p == 1 ? "T" : "F",
                                s == 1 ? "T" : "F",
                                h == 1 ? "T" : "F",
                                d == 1 ? "T" : "F",
                                eligible ? "T" : "F"
                        );
                    }
                }
            }
        }
    }
}
