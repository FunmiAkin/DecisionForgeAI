import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class DiagramUtils {
    public static void addNode(Pane canvas, String title, double x, double y, double width, double height, String type) {
        Label label = new Label(title);
        label.setWrapText(true);
        label.getStyleClass().add("diagram-node-label");
        StackPane node = new StackPane(label);
        node.setLayoutX(x);
        node.setLayoutY(y);
        node.setPrefSize(width, height);
        node.setMinSize(width, height);
        node.setMaxSize(width, height);
        node.getStyleClass().add("diagram-node");
        if (type != null && !type.isEmpty()) {
            node.getStyleClass().add(type);
        }
        canvas.getChildren().add(node);
    }

    public static void addArrow(Pane canvas, double startX, double startY, double endX, double endY, String caption) {
        Line line = new Line(startX, startY, endX, endY);
        line.getStyleClass().add("diagram-line");
        canvas.getChildren().add(line);

        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowSize = 10;
        Polygon arrow = new Polygon(
                endX, endY,
                endX - arrowSize * Math.cos(angle - Math.PI / 6), endY - arrowSize * Math.sin(angle - Math.PI / 6),
                endX - arrowSize * Math.cos(angle + Math.PI / 6), endY - arrowSize * Math.sin(angle + Math.PI / 6)
        );
        arrow.getStyleClass().add("diagram-arrow");
        canvas.getChildren().add(arrow);

        if (caption != null && !caption.isEmpty()) {
            Label label = new Label(caption);
            label.getStyleClass().add("diagram-edge-label");
            label.setLayoutX((startX + endX) / 2 - 18);
            label.setLayoutY((startY + endY) / 2 - 13);
            canvas.getChildren().add(label);
        }
    }
}
