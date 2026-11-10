import java.util.Scanner;
public class CoursePath {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Course DecisionForge AI.");
        System.out.println("Scenario B: CoursePath");
        System.out.println();
        System.out.println();

        System.out.print("Have you completed the prerequisite? (yes/no): ");
        String answer1 = input.nextLine();
        System.out.print("Is a seat available? (yes/no): ");
        String answer2 = input.nextLine();
        System.out.print("Do you have a registration hold? (yes/no): ");
        String answer3 = input.nextLine();
        System.out.print("Is registration still open? (yes/no): ");
        String answer4 = input.nextLine();

        if ((!answer1.equalsIgnoreCase("yes") && !answer1.equalsIgnoreCase("no")) ||
                (!answer2.equalsIgnoreCase("yes") && !answer2.equalsIgnoreCase("no")) ||
                (!answer3.equalsIgnoreCase("yes") && !answer3.equalsIgnoreCase("no")) ||
                (!answer4.equalsIgnoreCase("yes") && !answer4.equalsIgnoreCase("no"))) {
            System.out.println("Invaild input: Please enter yes or no only.");
            input.close();
            return;
        }
        boolean P = answer1.equalsIgnoreCase("yes");
        boolean S = answer2.equalsIgnoreCase("yes");
        boolean H = answer3.equalsIgnoreCase("yes");
        boolean D = answer4.equalsIgnoreCase("yes");

        boolean eligible = P && S && !H && D;
        System.out.println();
        System.out.println("Checking registration eligibility...");
        if (eligible) {
            System.out.println("Result: READY TO REGISTER");
            System.out.println("You meet all four registration conditions.");
        } else if (!P) {
            System.out.println("Result: CHECK PREREQUISITE");
            System.out.println("You must complete the prerequisite first.");
        }
        if (!S) {
            System.out.println("Result: CHOOSE ANOTHER SECTION");
            System.out.println("There are no available seats");
        }
        if (H) {
            System.out.println("Result: REMOVE THE HOLD");
            System.out.println("You have a registration hold that needs to be resolved.");
        }
        if (!D) {
            System.out.println("Result: REGISTRATION IS CLOSED");
            System.out.println("The registration deadline passed.");
        }
        System.out.println();
        System.out.print("Would you like to view the knowledge graph? (yes/no): ");
        String viewGraph = input.nextLine();
        if (viewGraph.equalsIgnoreCase("yes")) {
            CoursePathGraph graph = new CoursePathGraph();
            graph.displayGraph();
        }

        System.out.println();
            System.out.print("Would you like to view the decision tree? (yes/no): ");
            String viewDecisionTree = input.nextLine();

            if (viewDecisionTree.equalsIgnoreCase("yes")) {
                CoursePathDecisionTree.displayTree();
            }
        }
    }


