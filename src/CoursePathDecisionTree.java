import javafx.scene.layout.Pane;

public class CoursePathDecisionTree {
    public static Pane createView() {
        Pane canvas = new Pane();
        canvas.getStyleClass().add("diagram-canvas");
        canvas.setPrefSize(1060, 805);

        String[] questions = {
                "Prerequisite completed?",
                "Seat available?",
                "No registration hold?",
                "Registration still open?"
        };
        String[] noOutcomes = {
                "CHECK PREREQUISITE",
                "CHOOSE ANOTHER SECTION",
                "REMOVE THE HOLD",
                "REGISTRATION IS CLOSED"
        };

        double leftX = 85;
        double outcomeX = 640;
        double nodeWidth = 270;
        double nodeHeight = 66;

        for (int i = 0; i < questions.length; i++) {
            double y = 70 + i * 155;
            DiagramUtils.addArrow(canvas, leftX + nodeWidth, y + nodeHeight / 2, outcomeX, y + nodeHeight / 2, "No");
            if (i < questions.length - 1) {
                DiagramUtils.addArrow(canvas, leftX + nodeWidth / 2, y + nodeHeight, leftX + nodeWidth / 2, y + 155, "Yes");
            } else {
                DiagramUtils.addArrow(canvas, leftX + nodeWidth / 2, y + nodeHeight, leftX + nodeWidth / 2, y + 155, "Yes");
            }
        }

        for (int i = 0; i < questions.length; i++) {
            double y = 70 + i * 155;
            DiagramUtils.addNode(canvas, questions[i], leftX, y, nodeWidth, nodeHeight, "diagram-accent");
            DiagramUtils.addNode(canvas, noOutcomes[i], outcomeX, y, 310, nodeHeight, "diagram-negative");
        }
        DiagramUtils.addNode(canvas, "READY TO REGISTER", leftX, 690, nodeWidth, nodeHeight, "diagram-positive");
        return canvas;
    }
}
