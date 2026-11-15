import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class CoursePathGraph {
    private final Map<String, List<String>> graph =
            new LinkedHashMap<>();
    public CoursePathGraph() {
        addRelationship("Student", "wants to register for", "Course");
        addRelationship("Student", "may have", "Registration Hold");
        addRelationship("Course", "requires", "Prerequisite");
        addRelationship("Course", "has", "Seat Availability");
        addRelationship("Course", "has", "Registration Deadline");
        addRelationship("Advisor", "assists", "Student");
    }
    private void addRelationship(String from, String relation, String to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>())
                .add(relation + " -> " + to);
    }
    public void displayGraph() {
        System.out.println(" CoursePath Knowledge Graph ");
        for (String node : graph.keySet()) {
            System.out.println(node + ":");
            for (String relationship : graph.get(node)) {
                System.out.println("  -> " + relationship);
            }
        }
    }
}

