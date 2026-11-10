public class CoursePathDecisionTree {
    public static void displayTree() {
        System.out.println("CoursePath Decision Tree");
        System.out.println("START");
        System.out.println("  |");
        System.out.println("  v");
        System.out.println("Prerequisite completed?");
        System.out.println("  No  -> CHECK PREREQUISITE");
        System.out.println("  Yes -> Continue");
        System.out.println("         |");
        System.out.println("         v");
        System.out.println("Seat available?");
        System.out.println("  No  -> CHOOSE ANOTHER SECTION");
        System.out.println("  Yes -> Continue");
        System.out.println("         |");
        System.out.println("         v");
        System.out.println("No registration hold?");
        System.out.println("  No  -> REMOVE THE HOLD");
        System.out.println("  Yes -> Continue");
        System.out.println("         |");
        System.out.println("         v");
        System.out.println("Registration still open?");
        System.out.println("  No  -> REGISTRATION IS CLOSED");
        System.out.println("  Yes -> READY TO REGISTER");
    }
}

