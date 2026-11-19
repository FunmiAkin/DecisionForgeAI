import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class CoursePathGraph {
    public static class Relationship {
        private final String from;
        private final String relation;
        private final String to;

        public Relationship(String from, String relation, String to) {
            this.from = from;
            this.relation = relation;
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public String getRelation() {
            return relation;
        }

        public String getTo() {
            return to;
        }
    }

    private final List<Relationship> relationships = new ArrayList<>();

    public CoursePathGraph() {
        addRelationship("Student", "wants to register for", "Course");
        addRelationship("Student", "may have", "Registration Hold");
        addRelationship("Course", "requires", "Prerequisite");
        addRelationship("Course", "has", "Seat Availability");
        addRelationship("Course", "has", "Registration Deadline");
        addRelationship("Advisor", "assists", "Student");
    }

    private void addRelationship(String from, String relation, String to) {
        relationships.add(new Relationship(from, relation, to));
    }

    public List<Relationship> getRelationships() {
        return Collections.unmodifiableList(relationships);
    }

    public Pane createView() {
        Pane canvas = new Pane();
        canvas.getStyleClass().add("diagram-canvas");
        canvas.setPrefSize(1060, 670);
        double nodeWidth = 190;
        double nodeHeight = 66;

        Map<String, Point2D> positions = new LinkedHashMap<>();
        positions.put("Advisor", new Point2D(80, 85));
        positions.put("Student", new Point2D(80, 310));
        positions.put("Registration Hold", new Point2D(410, 75));
        positions.put("Course", new Point2D(410, 310));
        positions.put("Prerequisite", new Point2D(745, 80));
        positions.put("Seat Availability", new Point2D(745, 310));
        positions.put("Registration Deadline", new Point2D(745, 545));

        for (Relationship relationship : relationships) {
            Point2D from = positions.get(relationship.getFrom());
            Point2D to = positions.get(relationship.getTo());
            Point2D direction = to.subtract(from);
            double length = direction.magnitude();
            double dx = direction.getX() / length;
            double dy = direction.getY() / length;
            double startDistance = Math.min(
                    dx == 0 ? Double.POSITIVE_INFINITY : nodeWidth / 2 / Math.abs(dx),
                    dy == 0 ? Double.POSITIVE_INFINITY : nodeHeight / 2 / Math.abs(dy)
            );
            double endDistance = startDistance;
            double startX = from.getX() + dx * startDistance;
            double startY = from.getY() + dy * startDistance;
            double endX = to.getX() - dx * endDistance;
            double endY = to.getY() - dy * endDistance;
            DiagramUtils.addArrow(canvas, startX, startY, endX, endY, relationship.getRelation());
        }

        for (Map.Entry<String, Point2D> entry : positions.entrySet()) {
            Point2D center = entry.getValue();
            String name = entry.getKey();
            String type = name.equals("Student") || name.equals("Course") ? "diagram-accent" : "";
            DiagramUtils.addNode(canvas, name, center.getX() - nodeWidth / 2, center.getY() - nodeHeight / 2, nodeWidth, nodeHeight, type);
        }
        return canvas;
    }
}
