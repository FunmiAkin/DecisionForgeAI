import java.util.List;
import java.util.Locale;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DecisionForgeApp extends Application {
    private BorderPane root;
    private ScrollPane mainScroll;
    private Button homeNav;
    private Button courseNav;
    private Button laptopNav;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.getStyleClass().add("app-root");
        root.setLeft(createSidebar());

        mainScroll = new ScrollPane();
        mainScroll.setFitToWidth(true);
        mainScroll.getStyleClass().add("main-scroll");
        root.setCenter(mainScroll);

        Scene scene = new Scene(root, 1220, 800);
        attachStylesheet(scene);
        stage.setScene(scene);
        stage.setTitle("DecisionForge AI | CoursePath + LaptopWise");
        stage.setMinWidth(890);
        stage.setMinHeight(640);
        stage.show();
        showHome();
    }

    private VBox createSidebar() {
        Label symbol = new Label("◆");
        symbol.getStyleClass().add("brand-symbol");
        Label brand = new Label("DecisionForge");
        brand.getStyleClass().add("brand-title");
        Label brandSub = new Label("AI DECISION SUPPORT");
        brandSub.getStyleClass().add("brand-subtitle");

        VBox brandBlock = new VBox(5, symbol, brand, brandSub);
        brandBlock.getStyleClass().add("brand-block");

        Label menuTitle = new Label("WORKSPACE");
        menuTitle.getStyleClass().add("nav-heading");

        homeNav = navButton("Overview", this::showHome);
        courseNav = navButton("Scenario B · CoursePath", this::showCoursePath);
        laptopNav = navButton("Scenario C · LaptopWise", this::showLaptopWise);

        Label footer = new Label("COSC 208  •  GROUP PROJECT");
        footer.getStyleClass().add("sidebar-footer");
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox sidebar = new VBox(16, brandBlock, menuTitle, homeNav, courseNav, laptopNav, spacer, footer);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(247);
        sidebar.setMinWidth(247);
        return sidebar;
    }

    private Button navButton(String text, Runnable action) {
        Button button = new Button(text);
        button.getStyleClass().add("nav-button");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setOnAction(event -> action.run());
        return button;
    }

    private void setActiveNav(Button selected) {
        for (Button nav : List.of(homeNav, courseNav, laptopNav)) {
            nav.getStyleClass().remove("nav-active");
        }
        selected.getStyleClass().add("nav-active");
    }

    private void setPage(String eyebrow, String title, String description, Node content) {
        Label eyebrowLabel = new Label(eyebrow);
        eyebrowLabel.getStyleClass().add("eyebrow");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("page-title");
        titleLabel.setWrapText(true);
        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("page-description");
        descriptionLabel.setWrapText(true);

        VBox header = new VBox(8, eyebrowLabel, titleLabel, descriptionLabel);
        VBox page = new VBox(28, header, content);
        page.getStyleClass().add("page");
        mainScroll.setContent(page);
        mainScroll.setVvalue(0);
    }

    private void showHome() {
        setActiveNav(homeNav);

        VBox intro = new VBox(12);
        intro.getStyleClass().addAll("panel", "welcome-panel");
        Label introTitle = new Label("Make decisions you can explain.");
        introTitle.getStyleClass().add("welcome-title");
        Label introBody = new Label("Select a scenario to enter your own choices, review the Boolean logic, and see how the recommendation was made.");
        introBody.getStyleClass().add("welcome-description");
        introBody.setWrapText(true);
        intro.getChildren().addAll(introTitle, introBody);

        VBox courseCard = scenarioCard(
                "B  /  COURSEPATH",
                "Am I ready to register?",
                "Checks prerequisites, seat availability, registration holds and registration deadlines.",
                "Open CoursePath",
                this::showCoursePath
        );
        VBox laptopCard = scenarioCard(
                "C  /  LAPTOPWISE",
                "Which laptop meets my needs?",
                "Filters four sample laptops by budget, RAM, weight and warranty, then ranks matches by price.",
                "Open LaptopWise",
                this::showLaptopWise
        );

        HBox cards = new HBox(20, courseCard, laptopCard);
        HBox.setHgrow(courseCard, Priority.ALWAYS);
        HBox.setHgrow(laptopCard, Priority.ALWAYS);
        courseCard.setMaxWidth(Double.MAX_VALUE);
        laptopCard.setMaxWidth(Double.MAX_VALUE);

        setPage("DECISIONFORGE AI", "Your decision dashboard", "Two interactive decision tools, one desktop application.", new VBox(22, intro, cards));
    }

    private VBox scenarioCard(String tag, String title, String description, String buttonText, Runnable action) {
        Label tagLabel = new Label(tag);
        tagLabel.getStyleClass().add("eyebrow");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("section-title");
        titleLabel.setWrapText(true);
        Label body = new Label(description);
        body.getStyleClass().add("muted-text");
        body.setWrapText(true);
        Button button = primaryButton(buttonText);
        button.setOnAction(event -> action.run());
        VBox card = new VBox(14, tagLabel, titleLabel, body, button);
        card.getStyleClass().add("panel");
        VBox.setVgrow(body, Priority.ALWAYS);
        return card;
    }

    private void showCoursePath() {
        setActiveNav(courseNav);

        ComboBox<String> prerequisite = yesNoChoice();
        ComboBox<String> seats = yesNoChoice();
        ComboBox<String> hold = yesNoChoice();
        ComboBox<String> deadline = yesNoChoice();

        GridPane form = new GridPane();
        form.setHgap(22);
        form.setVgap(20);
        ColumnConstraints questionColumn = new ColumnConstraints();
        questionColumn.setHgrow(Priority.ALWAYS);
        form.getColumnConstraints().add(questionColumn);
        form.add(question("1", "Have you completed the prerequisite?"), 0, 0);
        form.add(prerequisite, 1, 0);
        form.add(question("2", "Is a seat available?"), 0, 1);
        form.add(seats, 1, 1);
        form.add(question("3", "Do you have a registration hold?"), 0, 2);
        form.add(hold, 1, 2);
        form.add(question("4", "Is registration still open?"), 0, 3);
        form.add(deadline, 1, 3);

        Button evaluate = primaryButton("Check registration eligibility");
        Button truthTable = secondaryButton("View truth table");
        Button graph = secondaryButton("Knowledge graph");
        Button tree = secondaryButton("Decision tree");

        FlowPane buttons = new FlowPane(10, 10, evaluate, truthTable, graph, tree);
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setStyle("-fx-padding: 7 0 0 0;");

        VBox inputs = new VBox(22, sectionHeader("Registration checklist", "Choose Yes or No for all four conditions."), form, buttons);
        inputs.getStyleClass().add("panel");

        VBox resultBox = new VBox();
        resultBox.getChildren().add(emptyState("Your result will appear here", "Complete the checklist and select Check registration eligibility."));
        evaluate.setOnAction(event -> {
            if (prerequisite.getValue() == null || seats.getValue() == null || hold.getValue() == null || deadline.getValue() == null) {
                resultBox.getChildren().setAll(notification("Please select Yes or No for all four questions.", false));
                return;
            }
            String answer1 = prerequisite.getValue();
            String answer2 = seats.getValue();
            String answer3 = hold.getValue();
            String answer4 = deadline.getValue();

            CoursePath.Result result = CoursePath.evaluate(answer1, answer2, answer3, answer4);
            resultBox.getChildren().setAll(courseResultView(result));
        });

        truthTable.setOnAction(event -> showTruthTable());
        graph.setOnAction(event -> showDiagram("CoursePath knowledge graph", "This diagram shows the relationships used in the registration scenario.", new CoursePathGraph().createView(), 1110, 790));
        tree.setOnAction(event -> showDiagram("CoursePath decision tree", "Follow each Yes or No branch to see how the registration decision is reached.", CoursePathDecisionTree.createView(), 1110, 850));

        VBox content = new VBox(22, inputs, resultBox);
        setPage("SCENARIO B  /  COURSEPATH", "Course registration eligibility", "A transparent decision based on four Boolean conditions.", content);
    }

    private VBox courseResultView(CoursePath.Result result) {
        Label status = new Label(result.isEligible() ? "READY TO REGISTER" : "NOT READY TO REGISTER");
        status.getStyleClass().addAll("result-title", result.isEligible() ? "success-text" : "warning-text");
        Label description = new Label(result.isEligible()
                ? "All four registration conditions are satisfied."
                : "One or more conditions need attention before registering.");
        description.getStyleClass().add("muted-text");
        description.setWrapText(true);
        Label expression = new Label("P AND S AND NOT H AND D  =  " + result.isEligible());
        expression.getStyleClass().add("logic-expression");

        FlowPane conditionRow = new FlowPane(9, 9,
                statusPill("P Prerequisite", result.isPrerequisite()),
                statusPill("S Seats", result.isSeatAvailable()),
                statusPill("NOT H No hold", !result.hasRegistrationHold()),
                statusPill("D Open", result.isRegistrationOpen())
        );
        conditionRow.setStyle("-fx-padding: 4 0 0 0;");

        VBox messages = new VBox(10);
        for (String message : result.getMessages()) {
            Label messageLabel = new Label("•  " + message);
            messageLabel.getStyleClass().add("message-line");
            messageLabel.setWrapText(true);
            messages.getChildren().add(messageLabel);
        }

        VBox card = new VBox(16, sectionHeader("Eligibility result", "Based on your selected answers."), status, description, expression, conditionRow, new Separator(), messages);
        card.getStyleClass().add("panel");
        return card;
    }

    private void showTruthTable() {
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        String[] headers = {"P", "S", "H", "D", "P ∧ S ∧ ¬H ∧ D"};
        for (int column = 0; column < headers.length; column++) {
            Label heading = new Label(headers[column]);
            heading.getStyleClass().addAll("truth-cell", "truth-header");
            heading.setMinWidth(column == 4 ? 205 : 92);
            grid.add(heading, column, 0);
        }

        for (int row = 0; row < 16; row++) {
            boolean p = (row & 8) != 0;
            boolean s = (row & 4) != 0;
            boolean h = (row & 2) != 0;
            boolean d = (row & 1) != 0;
            boolean eligible = p && s && !h && d;
            String[] values = {boolLetter(p), boolLetter(s), boolLetter(h), boolLetter(d), boolLetter(eligible)};
            for (int column = 0; column < values.length; column++) {
                Label cell = new Label(values[column]);
                cell.getStyleClass().add("truth-cell");
                if (eligible) {
                    cell.getStyleClass().add("truth-eligible");
                } else if (row % 2 == 0) {
                    cell.getStyleClass().add("truth-even");
                }
                cell.setMinWidth(column == 4 ? 205 : 92);
                grid.add(cell, column, row + 1);
            }
        }
        VBox container = new VBox(18, new Label("T = True     F = False"), grid);
        container.setPadding(new Insets(12));
        showDiagram("CoursePath truth table", "All 16 possible combinations of four Boolean conditions. The eligible row is highlighted.", container, 775, 870);
    }

    private String boolLetter(boolean value) {
        return value ? "T" : "F";
    }

    private String boolDigit(boolean value) {
        return value ? "1" : "0";
    }

    private void showLaptopWise() {
        setActiveNav(laptopNav);

        TextField budget = numericField("900");
        TextField minimumRam = numericField("8");
        TextField maximumWeight = numericField("5");
        CheckBox warranty = new CheckBox("Require at least a 2-year warranty");
        warranty.getStyleClass().add("warranty-check");

        GridPane inputsGrid = new GridPane();
        inputsGrid.setHgap(20);
        inputsGrid.setVgap(15);
        inputsGrid.add(fieldGroup("Maximum budget ($)", budget), 0, 0);
        inputsGrid.add(fieldGroup("Minimum RAM (GB)", minimumRam), 1, 0);
        inputsGrid.add(fieldGroup("Maximum weight (lbs)", maximumWeight), 2, 0);
        for (int i = 0; i < 3; i++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPercentWidth(33.33);
            constraints.setHgrow(Priority.ALWAYS);
            inputsGrid.getColumnConstraints().add(constraints);
        }
        budget.setMaxWidth(Double.MAX_VALUE);
        minimumRam.setMaxWidth(Double.MAX_VALUE);
        maximumWeight.setMaxWidth(Double.MAX_VALUE);

        Button evaluate = primaryButton("Find matching laptops");
        Button knowledge = secondaryButton("View knowledge graph");
        Button truthTable = secondaryButton("View truth table");
        Button decisionTree = secondaryButton("View decision tree");
        FlowPane actions = new FlowPane(12, 12, evaluate, knowledge, truthTable, decisionTree);
        actions.setAlignment(Pos.CENTER_LEFT);

        VBox form = new VBox(19, sectionHeader("Your laptop preferences", "Enter your limits and select any warranty requirement."), inputsGrid, warranty, actions);
        form.getStyleClass().add("panel");

        VBox catalog = new VBox(12, sectionHeader("Sample laptop catalog", "Four models are included in the original project data."));
        catalog.getStyleClass().add("panel");
        for (LaptopWise.Laptop laptop : LaptopWise.getCatalog()) {
            HBox item = new HBox(14);
            item.getStyleClass().add("catalog-row");
            Label name = new Label(laptop.getName());
            name.getStyleClass().add("catalog-name");
            Label specs = new Label(laptop.getRam() + " GB RAM  ·  " + laptop.getWeight() + " lbs  ·  " + laptop.getWarranty() + "-year warranty");
            specs.getStyleClass().add("muted-text");
            VBox detail = new VBox(3, name, specs);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            Label price = new Label(money(laptop.getPrice()));
            price.getStyleClass().add("catalog-price");
            item.getChildren().addAll(detail, spacer, price);
            catalog.getChildren().add(item);
        }

        VBox resultBox = new VBox(emptyState("No recommendations yet", "Choose your preferences and select Find matching laptops."));
        evaluate.setOnAction(event -> {
            try {
                double maxBudget = Double.parseDouble(budget.getText().trim());
                int minRam = Integer.parseInt(minimumRam.getText().trim());
                double maxWeight = Double.parseDouble(maximumWeight.getText().trim());
                if (!Double.isFinite(maxBudget) || !Double.isFinite(maxWeight) || maxBudget < 0 || minRam < 0 || maxWeight <= 0) {
                    throw new NumberFormatException();
                }
                List<LaptopWise.Evaluation> results = LaptopWise.evaluate(maxBudget, minRam, maxWeight, warranty.isSelected());
                resultBox.getChildren().setAll(laptopResultView(results, warranty.isSelected()));
            } catch (NumberFormatException exception) {
                resultBox.getChildren().setAll(notification("Enter a valid nonnegative budget and RAM amount, and a maximum weight greater than zero.", false));
            }
        });
        knowledge.setOnAction(event -> showLaptopGraph());
        truthTable.setOnAction(event -> showLaptopTruthTable(warranty.isSelected()));
        decisionTree.setOnAction(event -> showLaptopDecisionTree(warranty.isSelected()));

        VBox content = new VBox(22, form, catalog, resultBox);
        setPage("SCENARIO C  /  LAPTOPWISE", "Find the right laptop", "Compare the sample catalog using four Boolean checks and rank eligible models by price.", content);
    }

    private VBox laptopResultView(List<LaptopWise.Evaluation> results, boolean requireWarranty) {
        List<LaptopWise.Laptop> matches = LaptopWise.getRankedMatches(results);
        Label status = new Label(matches.isEmpty() ? "NO MATCHING LAPTOPS" : matches.size() + " MATCHING LAPTOP(S)");
        status.getStyleClass().addAll("result-title", matches.isEmpty() ? "warning-text" : "success-text");

        VBox recommendation = new VBox(8);
        if (matches.isEmpty()) {
            Label explanation = new Label("Try increasing your budget or maximum weight, lowering your minimum RAM, or removing the warranty requirement.");
            explanation.setWrapText(true);
            explanation.getStyleClass().add("muted-text");
            recommendation.getChildren().add(explanation);
        } else {
            LaptopWise.Laptop top = matches.get(0);
            Label topLabel = new Label("Top recommendation: " + top.getName() + "  ·  " + money(top.getPrice()));
            topLabel.getStyleClass().add("recommendation-title");
            Label why = new Label("Why: This is the lowest-priced model that meets all your selected requirements.");
            why.getStyleClass().add("muted-text");
            why.setWrapText(true);
            recommendation.getChildren().addAll(topLabel, why);
            for (int i = 0; i < matches.size(); i++) {
                LaptopWise.Laptop laptop = matches.get(i);
                Label ranked = new Label((i + 1) + ".  " + laptop.getName() + "  —  " + money(laptop.getPrice()));
                ranked.getStyleClass().add("message-line");
                recommendation.getChildren().add(ranked);
            }
        }

        VBox summary = new VBox(14, sectionHeader("Recommendation", "Eligible models are ranked from lowest to highest price."), status, recommendation);
        summary.getStyleClass().add("panel");

        VBox evaluation = new VBox(12, sectionHeader("Boolean checks for each model", "B = Budget, M = RAM, L = Weight and W = Warranty."));
        evaluation.getStyleClass().add("panel");
        for (LaptopWise.Evaluation result : results) {
            LaptopWise.Laptop laptop = result.getLaptop();
            Label model = new Label(laptop.getName());
            model.getStyleClass().add("catalog-name");
            Label outcome = new Label(result.isQualified() ? "QUALIFIED" : "REJECTED");
            outcome.getStyleClass().addAll("result-chip", result.isQualified() ? "chip-pass" : "chip-fail");
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            HBox heading = new HBox(10, model, spacer, outcome);
            FlowPane badges = new FlowPane(8, 8,
                    statusPill("B Budget", result.isBudgetMet()),
                    statusPill("M RAM", result.isRamMet()),
                    statusPill("L Weight", result.isWeightMet())
            );
            if (requireWarranty) {
                badges.getChildren().add(statusPill("W Warranty", result.isWarrantyMet()));
            }
            Label specs = new Label(money(laptop.getPrice()) + "  ·  " + laptop.getRam() + " GB RAM  ·  " + laptop.getWeight() + " lbs  ·  " + laptop.getWarranty() + "-year warranty");
            specs.getStyleClass().add("muted-text");
            VBox item = new VBox(10, heading, specs, badges);
            item.getStyleClass().add("evaluation-row");
            evaluation.getChildren().add(item);
        }
        return new VBox(22, summary, evaluation);
    }

    private void showLaptopTruthTable(boolean requireWarranty) {
        GridPane grid = new GridPane();
        String[] headers = {"B", "M", "L", "W", "Eligible"};

        for (int column = 0; column < headers.length; column++) {
            Label heading = new Label(headers[column]);
            heading.getStyleClass().addAll("truth-cell", "truth-header");
            heading.setMinWidth(column == 4 ? 150 : 92);
            grid.add(heading, column, 0);
        }

        for (int row = 0; row < 16; row++) {
            boolean B = (row & 8) != 0;
            boolean M = (row & 4) != 0;
            boolean L = (row & 2) != 0;
            boolean W = (row & 1) != 0;
            boolean eligible = B && M && L && (!requireWarranty || W);

            String[] values = {
                    boolDigit(B), boolDigit(M), boolDigit(L), boolDigit(W), boolDigit(eligible)
            };

            for (int column = 0; column < values.length; column++) {
                Label cell = new Label(values[column]);
                cell.getStyleClass().add("truth-cell");
                if (eligible) {
                    cell.getStyleClass().add("truth-eligible");
                } else if (row % 2 == 0) {
                    cell.getStyleClass().add("truth-even");
                }
                cell.setMinWidth(column == 4 ? 150 : 92);
                grid.add(cell, column, row + 1);
            }
        }

        VBox container = new VBox(18, new Label("1 = True     0 = False"), grid);
        container.setPadding(new Insets(12));
        showDiagram("LaptopWise truth table", "All 16 Boolean combinations. Warranty is optional unless selected.", container, 775, 870);
    }

    private void showLaptopDecisionTree(boolean requireWarranty) {
        Pane canvas = new Pane();
        canvas.getStyleClass().add("diagram-canvas");
        canvas.setPrefSize(1060, 805);

        String[] questions = requireWarranty
                ? new String[]{"Within budget?", "Enough RAM?", "Within weight limit?", "At least 2-year warranty?"}
                : new String[]{"Within budget?", "Enough RAM?", "Within weight limit?"};

        String[] outcomes = requireWarranty
                ? new String[]{"OVER BUDGET", "NOT ENOUGH RAM", "TOO HEAVY", "WARRANTY TOO SHORT"}
                : new String[]{"OVER BUDGET", "NOT ENOUGH RAM", "TOO HEAVY"};

        double x = 85;
        double outcomeX = 640;
        double width = 270;
        double height = 66;

        for (int i = 0; i < questions.length; i++) {
            double y = 70 + i * 155;
            DiagramUtils.addArrow(canvas, x + width, y + height / 2, outcomeX, y + height / 2, "No");
            DiagramUtils.addArrow(canvas, x + width / 2, y + height, x + width / 2, y + 155, "Yes");
        }

        for (int i = 0; i < questions.length; i++) {
            double y = 70 + i * 155;
            DiagramUtils.addNode(canvas, questions[i], x, y, width, height, "diagram-accent");
            DiagramUtils.addNode(canvas, outcomes[i], outcomeX, y, 310, height, "diagram-negative");
        }

        DiagramUtils.addNode(canvas, "MATCHES REQUIREMENTS", x, 70 + questions.length * 155, width, height, "diagram-positive");

        showDiagram("LaptopWise decision tree", "Follow the Yes or No branches to see how laptops are selected.", canvas, 1110, 850);
    }

    private void showLaptopGraph() {
        TreeItem<String> rootItem = new TreeItem<>("LaptopWise sample catalog");
        rootItem.setExpanded(true);
        for (LaptopWise.Laptop laptop : LaptopWise.getCatalog()) {
            TreeItem<String> model = new TreeItem<>(laptop.getName());
            model.setExpanded(true);
            model.getChildren().addAll(
                    new TreeItem<>("Price → " + money(laptop.getPrice())),
                    new TreeItem<>("RAM → " + laptop.getRam() + " GB"),
                    new TreeItem<>("Weight → " + laptop.getWeight() + " lbs"),
                    new TreeItem<>("Warranty → " + laptop.getWarranty() + " years")
            );
            rootItem.getChildren().add(model);
        }
        TreeView<String> graph = new TreeView<>(rootItem);
        graph.setShowRoot(true);
        graph.setPrefHeight(640);
        graph.getStyleClass().add("knowledge-tree");
        showDiagram("LaptopWise knowledge graph", "Each laptop connects to its stored price, RAM, weight and warranty attributes.", graph, 680, 785);
    }

    private VBox question(String number, String text) {
        Label numberLabel = new Label(number);
        numberLabel.getStyleClass().add("question-number");
        Label questionLabel = new Label(text);
        questionLabel.getStyleClass().add("question-text");
        questionLabel.setWrapText(true);
        VBox block = new VBox(5, numberLabel, questionLabel);
        return block;
    }

    private ComboBox<String> yesNoChoice() {
        ComboBox<String> choice = new ComboBox<>();
        choice.getItems().addAll("Yes", "No");
        choice.setPromptText("Select an answer");
        choice.setPrefWidth(205);
        choice.getStyleClass().add("answer-choice");
        return choice;
    }

    private TextField numericField(String defaultValue) {
        TextField field = new TextField(defaultValue);
        field.getStyleClass().add("number-input");
        return field;
    }

    private VBox fieldGroup(String caption, Node input) {
        Label name = new Label(caption);
        name.getStyleClass().add("field-caption");
        VBox box = new VBox(8, name, input);
        GridPane.setHgrow(box, Priority.ALWAYS);
        return box;
    }

    private HBox sectionHeader(String title, String subtitle) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("section-title");
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("muted-text");
        subtitleLabel.setWrapText(true);
        VBox text = new VBox(5, titleLabel, subtitleLabel);
        HBox box = new HBox(text);
        return box;
    }

    private Label statusPill(String text, boolean good) {
        Label pill = new Label((good ? "✓ " : "✕ ") + text);
        pill.getStyleClass().addAll("result-chip", good ? "chip-pass" : "chip-fail");
        return pill;
    }

    private VBox emptyState(String title, String description) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("empty-title");
        Label body = new Label(description);
        body.getStyleClass().add("muted-text");
        body.setWrapText(true);
        VBox box = new VBox(9, titleLabel, body);
        box.getStyleClass().add("empty-panel");
        return box;
    }

    private VBox notification(String message, boolean success) {
        Label text = new Label(message);
        text.getStyleClass().add(success ? "success-text" : "warning-text");
        text.setWrapText(true);
        VBox card = new VBox(text);
        card.getStyleClass().add("panel");
        return card;
    }

    private Button primaryButton(String title) {
        Button button = new Button(title);
        button.getStyleClass().add("primary-button");
        return button;
    }

    private Button secondaryButton(String title) {
        Button button = new Button(title);
        button.getStyleClass().add("secondary-button");
        return button;
    }

    private String money(double amount) {
        return String.format(Locale.US, "$%,.2f", amount);
    }

    private void showDiagram(String title, String description, Node drawing, double width, double height) {
        Stage window = new Stage();
        window.initModality(Modality.NONE);
        window.setTitle(title);

        Label heading = new Label(title);
        heading.getStyleClass().add("section-title");
        Label intro = new Label(description);
        intro.getStyleClass().add("muted-text");
        intro.setWrapText(true);
        VBox top = new VBox(6, heading, intro);
        top.setPadding(new Insets(22, 24, 18, 24));

        ScrollPane scroller = new ScrollPane(drawing);
        scroller.setFitToWidth(false);
        scroller.setFitToHeight(false);
        scroller.getStyleClass().add("diagram-scroll");

        BorderPane panel = new BorderPane();
        panel.getStyleClass().add("app-root");
        panel.setTop(top);
        panel.setCenter(scroller);

        Scene scene = new Scene(panel, width, height);
        attachStylesheet(scene);
        window.setScene(scene);
        window.show();
    }

    private void attachStylesheet(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
